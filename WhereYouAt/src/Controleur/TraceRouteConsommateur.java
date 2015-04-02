package Controleur;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Controleur.Buffer;
import RealProject.Traceroute;
import Controleur.Coordonnees;
import Graphe.*;

public class TraceRouteConsommateur extends Thread {

	private Buffer buf;
	private int id;
	private Vue.PointMarkers.AppFrame a;
	private Traceroute trace;
	private static Graphe graphe;

	public TraceRouteConsommateur(Buffer c, int id,
			Vue.PointMarkers.AppFrame a, Traceroute trace, Graphe graphe) {
		buf = c;
		this.id = id;
		this.a = a;
		this.trace = trace;
		TraceRouteConsommateur.graphe = graphe;
	}

	public void run() {
		int cpt = 0;
		while (true) {
			Coordonnees c = buf.prendre();
			List<Sommet> historique = new ArrayList<Sommet>();
			System.out.println("Consommateur #" + this.id
					+ " prend: latitude = " + c.getLatitude() + " longitude = "
					+ c.getLongitude());
			a.AjouterCoordonnees(c);
			System.out.println("La couleur dans le thread conso "
					+ c.getCouleur());

			cpt++; // compteur pour connaitre le nombre de consomation

			// graphe.addSommet(new Sommet(c, trace.getSite(), new
			// ArrayList<Sommet>(), 0));
			// New Sommet cree un nouveau Sommet et l'ajoute le sommet dans graphe.listeSommets 
			Sommet s2 = new Sommet(c, c.getSite(), -1, graphe);
			System.out.println("Graphe =" + graphe.toString());

			if (cpt >= 2) {

				// Si on a au moins deux sommets  on ajoute un arc entre eux s'ils se suivents 
				ListIterator<Sommet> liSommets = graphe.listeSommets
						.listIterator(graphe.listeSommets.size() - 1);
				while (liSommets.hasPrevious()) {
					Sommet item = liSommets.previous();
					if (item.getSiteATracer() == s2.getSiteATracer()){
						Arc arc = new Arc(item, s2);
						arc.addArc(graphe, arc);

						// Dessine les Traits
						ArrayList<Position> lastTwo = new ArrayList<Position>();
						lastTwo.add(Position.fromDegrees(item.getC()
								.getLatitude(), item.getC().getLongitude()));
						lastTwo.add(Position.fromDegrees(s2.getC()
								.getLatitude(), s2.getC().getLongitude()));
						a.drawline(lastTwo, c.getCouleur());
						System.out.println("je trace la ligne pour le site "
								+ c.getSite());
						break;
					}else{						
						if(item.getIp()==s2.getIp()){
							// Il faut alors mettre un arc en item et les voisins suivants de s2
							Sommet sommetTemp = item;
							sommetTemp.setSiteATracer(s2.getSiteATracer());
							historique.add(sommetTemp);
						}
						
						for (Sommet sommet : historique) {
							if(sommet.getSiteATracer()==s2.getSiteATracer()){
								Arc arc = new Arc(item, s2);
								arc.addArc(graphe, arc);
								historique.remove(sommet);
								break;
							}
						}
						
					}
				}

				System.out.println("Graphe =" + graphe.toString());

			}
		}

	}

	public static Graphe getGraphe() {
		return graphe;
	}

}