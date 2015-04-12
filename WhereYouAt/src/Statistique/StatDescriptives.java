package Statistique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import Graphe.Graphe;
import Graphe.Sommet;
import Modele.Tools;


public class StatDescriptives {

	private static int nbSommet = 0;
	private static int nbTraceroute = 0;
	private static List<Sommet> lst = new ArrayList<Sommet>();
	private Graphe g ;
	private int totalTrace;

	public StatDescriptives(Graphe graphe, int nbtrace){
		this.g = graphe;
		this.totalTrace = nbtrace;
	}



	public static int getNbSommet() {
		return nbSommet;
	}
	public static void setNbSommet() {
		StatDescriptives.nbSommet = StatDescriptives.nbSommet + 1;
	}
	public static int getNbTraceroute() {
		return nbTraceroute;
	}
	public static void setNbTraceroute() {
		StatDescriptives.nbTraceroute = StatDescriptives.nbTraceroute + 1;
	}
	public static List<Sommet> getLst() {
		return lst;
	}
	public static void setLst(List<Sommet> lst) {
		StatDescriptives.lst = lst;
	}

	public void AffichageStat(){

		
		List<Sommet> listSommetsSorted = Tools.tribulles(g.getListeSommets());
		//ListIterator li = listSommetsSorted.listIterator();
		

		//Collections.sort(lst);
		System.out.println("********* Statistiques Descriptives ***********" 
				+ "\n\nNombre de sommets : "+ g.getNbSommets() 
				+ "\n\nNombre de traceroutes lancés : " + nbTraceroute 
				+ "\nEn attente : " + (totalTrace - nbTraceroute) + "\n"
				+ "\n\n******* Classement des traceroutes par distance ******* \n"
				+ toStringSorted(RemoveDoublons(listSommetsSorted))
				+ " \n *******************************************");
		//return "test : "+ g.getNbSommets();
	}





	public static String toStringSorted(ListIterator<Sommet> liSommet) {
		StringBuffer texte = new StringBuffer();
		

		while (liSommet.hasNext()) {
			Sommet SommetCourant = liSommet.next();
			texte.append(" Site à tracer = " + SommetCourant.getSiteATracer()
					+ " Distance a l'origine =  " + SommetCourant.getDistance()
					+ "\n");
		}

		return texte.toString();
	}


	public static ListIterator<Sommet> RemoveDoublons(List<Sommet> listSommets){

		ListIterator<Sommet> listSommetsI = listSommets.listIterator();
		
		List<Sommet> lst = new ArrayList<Sommet>();
		lst.add(listSommets.get(0));
		ListIterator<Sommet> lstI = lst.listIterator();
		
		
		


		while (listSommetsI.hasNext()) {
			Sommet som = listSommetsI.next();
			
			boolean drap =false;
			
			while(lstI.hasNext()){
				Sommet somTest = lstI.next();

			
				if(som.getSiteATracer().equals(somTest.getSiteATracer())){
					drap= true;
			
				}
			}
			
			if(!drap){
			
				lstI.add(som);
			}
			
			while (lstI.hasPrevious()) lstI.previous();  // end to begin
		}

		return lstI;
	}








}
















