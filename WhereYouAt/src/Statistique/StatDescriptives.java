package Statistique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Graphe.Graphe;
import Graphe.Sommet;

 
public class StatDescriptives implements Comparable<StatDescriptives>{
	
	private static int nbSommet = 0;
	private static int nbTraceroute = 0;
	private static List<Sommet> lst = new ArrayList<Sommet>();
	private Graphe g ;
	
	public StatDescriptives(Graphe graphe){
		this.g = graphe;
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
		
		
		
		//Collections.sort(lst);
		System.out.println("*********Statistiques Descriptive ***********" 
		+ "\n\nNombre de sommets : "+ g.getNbSommets() 
		+ "\n\nNombre de traceroutes lancés : " + nbTraceroute 
		+ "\n\nClassement des traceroute par distance : \n"
		
				
		+ "  *******************************************");
		//return "test : "+ g.getNbSommets();
	}



	@Override
	public int compareTo(StatDescriptives o) {
		// TODO Auto-generated method stub
		return 0;
	}



	

	
	
	
	
	

}
