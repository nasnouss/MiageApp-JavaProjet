package RealProject;

import RealProject.PointMarkers.AppFrame;

public class TraceRouteConsommateur extends Thread {
	
	private Buffer buf;
	private int id;
	private AppFrame a;
	private Traceroute trace;

	
	public TraceRouteConsommateur(Buffer c, int id,AppFrame a, Traceroute trace) {
		buf = c;
		this.id = id;
		this.a = a;
		this.trace=trace;
	}
	
	public void run() {
		int cpt = 0;
		while(true){
			Coordonnees c = buf.prendre();
			System.out.println("Consommateur #" + 
						this.id + " prend: latitude = " + c.getLatitude() + " longitude = " + c.getLongitude() );
			a.AjouterCoordonnees(c);
			System.out.println("La couleur dans le thread conso "+ c.getCouleur());
			
			cpt++;  // compteur pour connaitre le nombre de consomation
			
			if(cpt>=2){  // s'il il y a au moins de position sur la carte
//			System.out.println("je suis dans la partie du line manager conso");
//			LineManager lineManager = LineManager.getInstance();			
//			lineManager.addLine(trace.getLastTwo(cpt), c);  // on ajoute la ligne 
			
				System.out.println("je trace la ligne pour le site " + c.getSite());
			a.drawline(trace.getLastTwo(cpt),c.getCouleur());	
				
			//System.out.println("posi1 " + trace.getLastTwo(cpt).get(0)+ " posi2 " + trace.getLastTwo(cpt).get(1) + " size " + trace.getLastTwo(cpt).size());
			
			}
		}
		
		
	}
	
	
}