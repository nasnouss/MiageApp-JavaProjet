package Graphe;

import java.util.ArrayList;
import java.util.List;

import Controleur.Coordonnees;

public class Sommet {
	Coordonnees c;
	String siteATracer;
	List<Sommet> adjacente = new ArrayList<Sommet>();
	double distance;
	Graphe graphe;
	String ip;
	int bool;
	int PremierSommet; // 0 si premier Sommet sinon 1;

	public Sommet(String ip) {
		this.ip = ip;
	}

	/**
	 * Constructeur qui permet de construire un nouvel objet Sommet
	 * 
	 * @param c
	 *            correspond a un objet Coordonnees
	 * @param siteATracer
	 *            correspond au site du Traceroute
	 * @param distance
	 *            distance a l'origine
	 * @param graphe
	 *            correspond à l'objet Graphe
	 * @param PremierSommet
	 *            correspond au premier Sommet du graphe 0 si premier Sommet 1
	 *            sinon
	 * @since 1.0
	 */

	public Sommet(Coordonnees c, String siteATracer, double distance,
			Graphe graphe, int PremierSommet) {
		super();
		bool = 0;
		this.c = c;
		this.siteATracer = siteATracer;
		this.distance = distance;
		this.graphe = graphe;
		this.PremierSommet = PremierSommet;
		synchronized (graphe) {

			// Si plusieurs fois la meme IP pour le meme site il ne faut pas
			// l'ajouter
			for (Sommet s : graphe.listeSommets) {
				if (c.getIp().equals(s.getIp())
						&& s.getSiteATracer() == siteATracer) {
					System.out.println("Sommet Existe deja :  " + siteATracer
							+ " ->" + s.getIp());
					bool = 1;
					break;
				}
			}
			if (bool == 0) {
				graphe.listeSommets.add(this);
			}
		}
	}

//github.com/nasnouss/MiageApp-JavaProjet.git
	public Coordonnees getC() {
		return c;
	}

	public String getSiteATracer() {
		return siteATracer;
	}

	public String getIp() {
		if (this.ip != null) {
			return this.ip;
		} else {
			return c.getIp();

		}
	}

	public void setSiteATracer(String siteATracer) {
		this.siteATracer = siteATracer;
	}

	public int getPremierSommet() {
		return PremierSommet;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getDistance() {
		return this.distance;
	}

	// @Override
	// public boolean equals(Object obj) {
	// if (obj instanceof Sommet) {
	//
	// // Vérification des valeurs des attributs
	// Sommet newSom = (Sommet) obj;
	// return newSom.c == this.c && newSom.siteATracer == this.siteATracer
	// && newSom.distance == this.distance
	// && newSom.PremierSommet == this.PremierSommet;
	// } else {
	// return false;
	// }
	//
	// }

}
