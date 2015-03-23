package Vue;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwind.util.measure.LengthMeasurer;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.Color;
import java.util.ArrayList;

import Controleur.Coordonnees;

public class PointMarkers extends ApplicationTemplate {
	@SuppressWarnings("serial")
	public static class AppFrame extends ApplicationTemplate.AppFrame {
		
		private static RenderableLayer layer;
		private static AppFrame app;
		protected LengthMeasurer measurer = new LengthMeasurer();
		protected boolean followTerrain = true;
		public final static int GREAT_CIRCLE = WorldWind.GREAT_CIRCLE;
		protected int pathType = GREAT_CIRCLE;

		public AppFrame() {
			super(true, true, false);
			app = this;
			layer = new RenderableLayer();
			 this.measurer.setFollowTerrain(this.followTerrain);
			 this.measurer.setPathType(this.pathType);
		}

		public static AppFrame getAppFrame() {
			return app;
		}

		public void AjouterCoordonnees(Coordonnees c) {
			// Creating positions where the placemarkers will be placed.
			Position pointPosition = Position.fromDegrees(c.getLatitude(),
					c.getLongitude());
			// Creating markers providing the positions previously defined
			PointPlacemark pmStandard = new PointPlacemark(pointPosition);

			// Creating new placemark attributes
			PointPlacemarkAttributes pointAttributeBlue = new PointPlacemarkAttributes();
			// Attaching the placemark attributes to the placemarks.
			pmStandard.setAttributes(pointAttributeBlue);
			// //pmBlue.setAttributes(pointAttributeBlue);
			// Changing color of the placemarkers attached to their respective
			// placemark attribute.
			pointAttributeBlue.setImageColor(c.getCouleur()); // On choisir la
																// couleur
			// Changing font type, size and setting it to bold.
			// pointAttributeBlue.setLabelFont(Font.decode(c.getIp()));
			// Changing the label text color
			// pointAttributeBlue.setLabelMaterial(Material.CYAN);
			// Changing the text scale of the placemark 'pmBlue'
			// pointAttributeBlue.setLabelScale(1.8);
			// Setting text label for placemarkers.
			// pointAttributeBlue.setLabelMaterial(Material.CYAN);
			pmStandard.setLabelText(c.getSite() + " " + c.getIp());
			// pmBlue.setLabelText("serveur du boss, ip: je te dis pas, Bold.");
			// Setting up annotation pop-up to be activated with mouse-over
			// at the placemark 'point2'.
			// pmRed.setValue(AVKey.DISPLAY_NAME,
			// "Text Displayed by mouse-over");

			// Creating renderable layer to attach the markers.
			// layer = new RenderableLayer();

			// Adding placemarkers to the rederable layer
			layer.addRenderable(pmStandard);

			// Adding renderable layer to the application.
			insertBeforeCompass(getWwd(), layer);
			this.getLayerPanel().update(this.getWwd());

		}

		public void drawline(ArrayList<Position> lst, Color color) {
			Polyline path = new Polyline(lst);
			path.setFollowTerrain(this.followTerrain);
			path.setColor(color);
			layer.addRenderable(path);
			insertBeforeCompass(getWwd(), layer);

			this.getLayerPanel().update(this.getWwd());

		}
		
		public  double getDistance (Position pos1, Position pos2 ){
			final int rayon = 6367445 ;
			double a =pos1.getLatitude().getDegrees() ;
			double b= pos2.getLatitude().getDegrees() ;
			double c= pos1.getLongitude().getDegrees() ;
			double d= pos2.getLongitude().getDegrees();
			
			
			return rayon*Math.acos(Math.sin(a)*Math.sin(b) + Math.cos(a)*Math.cos(b)*Math.cos(c-d));
		}

	}

}
