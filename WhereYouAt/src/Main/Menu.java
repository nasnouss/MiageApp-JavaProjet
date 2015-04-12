package Main;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controleur.Buffer;
import Controleur.TraceRouteConsommateur;
import Controleur.TraceRouteProducteur;
import Modele.Pool;
import Modele.Tools;
import Modele.Traceroute;
import Statistique.StatDescriptives;
import Graphe.Arc;
import Graphe.Graphe;
import Graphe.Sommet;

public class Menu {
	public static List<TraceRouteProducteur> ltrProd;
	public static List<TraceRouteConsommateur> ltrCons;
	Buffer c;
	Graphe graphe;
	int nbLancer; // Nb Traceroute Lancer pendant l'application
	int PremierFois; // Detecte si c'est la premier fois qu'on lance
						// l'application
	static Vue.PointMarkers.AppFrame a;

	Pool Allpool = null;
	int pool;
	private static Menu menu = null;

	public Menu() {
		Menu.ltrProd = new ArrayList<TraceRouteProducteur>();
		Menu.ltrCons = new ArrayList<TraceRouteConsommateur>();
		this.c = new Buffer();
		this.graphe = new Graphe();
		this.nbLancer = 0; // Nb Traceroute Lancer pendant l'application
		this.PremierFois = 0; // Detecte si c'est la premier fois qu'on lance
								// l'application
	}

	public static Menu getInstance() {
		if (menu == null)
			menu = new Menu();
		return menu;
	}

	public void Start() throws InterruptedException {

		a = (Vue.PointMarkers.AppFrame) ApplicationTemplate.start(
				"Globe - Where You At", Vue.PointMarkers.AppFrame.class);

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		String menu = "--------------------------------------------------------------------\n"
				+ " 					MENU  	       \n\n"
				+ "--------------------------------------------------------------------\n\n\n"
				+ "1. Lancer des traceroutes		 		\n"
				+ "2. Ajouter des cibles aleatoires	\n"
				+ "3. Ajouter des tranches d’adresses Ip	\n"
				+ "4. Graphe + Voisins	\n"
				+ "5. Statistiques	\n"
				+ "6. Exporter le graphe en ficher csv           \n"
				+ "0. Exit\n			  		  ";
		System.out
				.println("Combien de threads voulez-vous executer simultanement ?");
		pool = Integer.parseInt(sc.nextLine());
		Allpool = Pool.getInstance(pool, this);

		while (true) {

			System.out.println("Veuillez faire un choix \n");
			System.out.println(menu); // on affiche le menu
			String choix = sc.nextLine();

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

					Menu.ltrProd.add(new TraceRouteProducteur(c, j,
							siteATracer, Integer.parseInt(choixAPI), trace,
							Tools.setCouleur()));
					Menu.ltrCons.add(new TraceRouteConsommateur(c, j, a, trace,
							graphe));
					nbLancer++;

					// POOL DE THREAD
					Allpool.start(Menu.ltrProd.get(j), Menu.ltrCons.get(j));

				}
				PremierFois++;

				break;
			case "2":

				Scanner sc2 = new Scanner(System.in);
				System.out.println("Combien de cibles voulez-vous ajouter ?");
				int nbSiteATracer = Integer.parseInt(sc2.nextLine());

				if (PremierFois != 0) {
					nbSiteATracer += nbLancer;
				}

				for (int i = nbLancer; i < nbSiteATracer; i++) {

					String ip = Tools.randomIp();
					Menu.ltrProd.add(new TraceRouteProducteur(c, i, ip, 2,
							new Traceroute(ip, new ArrayList<Position>()),
							Tools.setCouleur()));
					Menu.ltrCons.add(new TraceRouteConsommateur(c, i, a,
							new Traceroute(ip, new ArrayList<Position>()),
							graphe));
					nbLancer++;

					Allpool.start(Menu.ltrProd.get(i), Menu.ltrCons.get(i));

				}

				PremierFois++;

				break;
			case "3":
				System.out.println("Veuillez saisir l'ip de début : ");
				String ip1 = sc.nextLine();
				System.out.println("Veuillez saisir l'ip de fin : ");
				String ip2 = sc.nextLine();
				try {
					System.out.println("nbLancer = " + nbLancer);
					Tools.trancheIp(ip1, ip2, nbLancer, Menu.ltrProd, Menu.ltrCons);

				} catch (UnknownHostException e) {
					System.out.println("Une erreur est survenue plage ip");
				}
				break;

			case "4":
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
			case "5":

				StatDescriptives stat = new StatDescriptives(graphe, nbLancer);
				stat.AffichageStat();
				break;

			case "6":
				try {
					Sommet.exportToCSV(graphe);
					Arc.exportToCSV(graphe);
				} catch (IllegalArgumentException | IllegalAccessException
						| IOException e) {
					System.out
							.println("Une erreur est survenue pendant l'export");
				}

				break;
			// sortie
			case "0":
				System.out.println("Exit");
				System.exit(0);
				break;
			}

		}

	}

	public void lancerThread(int i, String ip,
			List<TraceRouteProducteur> ltrProd,
			List<TraceRouteConsommateur> ltrCons) {
		List<Position> lstpos = new ArrayList<Position>();
		Traceroute trace = new Traceroute(ip, lstpos);
		System.out.println("i == " + i + " size = " + ltrProd.size());
		ltrProd.add(new TraceRouteProducteur(c, i, ip, 1, trace, Tools
				.setCouleur()));
		ltrCons.add(new TraceRouteConsommateur(c, i, a, trace, graphe));
		Pool.getInstance(pool, Menu.getInstance()).start(ltrProd.get(i),
				ltrCons.get(i));
		Menu.ltrProd = ltrProd;
		Menu.ltrCons = ltrCons;
		nbLancer = nbLancer + i + 1;
	}

	public List<TraceRouteProducteur> getLtrProd() {
		return ltrProd;
	}

	public List<TraceRouteConsommateur> getLtrCons() {
		return ltrCons;
	}

	// public List<TraceRouteProducteur> getLtrProd() {
	// return ltrProd;
	// }
	//
	// public void setLtrProd(String site) {
	// ListIterator<TraceRouteProducteur> ltrProdTemp = ltrProd.listIterator();
	// while (ltrProdTemp.hasNext()) {
	//
	// if (ltrProdTemp.next().getSiteATracer().equals(site)) {
	// ltrProdTemp.remove();
	// }
	// }
	//
	// this.ltrProd = Lists.newArrayList(ltrProdTemp);
	// }
	//
	// public List<TraceRouteConsommateur> getLtrCons() {
	// return ltrCons;
	// }
	//
	// public void setLtrCons(List<TraceRouteConsommateur> ltrCons) {
	// this.ltrCons = ltrCons;
	// }

}
