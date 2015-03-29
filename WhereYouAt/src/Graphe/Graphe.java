package Graphe;

import java.util.LinkedList;
import java.util.ListIterator;

public class Graphe {
	
	   public LinkedList<Sommet> listeSommets = new LinkedList<Sommet> ();
	   public LinkedList<Arc> listeArcs = new LinkedList<Arc> ();

	   public Object[] getSommets(){
	    return listeSommets.toArray();
	   }

	   public Object[] getArcs(){
	    return listeArcs.toArray();
	   }

	   public String toString(){
	    StringBuffer texte = new StringBuffer("*** graphe ***\n");
	    ListIterator<Sommet> liSommet = this.listeSommets.listIterator();
	    while (liSommet.hasNext()){
	       Sommet SommetCourant = liSommet.next();
	       texte.append("Sommet "+SommetCourant.getSiteATracer()+" " +SommetCourant.getIp().toString()+"\n" );
	    }
	    ListIterator<Arc> liArc = this.listeArcs.listIterator();
	    while (liArc.hasNext()){
	       Arc arcCourant = liArc.next();
	       texte.append("Arc " + arcCourant.toString()+"\n" );
	    }
	    return texte.toString();
	   }
	
}
