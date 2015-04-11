package Controleur;

import gov.nasa.worldwind.geom.Position;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import API.HostIPDataLoader;
import API.Ip;
import Modele.Tools;
import Modele.Traceroute;
import Statistique.StatDescriptives;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public class TraceRouteProducteur extends Thread {

	private Buffer buf;
	private int id;
	private String siteATracer;
	private int api;
	List<Coordonnees> lstcoor;
	static int nbTraceroute;
	private Traceroute trace; // Permet de garder l'historique d'un traceroute
	private Coordonnees cds;
	private List<Position> lstPos;

	public TraceRouteProducteur() {
	}

	public TraceRouteProducteur(Buffer buf, int id, String siteATracer,
			int api, Traceroute trace, Color couleur) {
		this.buf = buf;
		this.id = id;
		this.siteATracer = siteATracer;
		this.api = api;
		this.trace = trace;
		this.cds = new Coordonnees(siteATracer, couleur);
		this.lstPos = new ArrayList<Position>();

	}

	// Permet de parser le traceroute
	public String getIp(String ligneTraceRoute) {
		int bool = 0;
		String monIp = "";
		// Si la ligne ne commence pas par un chiffre alors il ne faut pas la
		// compter, car on a deja pris un premier traceroute avant
		for (int i = 0; i < 4; i++) {

			if (ligneTraceRoute.charAt(i) != ' ') {
				bool = 1;
				break;
			}
		}

		if (bool == 1) {
			for (int i = 0; i < ligneTraceRoute.length(); i++) {
				if (ligneTraceRoute.charAt(i) == '(') {
					while (ligneTraceRoute.charAt(i + 1) != ')') {
						monIp = monIp + ligneTraceRoute.charAt(i + 1);
						i++;
					}
					break;
				}
			}
		}
		return monIp;
	}

	public String getSiteATracer() {
		return siteATracer;
	}

	public void setSiteATracer(String siteATracer) {
		this.siteATracer = siteATracer;
	}

	public void executeAPI(Ip monIp, Color color) throws IOException,
			GeoIp2Exception {
		DatabaseReader r = null;
		Position pos;
		Double latitude;
		Double longitude;

		StatDescriptives.setNbTraceroute();
		
		if (this.api == 1) {
			try {
				// On charge la base de donnees
				r = Ip.load();
			} catch (IOException e) {
				System.out.println("Impossible de charger la BDD");
			}

			latitude = monIp.getLatitude(r);
			longitude = monIp.getLongitude(r);

			if (latitude != 0 && longitude != 0) { // new

				pos = Position.fromDegrees(latitude, longitude);

				// On met dans le buffer la latitude et la longitude
				buf.mettre(new Coordonnees(latitude, longitude, siteATracer,
						monIp.getIp().getHostAddress(), color));

				// on ajoute les positions a l'historique du traceroute
				lstPos = trace.getListCoordonnees();
				lstPos.add(pos);
				trace.setListCoordonnees(lstPos);
			}

		} else {
			cds.setIp(monIp.getIp().getHostAddress());

			HostIPDataLoader hdl = new HostIPDataLoader();
			hdl.getPointData(cds);

			if (cds.getLatitude() != null || cds.getLongitude() != null) {

				cds.setSite(siteATracer);

				pos = Position.fromDegrees(cds.getLatitude(),
						cds.getLongitude());
				// On met dans le buffer la latitude et la longitude
				buf.mettre(cds);

				// on ajoute les positions a l'historique du traceroute

				lstPos = trace.getListCoordonnees();
				lstPos.add(pos);
				trace.setListCoordonnees(lstPos);
			}

		}

	}

	public void run() {
		int compteurLigneEtoile = 0;
		boolean running = true;
		Color color = Tools.setCouleur();

		Runtime runtime = Runtime.getRuntime();

		Process process = null;
		try {
			// On lance le traceroute
			process = runtime.exec("traceroute " + siteATracer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		String line = "";

		// on initialise le traceroute avec le site a tracer
		trace.setSite(siteATracer);

		try {
			while ((line = reader.readLine()) != null && running) {
				if (!line.contains("* * *")) {
					// on remet a 0 le compteur de lignes des etoiles
					compteurLigneEtoile = 0;

					// on Parse le Traceroute
					Ip monIp = new Ip(getIp(line));
					executeAPI(monIp, color);

				} else {

					compteurLigneEtoile++;
					// S'il y a 5 lignes avec des etoiles on Kill le traceroute

					if (compteurLigneEtoile == 5) {
						running = false;
						Thread.currentThread().interrupt();
						System.out.println("Fin du Traceroute : "
								+ this.siteATracer);

					}
				}

			}
		} catch (IOException | GeoIp2Exception e) {
			e.printStackTrace();
		}
	}

}
