package Controleur;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Controleur.Buffer;
import Modele.Traceroute;
import Vue.PointMarkers;
import Controleur.Coordonnees;
import Graphe.*;

public class TraceRouteConsommateur extends Thread {

	private Buffer buf;
	private Vue.PointMarkers.AppFrame a;
	private static Graphe graphe;
	List<Sommet> historique;
	int id;
	List<TraceRouteConsommateur> PremierSommetTraceRoute = new ArrayList<TraceRouteConsommateur>();

	public TraceRouteConsommateur(Buffer c, int id,
			Vue.PointMarkers.AppFrame a, Traceroute trace, Graphe graphe,
			List<Sommet> historique) {
		buf = c;
		this.a = a;
		TraceRouteConsommateur.graphe = graphe;
		this.historique = historique;
		this.id = id;
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

			if (boolPremierSommet == 0) {
				PremierSommetTraceRoute.add(this);
				s2 = new Sommet(c, c.getSite(), -1, graphe, 0);
			} else {
				s2 = new Sommet(c, c.getSite(), -1, graphe, 1);
				distance = Vue.PointMarkers.getDistance(Position.fromDegrees(
						graphe.getPremierSommet().getC().getLatitude(), graphe
								.getPremierSommet().getC().getLongitude()),
						Position.fromDegrees(s2.getC().getLatitude(), s2.getC()
								.getLongitude()));
				s2.setDistance(distance);
			}

			if (cpt >= 2) {

				// Si on a au moins deux sommets on ajoute un arc entre eux
				// s'ils se suivents
				ListIterator<Sommet> liSommets = graphe.listeSommets
						.listIterator(graphe.listeSommets.size() - 1);
				while (liSommets.hasPrevious()) {
					Sommet item = liSommets.previous();
					if (item.getSiteATracer().equals(s2.getSiteATracer())) {
						Arc arc = new Arc(item, s2);
						arc.addArc(graphe, arc);

						// Dessine les Traits
						ArrayList<Position> lastTwo = new ArrayList<Position>();
						lastTwo.add(Position.fromDegrees(item.getC()
								.getLatitude(), item.getC().getLongitude()));
						lastTwo.add(Position.fromDegrees(s2.getC()
								.getLatitude(), s2.getC().getLongitude()));
						a.drawline(lastTwo, c.getCouleur());
						break;
					} 
					//						else {
//						if (item.getIp().equals(s2.getIp())) {
//							// Il faut alors mettre un arc en item et les
//							// voisins suivants de s2
//							Sommet sommetTemp = item;
//							sommetTemp.setSiteATracer(s2.getSiteATracer());
////							System.out.println("sommetTemp = "
////									+ sommetTemp.getSiteATracer());
////							historique.add(sommetTemp);
//						}
//
//						for (Sommet sommet : historique) {
//							if (sommet.getSiteATracer().equals(
//									s2.getSiteATracer())) {// &&
//															// (!sommet.getIp().equals(s2.getIp()))
////								System.out.println("sommet IIIII = "
////										+ sommet.getSiteATracer()
////										+ "sommet IP = " + sommet.getIp()
////										+ "s2 IP " + s2.getIp());
//
//								Arc arc = new Arc(item, s2); // Changer item pour rattacher au bon sommet 
//								arc.addArc(graphe, arc);
//								historique.remove(sommet);
//								break;
//							}
//						}
//
//					}
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