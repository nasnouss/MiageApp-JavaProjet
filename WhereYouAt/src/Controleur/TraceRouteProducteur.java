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
import Main.Menu;
import Modele.Tools;
import Modele.Traceroute;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public class TraceRouteProducteur extends Thread {

	private Buffer buf;
	private int id;
	private String siteATracer;
	private int api;
	List<Coordonnees> lstcoor; // new
	static int nbTraceroute; // new
	private Traceroute trace; // pour avoir l'historique du traceroute
	private Coordonnees cds;
	private List<Position> lstPos;
	private Menu m;

	public TraceRouteProducteur() {
	}

	public TraceRouteProducteur(Buffer buf, int id, String siteATracer,
			int api, Traceroute trace, Color couleur,Menu m) {
		this.buf = buf;
		this.id = id;
		this.siteATracer = siteATracer;
		this.api = api;
		this.trace = trace;
		this.cds = new Coordonnees(siteATracer, couleur);
		this.lstPos = new ArrayList<Position>();
		this.m = m;

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

	public void run() {
		int compteurLigneEtoile = 0;
		Position pos;
		boolean running = true;
		if (this.api == 1) {

			Double latitude;
			Double longitude;
			Color color = Tools.setCouleur();

			int cpt = 1;
			DatabaseReader r = null;
			Runtime runtime = Runtime.getRuntime();

			try {
				r = Ip.load(); // On charge la base de donnees
				final Process process = runtime.exec("traceroute "
						+ siteATracer); // On lance le traceroute

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String line = "";

				// on creer un enregistrement du traceroute

				trace.setSite(siteATracer); // on initialise le traceroute avec
				// le site a tracer

				try {

					while ((line = reader.readLine()) != null && running) {
						if (!line.contains("* * *") ) {
							//System.out.println("IDDDDD = " + this.siteATracer);
							compteurLigneEtoile = 0; // on remet a 0 le compter
							// de ligne des etoiles

							Ip monIp = new Ip(getIp(line)); // On Parse le
							// traceroute
							latitude = monIp.getLatitude(r);
							longitude = monIp.getLongitude(r);

							if (latitude != 0 && longitude != 0) { // new

								pos = Position.fromDegrees(latitude, longitude);

								// On met dans le buffer la latitude et la
								// longitude
								buf.mettre(new Coordonnees(latitude, longitude,
										siteATracer, getIp(line), color));

								// on ajoute les positions a l'historique du
								// traceroute

								lstPos = trace.getListCoordonnees();
								lstPos.add(pos);
								trace.setListCoordonnees(lstPos);
							}

						} else {

							compteurLigneEtoile++;

							if (compteurLigneEtoile == 5) { // si il y a 5
															// lignes
								// de suite on Kill
								running = false;
								Thread.currentThread().interrupt();
							}
						}
						cpt++;

					}
				} catch (GeoIp2Exception e) {
					e.printStackTrace();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally { 
					System.out.println("end");
//					m.setLtrProd(this.getSiteATracer());
//					System.out.println("sizeeeeeeeeeee =" + m.getLtrProd().size());
					reader.close();

				}
			} catch (IOException e1) {
				System.out
						.println("Une erreur est survenue, probleme chargement base de donnees");
				e1.printStackTrace();
			}

		}

		if (api == 2) {

			int cpt = 1;
			DatabaseReader r = null;
			Runtime runtime = Runtime.getRuntime();

			try {

				final Process process = runtime.exec("traceroute "
						+ siteATracer); // On lance le traceroute

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String line = "";
				try {

					Thread.currentThread().sleep(5000);
					while ((line = reader.readLine()) != null) {
						if (!line.contains("* * *") && cpt > 2 && running) {

							compteurLigneEtoile = 0;

							Ip monIp = new Ip(getIp(line)); // On Parse le
							// traceroute

							cds.setIp(monIp.getIp().getHostAddress());

							HostIPDataLoader hdl = new HostIPDataLoader();
							hdl.getPointData(cds);

							if (cds.getLatitude() != null
									|| cds.getLongitude() != null) { // new

								cds.setSite(siteATracer);

								pos = Position.fromDegrees(cds.getLatitude(),
										cds.getLongitude());

								buf.mettre(cds); // On met dans le buffer la
								// latitude et la longitude

								// on ajoute les positions a l'historique du
								// traceroute

								lstPos = trace.getListCoordonnees();
								lstPos.add(pos);
								trace.setListCoordonnees(lstPos);

							}

						} else {

							compteurLigneEtoile++;

							if (compteurLigneEtoile == 5) { // si il y a 5 ligne
								// de suite on Kill
								// System.out.println("on a killer le thread");
								running = false;
								Thread.currentThread().interrupt();
							}
						}
						cpt++;

					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally { // fo			
					System.out.println("end");
//					m.setLtrProd(this.getSiteATracer());
//					System.out.println("sizeeeeeeeeeee =" + m.getLtrProd().size());
					reader.close();

				}
			} catch (IOException e1) {
				System.out
						.println("Une erreur est survenue, probl??me chargement base de donn??es");
				e1.printStackTrace();
			}
		}

	}

}
