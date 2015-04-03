package Main;

import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Controleur.Buffer;
import Controleur.TraceRouteConsommateur;
import Controleur.TraceRouteProducteur;
import Modele.Pool;
import Modele.Tools;
import RealProject.Traceroute;
import Graphe.Arc;
import Graphe.Graphe;
import Graphe.Sommet;

public class Menu {
	public static List<TraceRouteProducteur> ltrProd;
	public static List<TraceRouteConsommateur> ltrCons;

	public Menu() {
	}

	public List<TraceRouteProducteur> getLtrProd() {
		return ltrProd;
	}

	public void setLtrProd(List<TraceRouteProducteur> ltrProd) {
		Menu.ltrProd = ltrProd;
	}

	public List<TraceRouteConsommateur> getLtrCons() {
		return ltrCons;
	}

	public void setLtrCons(List<TraceRouteConsommateur> ltrCons) {
		Menu.ltrCons = ltrCons;
	}

	public void Start() throws InterruptedException {

		ltrProd = new ArrayList<TraceRouteProducteur>();
		ltrCons = new ArrayList<TraceRouteConsommateur>();
		Buffer c = new Buffer();
		Graphe graphe = new Graphe();
		Vue.PointMarkers.AppFrame a = (Vue.PointMarkers.AppFrame) ApplicationTemplate.start(
				"NASA World Wind Tutorial - Placemarks", Vue.PointMarkers.AppFrame.class);

		String menu = " ----------------------------------\n"
				+ " 					 MENU  \n		     "
				+ "		----------------------------------\n\n\n"
				+ "1. Lancer des traceroutes		 		\n"
				+ "2. Ajouter des cibles aléatoires	\n" 
				+ "3. Graphe + Voisins	\n" 
				+ "4. Statistiques	\n" 
				+ "0. Exit\n			  		  ";

		//boolean out = false; // boolean pour conditionner la sortie

		while (true) {

			System.out.println("Veuillez faire un choix \n");

			@SuppressWarnings("resource")
			Scanner sc1 = new Scanner(System.in);
			System.out.println(menu); // on affiche le menu
			String choix = sc1.nextLine();

			switch (choix) {

			// Affichage en temps reel
			case "1":

				Scanner sc = new Scanner(System.in);
				Configuration.setValue(AVKey.INITIAL_LATITUDE, 52);
				Configuration.setValue(AVKey.INITIAL_LONGITUDE, 10);
				Configuration.setValue(AVKey.INITIAL_ALTITUDE, 150e4);

				System.out
						.println("Combien de Traceroute voulez-vous lancer ?");
				int nbTraceRoute = Integer.parseInt(sc.nextLine());

				// on saisie les sites que l'on veut
				for (int j = 0; j < nbTraceRoute; j++) {

					System.out.println("Veuillez saisir le " + (j + 1)
							+ " trouceroute \n");
					String siteATracer = sc.nextLine();

					System.out.println("Quel api voulez-vous ? \n");
					System.out.println("1 - GEOAPI \n2 - HOSTIP \n");
					String choixAPI = sc.nextLine();
					
					List<Position> lstpos = new ArrayList<Position>();
					Traceroute trace = new Traceroute(siteATracer,lstpos);
					
					ltrProd.add(new TraceRouteProducteur(c, j, siteATracer,
							Integer.parseInt(choixAPI), trace, Tools.setCouleur()));
					ltrCons.add(new TraceRouteConsommateur(c, j, a, trace,graphe));

				}

				System.out
						.println("Combien de threads voulez-vous executer simultanement ?");
				int pool = Integer.parseInt(sc.nextLine());

				// POOL DE THREAD

				Pool Allpool = new Pool(pool);
				Allpool.start();

				break;
			case "2":

				Scanner sc2 = new Scanner(System.in);
				System.out.println("Combien de cibles voulez-vous ajouter ?");
				int siteATracer = Integer.parseInt(sc2.nextLine());

				for (int i = 1; i <= siteATracer; i++) {

					String ip = Tools.randomIp();
					ltrProd.add(new TraceRouteProducteur(c, i, ip, 1, new Traceroute(ip, new ArrayList<Position>()),
							Tools.setCouleur()));
					ltrCons.add(new TraceRouteConsommateur(c, i, a, new Traceroute(ip, new ArrayList<Position>()),graphe));

				}

				System.out
						.println("Combien de threads voulez-vous executer simultanément ?");
				int pool2 = Integer.parseInt(sc2.nextLine());

				// POOL DE THREAD

				Pool Allpool2 = new Pool(pool2);
				Allpool2.start();

				break;
				
			case "3" :
				System.out.println("1 - Afficher le graphe\n 2-Afficher Voisin d'un sommet\n 0-Remonter au menu superieur\n");
				Scanner aff = new Scanner(System.in);
				String choixUtilisateur = aff.nextLine();

				while(!choixUtilisateur.equals("0")){					
					if(choixUtilisateur.equals("1")){
						System.out.println("Lancer afficheGraphe\n");
						TraceRouteConsommateur.afficheGraphe();
					}
					
					if(choixUtilisateur.equals("2")){
						System.out.println("Vous desirez voir les voisins de quel sommet ?");
						aff = new Scanner(System.in);
						choixUtilisateur = aff.nextLine();
						Arc.getVoisin(TraceRouteConsommateur.getGraphe(),new Sommet(choixUtilisateur));
					}
					aff = new Scanner(System.in);
					choixUtilisateur = aff.nextLine();
				}
				
				break;
			case "4" :
				
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
