package API;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

public class Ip {
	private InetAddress ip;
	private static String urlDataBase = "./GeoLite2-City.mmdb";

	/**
	 * Constructeur Ip qui permet de manipuler des Adresses Ip
	 * @param ip Transforme l'ip String en InetAddress 
	 * @since 1.0
	 * @throws UnknownHostException lance une Exception de type UnknownHostException
	 * @since 1.0
	 */
	public Ip(String ip) throws UnknownHostException {
		this.ip = InetAddress.getByName(ip);
	}

	/**
	 * Méthode qui permet de charger la base de données
	 * @return un DatabaseReader pour lire ensuite dans la BDD
	 * @since 1.0
	 * @throws IOException lance une Exception de type IOException
	 */
	public static DatabaseReader load() throws IOException {

		File database = new File(urlDataBase);
		// This creates the DatabaseReader object, which should be reused across
		// lookups.
		DatabaseReader reader = new DatabaseReader.Builder(database).build();

		return reader;
	}

	/**
	 * Méthode qui retourne la Latitude d'une Ip
	 * @param reader Correspond au reader de la base de données.
	 * @return la latitude de l'Ip
	 * @since 1.0
	 * @throws IOException lance une Exception de typeIOException
	 * @throws GeoIp2Exception lance une Exception de type GeoIp2Exception
	 */
	public double getLatitude(DatabaseReader reader) throws IOException,
			GeoIp2Exception { // new
		double location = 0;

		try {
			CityResponse response = reader.city(this.ip);
			location = response.getLocation().getLatitude();

		} catch (Exception e) {
			//System.out.println("Unknown");

		}
		return location;
	}

	/**
	 * Methode qui retourne l'ip de l'instance
	 * @return une ip de type InetAddress
	 * @since 1.0
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * Méthode qui met à jour l'Ip de l'instance
	 * @param ip ip de type InetAddress pour mettre à jour l'ip de l'instance
	 */
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	/**
	 * Méthode qui retourne la Longitude d'une Ip
	 * @param reader Correspond au reader de la base de données.
	 * @return la longitutde de l'Ip
	 * @since 1.0
	 * @throws IOException lance une Exception de typeIOException
	 * @throws GeoIp2Exception lance une Exception de type GeoIp2Exception
	 */
	public double getLongitude(DatabaseReader reader) throws IOException,
			GeoIp2Exception { 
		double location = 0;
		try {
			CityResponse response = reader.city(this.ip);
			location = response.getLocation().getLongitude();

		} catch (Exception e) {
		}
		return location;

	}

}
