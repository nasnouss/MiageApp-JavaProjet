package RealProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Buffer {
	
	private boolean available = false;
	private List<Coordonnees> listCoordonnees = new ArrayList<Coordonnees>();
	int index = 0;

	// Methode qui renvoie un Coordonn��es latitude longitude
	public synchronized Coordonnees prendre() {
		System.out.println("yes");
		Coordonnees c = null;
		while (available == false) {
			try {
				wait();
			}
			catch (InterruptedException e) { }
		}
		//index--;
		available = false; 
		System.out.println("je prends et je notife\n" + listCoordonnees.size() );
		if (listCoordonnees != null && !listCoordonnees.isEmpty()) {
			
			c = listCoordonnees.remove(listCoordonnees.size() - 1);

		}
		notifyAll();
		return c;
		
	}

	// Methode qui met dans le buffer un coordonn��es
	public synchronized void mettre(Coordonnees c) {
		while (available == true) {
			try {
				wait();
			} catch (InterruptedException e) { 

			}
		}
		System.out.println("je mets et je notife \n");
		
		Set<Coordonnees> mySet = new HashSet<Coordonnees>(listCoordonnees);
		listCoordonnees = new ArrayList<Coordonnees>(mySet);
		listCoordonnees.add(new Coordonnees(c.getLatitude(), c.getLongitude(),c.getSite(),c.getIp(),c.getCouleur()));
		//listCoordonnees = lst2;
	//	listCoordonnees.add(new Coordonnees(c.getLatitude(), c.getLongitude(),c.getSite(),c.getIp(),c.getCouleur()));
		//index = index + 1;
		available = true; 
		notifyAll();
	}
}