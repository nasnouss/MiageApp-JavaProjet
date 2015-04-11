package Statistique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import Graphe.Graphe;
import Graphe.Sommet;

 
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
		
		ListIterator li = lst.listIterator();
        //ici je voudrai que le code affiche que les voiture par exemple qui coute 30000€
while(li.hasNext()){
 System.out.println(li.next());

}
	
		//Collections.sort(lst);
		System.out.println("*********Statistiques Descriptive ***********" 
		+ "\n\nNombre de sommets : "+ g.getNbSommets() 
		+ "\n\nNombre de traceroutes lancés : " + nbTraceroute 
		+ "\n\nClassement des traceroute par distance : \n"
		+ "En attente" + (totalTrace - nbTraceroute) + "\n"
				
		+ "  *******************************************");
		//return "test : "+ g.getNbSommets();
	}



	
	


	

	
	
	
	
	

}
















