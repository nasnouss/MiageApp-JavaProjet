package RealProject;

import gov.nasa.worldwind.geom.Position;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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

	public String getIp(String ligneTraceRoute) {
		String monIp = "";
		for (int i = 0; i < ligneTraceRoute.length(); i++) {
			if (ligneTraceRoute.charAt(i) == '(') {
				while (ligneTraceRoute.charAt(i + 1) != ')') {
					monIp = monIp + ligneTraceRoute.charAt(i + 1);
					i++;
				}
				break;
			}
		}
		return monIp;
	}

	public void run() {
		int compteurLigneEtoile = 0;
		Position pos;

		if (this.api == 1) {

			System.out.println("GO " + id + "\n");
			Double latitude;
			Double longitude;
			Color color = Tools.setCouleur();

			int cpt = 1;
			DatabaseReader r = null;
			Runtime runtime = Runtime.getRuntime();

			try {
				r = Ip.load(); // On charge la base de données
				final Process process = runtime.exec("traceroute "
						+ siteATracer); // On lance le traceroute

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String line = "";

				// on creer un enregistrement du traceroute

				trace.setSite(siteATracer); // on initialise le traceroute avec
				// le site à tracer

				try {

					while ((line = reader.readLine()) != null) {
						if (!line.contains("* * *") && cpt > 2) {

							compteurLigneEtoile = 0; // on remet à 0 le compter
							// de ligne des etoiles

							Ip monIp = new Ip(getIp(line)); // On Parse le
							// traceroute
							latitude = monIp.getLatitude(r);
							longitude = monIp.getLongitude(r);

							if (latitude != null && longitude != null) { // new

								pos = Position.fromDegrees(latitude, longitude);

								// On met dans le buffer la latitude et la
								// longitude
								buf.mettre(new Coordonnees(latitude, longitude,
										siteATracer, getIp(line), color));

								// on ajoute les positions à l'historique du
								// traceroute

								lstPos = trace.getListCoordonnees();
								lstPos.add(pos);
								trace.setListCoordonnees(lstPos);
							}

							System.out.println("Mon id = " + this.id
									+ " site : " + siteATracer + " latitude "
									+ latitude + "\n");
							System.out.println("Mon id = " + this.id
									+ " site : " + siteATracer + " longitude "
									+ longitude + "\n");

						} else {
							System.out.println(line);

							compteurLigneEtoile++;

							if (compteurLigneEtoile == 5) { // si il y a 5 ligne
								// de suite on Kill
								System.out.println("end by 5*");
								Thread.currentThread().interrupt();
							}
						}
						cpt++;

					}
				} catch (GeoIp2Exception e) {
					e.printStackTrace();

				} finally { // fo
					System.out.println("end");
					reader.close();

				}
			} catch (IOException e1) {
				System.out
						.println("Une erreur est survenue, problème chargement base de données");
				e1.printStackTrace();
			}

		}

		if (api == 2) {
			System.out.println("GO " + id + "\n");

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

					System.out.println("la courleur est :"
							+ cds.getCouleur().toString());
					Thread.currentThread().sleep(5000);
					while ((line = reader.readLine()) != null) {
						if (!line.contains("* * *") && cpt > 2) {

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

								// on ajoute les positions à l'historique du
								// traceroute

								lstPos = trace.getListCoordonnees();
								lstPos.add(pos);
								trace.setListCoordonnees(lstPos);

							}

							System.out.println("Mon id = " + this.id
									+ " site : " + siteATracer + " latitude "
									+ cds.getLatitude() + "\n");
							System.out.println("Mon id = " + this.id
									+ " site : " + siteATracer + " longitude "
									+ cds.getLongitude() + "\n");

						} else {
							System.out.println(line);

							compteurLigneEtoile++;

							if (compteurLigneEtoile == 5) { // si il y a 5 ligne
								// de suite on Kill

								System.out.println("end by 5*");
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
					reader.close();

				}
			} catch (IOException e1) {
				System.out
						.println("Une erreur est survenue, problème chargement base de données");
				e1.printStackTrace();
			}
		}

	}

}
