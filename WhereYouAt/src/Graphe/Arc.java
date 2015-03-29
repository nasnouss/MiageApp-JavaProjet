package Graphe;


public class Arc {

	Sommet SommetOrigine;
	Sommet s1;
	Sommet s2;
	
	public Arc(Sommet s1, Sommet s2){
		this.s1=s1;
		this.s2=s2;
	}

	public Sommet getSommetOrigine() {
	    return SommetOrigine;
	   }

	Sommet SommetExtremite;

	public Sommet getSommetExtremite() {
	    return SommetExtremite;
	   }

	public void addArc(Graphe graphe,Arc arc){
		graphe.listeArcs.add(arc);
	}

	public String toString(){
	    return new String("   arc: ( "
	        + this.s1.getSiteATracer() +" " + this.s1.getIp()
	        + " -> "
	        + this.s1.getSiteATracer() +" " + this.s2.getIp()
	        + " )"
	    );
	   }

}
