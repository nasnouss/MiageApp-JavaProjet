package Graphe;

import java.util.ArrayList;
import java.util.List;

import Controleur.Coordonnees;

public class Sommet {
	Coordonnees c;
	String siteATracer;
	List<Sommet> adjacente = new ArrayList<Sommet>();
	int distance;
	Graphe graphe;
	String ip;
	int bool;

	public Sommet(String ip) {
		this.ip = ip;
	}

	public Sommet(Coordonnees c, String siteATracer, int distance, Graphe graphe) {
		super();
		bool=0;
		this.c = c;
		this.siteATracer = siteATracer;
		this.distance = distance;
		this.graphe = graphe;
		// Si plusieurs fois la meme IP il faut l'ajouter une seule fois
		for (Sommet s : graphe.listeSommets) {
			if (c.getIp().equals(s.getIp())) {
				System.out.println("Meme ip on l'ajoute pas à la liste des sommets");
				bool = 1;
				break;
			}
		}
		if (bool == 0) {
			graphe.listeSommets.add(this);
		}
	}

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

}
