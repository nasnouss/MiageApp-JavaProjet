package Controleur;

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
	private Graphe graphe;

	public TraceRouteConsommateur(Buffer c, int id,
			Vue.PointMarkers.AppFrame a, Traceroute trace, Graphe graphe) {
		buf = c;
		this.id = id;
		this.a = a;
		this.trace = trace;
		this.graphe = graphe;
	}

	public void run() {
		int cpt = 0;
		while (true) {
			Coordonnees c = buf.prendre();
			System.out.println("Consommateur #" + this.id
					+ " prend: latitude = " + c.getLatitude() + " longitude = "
					+ c.getLongitude());
			a.AjouterCoordonnees(c);
			System.out.println("La couleur dans le thread conso "
					+ c.getCouleur());

			cpt++; // compteur pour connaitre le nombre de consomation

			// graphe.addSommet(new Sommet(c, trace.getSite(), new
			// ArrayList<Sommet>(), 0));
			Sommet s2 = new Sommet(c, c.getSite(), -1, graphe);
			System.out.println("Graphe =" + graphe.toString());

			if (cpt >= 2) {
				// s'il il y a au moins de position sur la carte on ajoute la
				// ligne

				ListIterator<Sommet> liSommets = graphe.listeSommets
						.listIterator(graphe.listeSommets.size() - 1);
				while (liSommets.hasPrevious()) {
					Sommet item = liSommets.previous();
					if (item.getSiteATracer() == s2.getSiteATracer()) {
						Arc arc = new Arc(item, s2);
						arc.addArc(graphe, arc);
						break;
					}
					// a voir lorsque c'est un autre site
				}

				System.out.println("Graphe =" + graphe.toString());

				System.out.println("je trace la ligne pour le site "
						+ c.getSite());
				a.drawline(trace.getLastTwo(cpt), c.getCouleur());
			}
		}

	}

}