package Test;

import static org.junit.Assert.*;

import java.io.File;


import org.junit.Test;



public class APITest {
	

	File file = new File("./GeoLite2-City.mmdb");
	     

	@Test
	public void Loadtest() {
		
		assertTrue("Le fichier existe", file.exists());
		
		
	}
	
	

}
