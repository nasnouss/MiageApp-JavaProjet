package RealProject;

import java.util.ArrayList;
import java.util.Iterator;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwindx.applications.antenna.AntennaViewer.AppFrame;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

public class LineManager extends ApplicationTemplate {
	
	private  static AppFrame a;
	private static LineManager instance;
	
	
	public LineManager(){
		a = PointMarkers.AppFrame.getAppFrame();
		
	}

	
	public static LineManager getInstance(){
		if(instance==null){
			return new LineManager();
		}
		return instance;
	}
	
	
	
public void addLine(ArrayList<Position> lstCoordonnes, Coordonnees c){
	
	Polyline polyLine = new Polyline(lstCoordonnes);
	polyLine.setColor(c.getCouleur());
	RenderableLayer layer = new RenderableLayer();
	insertBeforeCompass(a.getWwd(), layer);
	
	System.out.println("on trace une ligne avec : "+ lstCoordonnes.get(0) + "et" + lstCoordonnes.get(1));
	
	
	
	
}
	
	
}
