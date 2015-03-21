package RealProject;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Traceroute {
	private String site;
	private List<Position> listCoordonnees ;


	public Traceroute(String site, List<Position> listCoordonnees ){
		this.site=site;
		this.listCoordonnees=listCoordonnees;

	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<Position> getListCoordonnees() {
		return listCoordonnees;
	}

	public void setListCoordonnees(List<Position> listCoordonnees) {
		this.listCoordonnees = listCoordonnees;
	}


	public void addPos(Position pos){
		this.listCoordonnees.add(pos);

	}

	public ArrayList<Position> getLastTwo(int cpt){
		ArrayList<Position> lst2 = new ArrayList<Position>();


		Set<Position> mySet = new HashSet<Position>(listCoordonnees);

		// Créer une Nouvelle ArrayList à partir de Set
		ArrayList<Position> lst = new ArrayList<Position>(mySet);
		System.out.println("size de la liste des coordonnée" + lst.size());
		
		if(lst.size()>=2){
			lst2.add(lst.get(lst.size()-2));
			lst2.add(lst.get(lst.size()-1));
		}
		return lst2;
	}








}
