package Controleur;

import java.awt.Color;

public class Coordonnees {

	private Double latitude;
	private Double longitude;
	private String site;
	private Color couleur; 
	private String Ip;

	public Coordonnees(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Coordonnees(String ip) {
		this.Ip = ip;

	}

	public Coordonnees() {
	}

	public Coordonnees(String ip, Color couleur) {
		this.Ip = ip;
		this.couleur = couleur;
	}

	public void setIp(String ip) {
		Ip = ip;
	}

	public Coordonnees(Double latitude, Double longitude, String site, String Ip) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.site = site;
		this.Ip = Ip;
	}

	public Coordonnees(Double latitude, Double longitude, String siteATracer,
			String ip, Color color) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.site = siteATracer;
		this.Ip = ip;
		this.couleur = color;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Color getCouleur() {
		return couleur;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getIp() {
		return Ip;
	}

	public void createCoordinate(double parseDouble, double parseDouble2) {
		this.latitude = parseDouble;
		this.longitude = parseDouble2;

	}

}
