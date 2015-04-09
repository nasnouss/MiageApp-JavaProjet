package Graphe;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Arc {

	Sommet s1;
	Sommet s2;

	public Arc(Sommet s1, Sommet s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public void addArc(Graphe graphe, Arc arc) {
		graphe.listeArcs.add(arc);
	}

	/**
	 * Affiche les Voisins d'un sommet du graphe
	 * 
	 * @param graphe
	 * @param s
	 */
	public static void getVoisin(Graphe graphe, Sommet s) {
		String afficheVoisin = "";
		// Pour ne pas afficherles doublons lorsqu'on a plusieursvoisins
		List<String> histo = new ArrayList<String>();

		int bool = 0;
		for (Arc voisin : graphe.listeArcs) {

			if (voisin.s1.getIp().equals(s.getIp())) {
				for (String hi : histo) {

					if (hi.equals(voisin.s2.getIp())) {
						bool = 1;
						break;
					}

				}
				if (bool == 0) {

					afficheVoisin += voisin.s2.getIp() + " ";
					histo.add(voisin.s2.getIp());
				}
				bool = 0;

			} else if (voisin.s2.getIp().equals(s.getIp())) {
				for (String hi : histo) {

					if (hi.equals(voisin.s1.getIp())) {
						bool = 1;
						break;
					}
				}
				if (bool == 0) {

					afficheVoisin += voisin.s1.getIp() + " ";
					histo.add(voisin.s1.getIp());
				}

			}
			bool = 0;
		}
		if (afficheVoisin == "") {
			System.out.println("Il n'y a pas de voisin pour ce sommet");
		} else {
			System.out.println("Voici le(s) voisin(s) de " + s.getIp() + " : "
					+ afficheVoisin + "\n");
		}

	}

	public String toString() {
		return new String("   arc: ( " + this.s1.getSiteATracer() + " "
				+ this.s1.getIp() + " -> " + this.s1.getSiteATracer() + " "
				+ this.s2.getIp() + " )");
	}

}
