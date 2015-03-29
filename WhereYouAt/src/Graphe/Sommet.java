package Graphe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import Controleur.Coordonnees;

public class Sommet {
	Coordonnees c;
	String siteATracer;
	List<Sommet>adjacente = new ArrayList<Sommet>();
	int distance;
	Graphe graphe;

	
	public Sommet(Coordonnees c, String siteATracer, 
			int distance, Graphe graphe) {
		super();
		this.c = c;
		this.siteATracer = siteATracer;
		this.distance = distance;
		this.graphe = graphe;
	    graphe.listeSommets.add(this);
	}

//	public Object[] getChildren(Sommet noeud){
//	    LinkedList<Sommet> children = new LinkedList<Sommet> ();
//	    ListIterator<Arc> liArc = graphe.listeArcs.listIterator();
//	    while (liArc.hasNext()){
//	       Arc arcCourant = liArc.next();
//	       if (arcCourant.getSommetOrigine().getIp().equals(noeud.getIp())){
//	        children.add(arcCourant.getSommetExtremite());
//	       }
//	    }
//	    return children.toArray();
//	   }
//
//	   public Boolean hasChildren(){
//	    return (getChildren(this).length != 0 );
//	   }

	
	
	public Coordonnees getC() {
		return c;
	}

	public String getSiteATracer() {
		return siteATracer;
	}		
	
	public String getIp() {
	    return c.getIp();
	   }


}
