package Graphe;

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

	public static void getVoisin(Graphe graphe, Sommet s) {
		String afficheVoisin = "";
		for (Arc voisin : graphe.listeArcs) {
			if (voisin.s1.getIp().equals(s.getIp())) {
				afficheVoisin += voisin.s2.getIp() + " ";
			} else if (voisin.s2.getIp().equals(s.getIp())) {
				afficheVoisin += voisin.s1.getIp() + " ";
			}
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
