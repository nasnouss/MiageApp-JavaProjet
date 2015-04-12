package Graphe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Modele.CSVColumnHeader;
import Modele.CSVList;

public class Arc {

	Sommet s1;
	Sommet s2;
	@CSVColumnHeader(name = "Ip S1")
	String ipS1;
	@CSVColumnHeader(name = "longitutde S1")
	Double longitutdeS1;
	@CSVColumnHeader(name = "latitude S1")
	Double latitudeS1;
	@CSVColumnHeader(name = "Ip S2")
	String ipS2;

	
	public Arc() {

	}

	
	public void setIpS1(String ipS1) {
		this.ipS1 = ipS1;
	}

	public void setLongitutdeS1(Double longitutdeS1) {
		this.longitutdeS1 = longitutdeS1;
	}

	public void setLatitudeS1(Double latitudeS1) {
		this.latitudeS1 = latitudeS1;
	}

	public void setIpS2(String ipS2) {
		this.ipS2 = ipS2;
	}

	public void setLongitutdeS2(Double longitutdeS2) {
		this.longitutdeS2 = longitutdeS2;
	}

	public void setLatitudeS2(Double latitudeS2) {
		this.latitudeS2 = latitudeS2;
	}

	@CSVColumnHeader(name = "longitutde S2")
	Double longitutdeS2;
	@CSVColumnHeader(name = "latitude S2")
	Double latitudeS2;
	
	/**
	 * Arc du graphe qui prend en parametre deux sommets
	 * @param s1 s1 correpond au premier sommet
	 * @param s2 s2 correpond au deuxieme sommet
	 */
	public Arc(Sommet s1, Sommet s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	/**
	 * Ajoute un arc au graphe 
	 * @param graphe graphe de l'application
	 * @param arc arc composé de deux sommets
	 */
	public void addArc(Graphe graphe, Arc arc) {
		graphe.listeArcs.add(arc);
	}

	/**
	 * Affiche les Voisins d'un sommet du graphe
	 * 
	 * @param graphe graphe de l'application
	 * @param s sommet dont on veut afficher les voisins
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

	/**
	 * Affiche les arcs du graphe
	 */
	public String toString() {
		return new String("   arc: ( " + this.s1.getSiteATracer() + " "
				+ this.s1.getIp() + " -> " + this.s1.getSiteATracer() + " "
				+ this.s2.getIp() + " )");
	}

	/**
	 * Exporte les arcs du graphe dans un ficher CSV
	 * @param graphe graphe de l'application 
	 * @throws IOException leve une exception de type IOException
	 * @throws IllegalArgumentException leve une exception de type IllegalArgumentException
	 * @throws IllegalAccessException leve une exception de type IllegalAccessException
	 */
	public static void exportToCSV(Graphe graphe) throws IOException,
			IllegalArgumentException, IllegalAccessException {
		CSVList<Arc> arcCsv = new CSVList<Arc>();
		FileWriter fw = new FileWriter(new File("./Export/Arcs.csv"));
		BufferedWriter output = new BufferedWriter(fw);

		synchronized (graphe) {

			ListIterator<Arc> liSrc = graphe.listeArcs.listIterator();
			while (liSrc.hasNext()) {
				Arc arcCourant = liSrc.next();

				Arc arc = new Arc();
				arc.setIpS1(arcCourant.s1.getIp());
				arc.setLatitudeS1(arcCourant.s1.getC().getLatitude());
				arc.setLongitutdeS1(arcCourant.s1.getC().getLongitude());
				arc.setIpS2(arcCourant.s2.getIp());
				arc.setLatitudeS2(arcCourant.s2.getC().getLatitude());
				arc.setLongitutdeS2(arcCourant.s2.getC().getLongitude());
				arcCsv.add(arc);

			}
		}
		output.write(arcCsv.toCSV());
		output.flush();
		System.out.println("L'export des Arcs est termine\n");

		output.close();
	}

}
