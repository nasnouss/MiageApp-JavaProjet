package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Controleur.TraceRouteProducteur;

public class TraceRouteProducteurTest {

	@Test
	public void getIPtest() {
		TraceRouteProducteur trace = new TraceRouteProducteur();
		assertEquals( "84.96.162.193", trace.getIp("3  193.162.96.84.rev.sfr.net (84.96.162.193)  34.147 ms  29.199 ms  35.491 ms"));
		
	}

}
