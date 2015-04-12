package Test;

import static org.junit.Assert.*;
import gov.nasa.worldwind.geom.Position;

import org.junit.Before;
import org.junit.Test;

import Vue.PointMarkers;

public class PointMarkerTest {

 
	
    
    // les resultats attendus sont trouvé grace au site : http://fr.distance.to
	
	@SuppressWarnings("deprecation")
	@Test
	public void DistanceTest() {
		double delta = 0.5;
		//Paris
		Position paris = Position.fromDegrees(48.856667 , 2.350987);
		// New York
		Position newyork = Position.fromDegrees(40.7142700 , -74.0059700);	
		// Shanghai        
		Position shanghai = Position.fromDegrees(31.230707 , 121.472916);		
		// San Fransisco 
		Position sanFransisco = Position.fromDegrees(37.775 , -122.4183333);			
		// Milan
		Position milan = Position.fromDegrees(45.463688, 9.188141);		
		// Rio		
		Position rio= Position.fromDegrees(-22.903540, -43.209587);	
		
		// Paris-New York
		assertEquals("New York",5836.78,PointMarkers.getDistance(paris, newyork),delta);
		// Paris-shanghai
		assertEquals("shanghai",9262.20,PointMarkers.getDistance(paris, shanghai),delta);
		// Paris-San Fransisco
		assertEquals("San Fransisco",8952.89,PointMarkers.getDistance(paris, sanFransisco),delta);
		// Paris-Milan
		assertEquals("Milan",639.54,PointMarkers.getDistance(paris, milan),delta);
		// Paris-Rio
		assertEquals("Rio",9168.38,PointMarkers.getDistance(paris, rio),delta);
	}

}
