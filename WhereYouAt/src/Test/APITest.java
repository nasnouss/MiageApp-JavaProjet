package Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;



public class APITest {
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	File file = new File("./GeoLite2-City.mmdb");
	InetAddress ip ;
	
	
// test sur l'existence du fichier 
	@Test
	public void LoadTest() {	
		assertTrue("Le fichier existe", file.exists());	
	}
	
	// test sur la lecture de la base de données
	
	@Test
	public void ReadTest(){
		try {
			this.ip = InetAddress.getByName("");
			DatabaseReader reader = new DatabaseReader.Builder(file).build();
			CityResponse response = reader.city(this.ip);
			Double location = response.getLocation().getLatitude();
			System.out.println(location);
			assertTrue(location!=null);
			thrown.none();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
