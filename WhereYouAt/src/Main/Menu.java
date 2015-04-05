package Main;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import com.google.api.client.util.Lists;

import Controleur.Buffer;
import Controleur.TraceRouteConsommateur;
import Controleur.TraceRouteProducteur;
import Modele.Pool;
import Modele.Tools;
import Modele.Traceroute;
import Graphe.Arc;
import Graphe.Graphe;
import Graphe.Sommet;

public class Menu {
	public List<TraceRouteProducteur> ltrProd;
	public List<TraceRouteConsommateur> ltrCons;

	public Menu() {
	}

	public List<TraceRouteProducteur> getLtrProd() {
		return ltrProd;
	}

	public void setLtrProd(String site) {
		ListIterator<TraceRouteProducteur> ltrProdTemp = ltrProd.listIterator();
		while (ltrProdTemp.hasNext()) {

			if (ltrProdTemp.next().getSiteATracer().equals(site)) {
				ltrProdTemp.remove();
			}
		}

		this.ltrProd = Lists.newArrayList(ltrProdTemp);
	}

	public List<TraceRouteConsommateur> getLtrCons() {
		return ltrCons;
	}

	public void setLtrCons(List<TraceRouteConsommateur> ltrCons) {
		this.ltrCons = ltrCons;
	}

	public void Start() throws InterruptedException {

		ltrProd = new ArrayList<TraceRouteProducteur>();
		ltrCons = new ArrayList<TraceRouteConsommateur>();
		Buffer c = new Buffer();
		Graphe graphe = new Graphe();
		int nbLancer = 0; // Nb Traceroute Lancer pendant l'application 
		int PremierFois = 0; // Detecte si c'est la premier fois qu'on lance l'application
		List<Sommet> historique = new ArrayList<Sommet>(); 
		Vue.PointMarkers.AppFrame a = (Vue.PointMarkers.AppFrame) ApplicationTemplate
				.start("Globe - Where You At", Vue.PointMarkers.AppFrame.class);

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		String menu = 
				  "--------------------------------------------------------------------\n"
				+ " 					MENU  	       \n\n"
				+ "--------------------------------------------------------------------\n\n\n"
				+ "1. Lancer des traceroutes		 		\n"
				+ "2. Ajouter des cibles aléatoires	\n"
				+ "3. Graphe + Voisins	\n" + "4. Statistiques	\n"
				+ "0. Exit\n			  		  ";
		System.out
				.println("Combien de threads voulez-vous executer simultanement ?");
		int pool = Integer.parseInt(sc.nextLine());
		Pool Allpool = new Pool(pool, this);

		while (true) {

			System.out.println("Veuillez faire un choix \n");

			@SuppressWarnings("resource")
			Scanner sc1 = new Scanner(System.in);
			System.out.println(menu); // on affiche le menu
			String choix = sc1.nextLine();

			switch (choix) {

			// Affichage en temps reel
			case "1":

				Configuration.setValue(AVKey.INITIAL_LATITUDE, 52);
				Configuration.setValue(AVKey.INITIAL_LONGITUDE, 10);
				Configuration.setValue(AVKey.INITIAL_ALTITUDE, 150e4);
				System.out
						.println("Combien de Traceroute voulez-vous lancer ?");
				int nbTraceRoute = Integer.parseInt(sc.nextLine());

				// on saisie les sites que l'on veut

				if (PremierFois != 0) {
					nbTraceRoute += nbLancer;
				}

				for (int j = nbLancer; j < nbTraceRoute; j++) {

					System.out.println("Veuillez saisir le " + (j + 1)
							+ " trouceroute \n");
					String siteATracer = sc.nextLine();

					System.out.println("Quel api voulez-vous ? \n");
					System.out.println("1 - GEOAPI \n2 - HOSTIP \n");
					String choixAPI = sc.nextLine();

					List<Position> lstpos = new ArrayList<Position>();
					Traceroute trace = new Traceroute(siteATracer, lstpos);

					ltrProd.add(new TraceRouteProducteur(c, j, siteATracer,
							Integer.parseInt(choixAPI), trace, Tools
									.setCouleur(), this));
					ltrCons.add(new TraceRouteConsommateur(c, j, a, trace,
							graphe, historique));
					nbLancer++;

					// POOL DE THREAD
					Allpool.start(ltrProd.get(j), ltrCons.get(j));

				}
				PremierFois++;


				break;
			case "2":

				Scanner sc2 = new Scanner(System.in);
				System.out.println("Combien de cibles voulez-vous ajouter ?");
				int siteATracer = Integer.parseInt(sc2.nextLine());

				if (PremierFois != 0) {
					siteATracer += nbLancer;
				}

				for (int i = nbLancer; i < siteATracer; i++) {

					String ip = Tools.randomIp();
					ltrProd.add(new TraceRouteProducteur(c, i, ip, 1,
							new Traceroute(ip, new ArrayList<Position>()),
							Tools.setCouleur(), this));
					ltrCons.add(new TraceRouteConsommateur(c, i, a,
							new Traceroute(ip, new ArrayList<Position>()),
							graphe, historique));
					nbLancer++;

					Allpool.start(ltrProd.get(i), ltrCons.get(i));

				}

				PremierFois++;

				break;

			case "3":
				System.out
						.println("1 - Afficher le graphe\n 2-Afficher Voisin d'un sommet\n 0-Remonter au menu superieur\n");
				Scanner aff = new Scanner(System.in);
				String choixUtilisateur = aff.nextLine();

				while (!choixUtilisateur.equals("0")) {
					if (choixUtilisateur.equals("1")) {
						System.out.println("Lancer afficheGraphe\n");
						TraceRouteConsommateur.afficheGraphe();
					}

					if (choixUtilisateur.equals("2")) {
						System.out
								.println("Vous desirez voir les voisins de quel sommet ?");
						aff = new Scanner(System.in);
						choixUtilisateur = aff.nextLine();
						Arc.getVoisin(TraceRouteConsommateur.getGraphe(),
								new Sommet(choixUtilisateur));
					}
					aff = new Scanner(System.in);
					choixUtilisateur = aff.nextLine();
				}

				break;
			case "4":

				break;

			// sortie
			case "0":
				System.out.println("Exit");
				break;
			default:

			}

		}

	}

}
