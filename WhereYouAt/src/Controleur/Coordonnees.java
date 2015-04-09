package Controleur;

import java.awt.Color;

public class Coordonnees {

	private Double latitude;
	private Double longitude;
	private String site;
	private Color couleur; 
	private String Ip;

	/**
	 * Classe qui permet de gerer des Coordonnées GPS d'une IP  
	 * @param latitude latitude de la géolocalisation de l'IP
	 * @param longitude longitude de la géolocalisation de l'IP
	 * @since 1.0
	 */
	public Coordonnees(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Constructeur qui prend en parametre une IP 
	 * @param ip Ip du Coordonnées
	 * @since 1.0
	 */
	public Coordonnees(String ip) {
		this.Ip = ip;

	}

	public Coordonnees() {
	}

	/**
	 * Constructeur qui associe une couleur à une IP 
	 * @param ip Ip du Coordonnées
	 * @param couleur couleur du point de l'Ip
	 * @since 1.0
	 */
	public Coordonnees(String ip, Color couleur) {
		this.Ip = ip;
		this.couleur = couleur;
	}

	/**
	 * Met à jour l'ip dans la classe Coordonnées
	 * @param ip
	 * @since 1.0
	 */
	public void setIp(String ip) {
		Ip = ip;
	}
	/**
	 * Constructeur qui prend en parametre la latitude, longitude d'une IP associée à un site
	 * @param latitude latitude de la géolocalisation de l'IP
	 * @param longitude longitude de la géolocalisation de l'IP
	 * @param site site qu'on a appelle avec la commande traceroute ex : Traceroute www.google.com
	 * @param Ip Ip trouvée d'un serveur par lequel passe le Traceroute
	 */
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
