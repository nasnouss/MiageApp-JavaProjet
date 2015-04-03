package RealProject;

import gov.nasa.worldwind.geom.Position;

import java.util.List;

public class Traceroute {
	private String site;
	private List<Position> listCoordonnees;

	public Traceroute(String site, List<Position> listCoordonnees) {
		this.site = site;
		this.listCoordonnees = listCoordonnees;
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

	public void addPos(Position pos) {
		this.listCoordonnees.add(pos);
	}

}
