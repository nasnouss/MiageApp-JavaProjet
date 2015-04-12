package Graphe;

import java.util.LinkedList;
import java.util.ListIterator;

public class Graphe {

	public LinkedList<Sommet> listeSommets = new LinkedList<Sommet>();
	public LinkedList<Arc> listeArcs = new LinkedList<Arc>();

	/**
	 * Renvoie le nombre de Sommets dans le graphe
	 * 
	 * @return le nombre de Sommets dans le graphe
	 */
	public int getNbSommets() {
		return listeSommets.size();
	}

	/**
	 * 
	 * @return la liste des sommets sous forme de tableau
	 */
	public Object[] getSommets() {
		return listeSommets.toArray();
	}

	/**
	 * 
	 * @return la liste d'arcs sous forme de tableau
	 */
	public Object[] getArcs() {
		return listeArcs.toArray();
	}
	
	/**
	 * 
	 * @return la liste des sommets sous forme de liste
	 */
	public LinkedList<Sommet> getListeSommets(){
		return this.listeSommets;
	}

	/**
	 * Affiche le graphe avec la liste des sommets et la liste des arcs
	 */
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

	/**
	 * 
	 * @return Retourne le Premier Sommet du Graphe pour un traceroute
	 */
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

	/**
	 * Permet de recuperer un sommet dans la liste des sommets
	 * @param s
	 * @return le sommet s
	 */
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

	/**
	 * Test si un sommet existe dans la liste de sommets du graphe 
	 * @param s  sommet dont l'on cherche l'existence ou non dans le graphe
	 * @return vrai si le sommet s existe sinon faux
	 */
	public boolean existeSommet(Sommet s) {
		for (Sommet ss : this.listeSommets) {
			if (ss.getIp() == s.getIp()) {
				return true;
			}
		}
		return false;
	}

}
