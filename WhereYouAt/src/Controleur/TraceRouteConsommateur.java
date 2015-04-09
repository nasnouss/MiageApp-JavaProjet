package Controleur;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Controleur.Buffer;
import Modele.Traceroute;
import Controleur.Coordonnees;
import Graphe.*;

public class TraceRouteConsommateur extends Thread {

	private Buffer buf;
	private Vue.PointMarkers.AppFrame a;
	private static Graphe graphe;
	int id;
	List<TraceRouteConsommateur> PremierSommetTraceRoute = new ArrayList<TraceRouteConsommateur>();
	/**
	 * Classe TraceRouteConsommateur est un Thread qui permet de placer les points sur le globe.
	 * Cette classe prend les Coordonnées qui se trouve dans le buffer  
	 * @param c Buffer de l'application
	 * @param id Id du Traceroute
	 * @param a AppFrame de l'application qui correspond au globe
	 * @param trace Historise ce qui se passe sur un traceroute
	 * @param graphe graphe de l'application
	 * @since 1.0
	 */
	public TraceRouteConsommateur(Buffer c, int id,
			Vue.PointMarkers.AppFrame a, Traceroute trace, Graphe graphe) {
		buf = c;
		this.a = a;
		TraceRouteConsommateur.graphe = graphe;
		this.id = id;
	}

	/**
	 * Cette méthode permet de construire le graphe 
	 * @param s2 Correspond au Sommet courant
	 * @param c Correspond au Coordonnées present dans le Buffer
	 */
	public void consGraphe(Sommet s2, Coordonnees c) {
		ListIterator<Sommet> liSommets = graphe.listeSommets
				.listIterator(graphe.listeSommets.size() - 1);
		while (liSommets.hasPrevious()) {
			Sommet item = liSommets.previous();
			if (item.getSiteATracer().equals(s2.getSiteATracer())) {
				Arc arc = new Arc(item, s2);
				arc.addArc(graphe, arc);

				// Dessine les Traits
				ArrayList<Position> lastTwo = new ArrayList<Position>();
				lastTwo.add(Position.fromDegrees(item.getC().getLatitude(),
						item.getC().getLongitude()));
				lastTwo.add(Position.fromDegrees(s2.getC().getLatitude(), s2
						.getC().getLongitude()));
				a.drawline(lastTwo, c.getCouleur());
				break;
			}

		}
	}

	public void run() {
		int cpt = 0;
		while (true) {
			int boolPremierSommet = 0;
			double distance;
			Sommet s2;

			Coordonnees c = buf.prendre();

			a.AjouterCoordonnees(c);

			cpt++; // compteur pour connaitre le nombre de consomation

			for (TraceRouteConsommateur s : PremierSommetTraceRoute) {
				if (s.id == this.id) {
					boolPremierSommet = 1;

					break;
				}
			}

			// New Sommet cree un nouveau Sommet et l'ajoute le sommet dans

			synchronized (graphe) {

				if (boolPremierSommet == 0) {
					PremierSommetTraceRoute.add(this);
					s2 = new Sommet(c, c.getSite(), -1, graphe, 0);
				} else {
					s2 = new Sommet(c, c.getSite(), -1, graphe, 1);
					distance = Vue.PointMarkers.getDistance(Position
							.fromDegrees(graphe.getPremierSommet().getC()
									.getLatitude(), graphe.getPremierSommet()
									.getC().getLongitude()), Position
							.fromDegrees(s2.getC().getLatitude(), s2.getC()
									.getLongitude()));
					s2.setDistance(distance);
				}

				if (cpt >= 2) {

					// Si on a au moins deux sommets on ajoute un arc entre eux
					// s'ils se suivents
					consGraphe(s2, c);

				}
			}
		}
	}

	public static Graphe getGraphe() {
		return graphe;
	}

	public static void afficheGraphe() {
		System.out.println(graphe.toString());
	}

}