package Graphe;

import java.util.LinkedList;
import java.util.ListIterator;

public class Graphe {

	public LinkedList<Sommet> listeSommets = new LinkedList<Sommet>();
	public LinkedList<Arc> listeArcs = new LinkedList<Arc>();


	public int getNbSommets() {
		return listeSommets.size();
	}
	
	
	public Object[] getSommets() {
		return listeSommets.toArray();
	}

	public Object[] getArcs() {
		return listeArcs.toArray();
	}

	public String toString() {
		StringBuffer texte = new StringBuffer("*** graphe ***\n");
		ListIterator<Sommet> liSommet = this.listeSommets.listIterator();
		while (liSommet.hasNext()) {
			Sommet SommetCourant = liSommet.next();
			texte.append("Sommet : site = " + SommetCourant.getSiteATracer()
					+ " ip = " + SommetCourant.getIp().toString()
					+ " Distance a l'origine =  " + SommetCourant.getDistance()
					+ "\n");
		}
		ListIterator<Arc> liArc = this.listeArcs.listIterator();
		while (liArc.hasNext()) {
			Arc arcCourant = liArc.next();
			texte.append("Arc " + arcCourant.toString() + "\n");
		}
		return texte.toString();
	}
	
	//Retourne le Premier Sommet du Graphe pour un traceroute
	public Sommet getPremierSommet() {
		Sommet sommetCourant = null;
		ListIterator<Sommet> liSommet = this.listeSommets.listIterator();
		while (liSommet.hasNext()) {
			if (liSommet.next().getPremierSommet() == 0) {
				sommetCourant = liSommet.next();
				break;
			}

		}
		return sommetCourant;
	}

	public Sommet getSommet(Sommet s) {
		Sommet sommetCourant = null;
		ListIterator<Sommet> liSommet = this.listeSommets.listIterator();
		while (liSommet.hasNext()) {
			if (liSommet.next().equals(s)) {
				sommetCourant = liSommet.next();
				break;
			}
		}
		return sommetCourant;
	}
	
	public boolean existeSommet(Sommet s) {
		for (Sommet ss : this.listeSommets) {
			if (ss.getIp() == s.getIp()) {
				return true;
			}
		}
		return false;
	}

}
