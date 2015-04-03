package Controleur;

import java.util.ArrayList;
import java.util.List;

public class Buffer {

	private boolean available = false;
	private List<Coordonnees> listCoordonnees = new ArrayList<Coordonnees>();
	private List<Coordonnees> historique = new ArrayList<Coordonnees>();

	int index = 0;

	// Methode qui renvoie un Coordonnées latitude longitude
	public synchronized Coordonnees prendre() {
		// System.out.println("yes");
		Coordonnees c = null;
		while (available == false) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		available = false;
		if (listCoordonnees != null && !listCoordonnees.isEmpty()) {

			c = listCoordonnees.remove(listCoordonnees.size() - 1);

		}
		notifyAll();
		return c;

	}

	// Methode qui met dans le buffer un coordonnées
	public synchronized void mettre(Coordonnees c) {
		while (available == true) {
			try {
				wait();
			} catch (InterruptedException e) {

			}
		}

		listCoordonnees.add(new Coordonnees(c.getLatitude(), c.getLongitude(),
				c.getSite(), c.getIp(), c.getCouleur()));
		historique.add(new Coordonnees(c.getLatitude(), c.getLongitude(), c
				.getSite(), c.getIp(), c.getCouleur()));
		available = true;
		notifyAll();
	}
}