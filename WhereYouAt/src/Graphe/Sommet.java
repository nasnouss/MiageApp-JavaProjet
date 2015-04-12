package Graphe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Controleur.Coordonnees;
import Modele.CSVColumnHeader;
import Modele.CSVList;

public class Sommet {
	Coordonnees c;
	@CSVColumnHeader(name = "siteATracer")
	String siteATracer;
	List<Sommet> adjacente = new ArrayList<Sommet>();
	@CSVColumnHeader(name = "distance")
	double distance;
	Graphe graphe;
	@CSVColumnHeader(name = "Ip")
	String ip;
	int bool;
	int PremierSommet; // 0 si premier Sommet sinon 1;
	@CSVColumnHeader(name = "longitutde")
	Double longitutde;
	@CSVColumnHeader(name = "latitude")
	Double latitude;

	public Sommet() {

	}

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

	/**
	 * 
	 * @return le coordonnées c de l'instance
	 */
	public Coordonnees getC() {
		return c;
	}

	/**
	 * 
	 * @return le site a tracer rattaché a un sommet
	 */
	public String getSiteATracer() {
		return siteATracer;
	}

	/**
	 * 
	 * @return l'ip d'un sommet
	 */
	public String getIp() {
		if (this.ip != null) {
			return this.ip;
		} else {
			return c.getIp();

		}
	}

	/**
	 * Met a jour le site a tracer d'un sommet
	 * 
	 * @param siteATracer
	 *            nom du site ou de l'ip que l'on veut rattaché à un sommet
	 */
	public void setSiteATracer(String siteATracer) {
		this.siteATracer = siteATracer;
	}

	/**
	 * Retourne la valeur de PremierSommet, cette valeur permet de savoir qui
	 * est le premier sommet du graphe (0 si premier sommet 1 sinon)
	 * 
	 * @return un int 0 = premier sommet 1 = autre sommet
	 */
	public int getPremierSommet() {
		return PremierSommet;
	}

	/**
	 * Represente la distance à l'origine
	 * 
	 * @param distance
	 *            distance que l'on veut appliqué à un sommet
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

	/**
	 * Represente la distance à l'origine
	 * 
	 * @return la distance à l'origine d'un sommet -1 si premier sommet
	 */
	public double getDistance() {
		return this.distance;
	}

	/**
	 * Exporte la liste de sommet dans un fichier CSV
	 * 
	 * @param graphe
	 *            graphe de l'application
	 * @throws IOException
	 *             leve une exception de type IOException
	 * @throws leve
	 *             une exception de type leve une exception de type leve une
	 *             exception de type
	 * @throws IllegalAccessException
	 *             leve une exception de type IllegalAccessException
	 */
	public static void exportToCSV(Graphe graphe) throws IOException,
			IllegalArgumentException, IllegalAccessException {
		CSVList<Sommet> sommetCsv = new CSVList<Sommet>();
		FileWriter fw = new FileWriter(new File("./Export/Sommet.csv"));
		BufferedWriter output = new BufferedWriter(fw);

		synchronized (graphe) {

			ListIterator<Sommet> liSommet = graphe.listeSommets.listIterator();
			while (liSommet.hasNext()) {
				Sommet SommetCourant = liSommet.next();

				Sommet s = new Sommet();
				s.setSiteATracer(SommetCourant.getSiteATracer());
				s.setIp(SommetCourant.getIp());
				s.setLatitude(SommetCourant.getC().getLatitude());
				s.setLongitutde(SommetCourant.getC().getLongitude());
				s.setDistance(SommetCourant.getDistance());
				sommetCsv.add(s);
			}
		}
		output.write(sommetCsv.toCSV());
		output.flush();
		System.out.println("L'export des Sommets est termine");

		output.close();
	}

	/**
	 * Met a jour les coordonnées d'un sommet
	 * 
	 * @param c
	 *            Coordonnées que l'on veut rattacher à un sommet
	 */
	public void setC(Coordonnees c) {
		this.c = c;
	}

	/**
	 * Met a jour le graphe de l'application
	 * 
	 * @param graphe
	 *            correspond au graphe de l'application
	 */
	public void setGraphe(Graphe graphe) {
		this.graphe = graphe;
	}

	/**
	 * Met à jour l'ip du sommet 
	 * @param ip ip du sommet 
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setBool(int bool) {
		this.bool = bool;
	}

	public void setPremierSommet(int premierSommet) {
		PremierSommet = premierSommet;
	}

	public void setLongitutde(Double longitutde) {
		this.longitutde = longitutde;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
