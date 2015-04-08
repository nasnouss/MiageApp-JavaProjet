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
	private static String urlDataBase = "../BDD/GeoLite2-City.mmdb";

	public Ip(String ip) throws UnknownHostException {
		this.ip = InetAddress.getByName(ip);
	}

	public static DatabaseReader load() throws IOException {

		File database = new File(urlDataBase);
		// This creates the DatabaseReader object, which should be reused across
		// lookups.
		DatabaseReader reader = new DatabaseReader.Builder(database).build();

		return reader;
	}

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

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public double getLongitude(DatabaseReader reader) throws IOException,
			GeoIp2Exception { // new
		double location = 0;
		try {
			CityResponse response = reader.city(this.ip);
			location = response.getLocation().getLongitude();

		} catch (Exception e) {
		}
		return location;

	}

}
