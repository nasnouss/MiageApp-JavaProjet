package Vue;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.pick.PickedObject;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Polyline;
import gov.nasa.worldwind.util.measure.LengthMeasurer;
import gov.nasa.worldwindx.examples.ApplicationTemplate;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.google.api.client.util.StringUtils;

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

			// Add a select listener in order to determine when the label is
			// selected.
			this.getWwd().addSelectListener(new SelectListener() {
				@Override
				public void selected(SelectEvent event) {
					PickedObject po = event.getTopPickedObject();
					if (po != null && po.getObject() instanceof PointPlacemark) {
						PointPlacemark point = (PointPlacemark) po.getObject();

						if (event.getEventAction().equals(
								SelectEvent.LEFT_CLICK)) {
							System.out
									.println("j'ai touche le Placemark !!!!!!! negga !!!! ");
							String split[] = point.getLabelText().split("\\s+");

							System.out.println("l'ip est "
									+ split[split.length - 1].toString());

						}
						if (event.getEventAction().equals(
								SelectEvent.RIGHT_CLICK)) {

							System.out
									.println("bye bye ! je suis plus sur le placemark");

						}
					}
				}
			});

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

		public double getDistance(Position pos1, Position pos2) {
			// source : falculté des sciences : Explication
			// http://www.ipnas.ulg.ac.be/garnir/donneesGPS/TexteTP_calcul.pdf
			// code source (��� enlever) :
			// http://dotclear.placeoweb.com/post/Formule-de-calcul-entre-2-points-wgs84-pour-calculer-la-distance-qui-separe-ces-deux-points

			// r
			int r = 6371;

			long tempsT1;
			long tempsT2;

			// POINT DE DEPART
			double lat1 = Math.toRadians(pos1.getLatitude().getDegrees());
			;
			double lon1 = Math.toRadians(pos1.getLongitude().getDegrees());

			// POINT D'ARRIVE
			double lat2 = Math.toRadians(pos2.getLatitude().getDegrees());
			double lon2 = Math.toRadians(pos2.getLongitude().getDegrees());

			tempsT1 = System.nanoTime();
			double distance = distanceVolOiseauEntre2PointsAvecPrecision(lat1,
					lon1, lat2, lon2);
			tempsT2 = System.nanoTime();

			double distanceEnKm = distance * r;

			tempsT1 = System.nanoTime();
			tempsT2 = System.nanoTime();

			System.out.println("Temps (SansPrecision) : "
					+ String.format("%10d", (tempsT2 - tempsT1)) + " ns");

			return distanceEnKm;
		}

		public static double distanceVolOiseauEntre2PointsAvecPrecision(
				double lat1, double lon1, double lat2, double lon2) {

			return 2 * Math.asin(Math.sqrt(Math.pow(
					(Math.sin((lat1 - lat2) / 2)), 2)
					+ Math.cos(lat1) * Math.cos(lat2) *

					(Math.pow(Math.sin(((lon1 - lon2) / 2)), 2))));
		}

	}

}
