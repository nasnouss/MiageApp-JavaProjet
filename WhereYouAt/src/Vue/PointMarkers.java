package Vue;

import gov.nasa.worldwind.WorldWind;
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
import Controleur.Coordonnees;
import Controleur.TraceRouteConsommateur;
import Graphe.Arc;
import Graphe.Sommet;

public class PointMarkers extends ApplicationTemplate {
	@SuppressWarnings("serial")
	public static class AppFrame extends ApplicationTemplate.AppFrame {

		private static RenderableLayer layer;
		private static AppFrame app;
		protected LengthMeasurer measurer = new LengthMeasurer();
		protected boolean followTerrain = true;
		public final static int GREAT_CIRCLE = WorldWind.GREAT_CIRCLE;
		protected int pathType = GREAT_CIRCLE;

		/**
		 * Affiche le globe 
		 */
		public AppFrame() {
			super(true, true, false);
			app = this;
			layer = new RenderableLayer();
			this.measurer.setFollowTerrain(this.followTerrain);
			this.measurer.setPathType(this.pathType);
			insertAfterPlacenames(getWwd(), layer);
//			insertBeforeCompass(getWwd(), layer);
//			this.getLayerPanel().update(this.getWwd());


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
							String split[] = point.getLabelText().split("\\s+");
							Arc.getVoisin(
									TraceRouteConsommateur.getGraphe(),
									new Sommet(split[split.length - 1]
											.toString()));

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

		/**
		 * 
		 * @return une instance vers le globe de l'application
		 */
		public static AppFrame getAppFrame() {
			return app;
		}

		/**
		 * Ajoute un point sur le globe grace au coordonnées GPS du point
		 * @param c coordonnées du point que l'on veut placer sur le globe
		 */
		public void AjouterCoordonnees(Coordonnees c) {
			// Creating positions where the placemarkers will be placed.
			Position pointPosition = Position.fromDegrees(c.getLatitude(),
					c.getLongitude());
			// Creating markers providing the positions previously defined
			PointPlacemark pmStandard = new PointPlacemark(pointPosition);

			// Creating new placemark attributes
			PointPlacemarkAttributes pointAttributeBlue = new PointPlacemarkAttributes();
			pmStandard.setAttributes(pointAttributeBlue);
			pointAttributeBlue.setImageColor(c.getCouleur()); // On choisir la
			// couleur
			pmStandard.setLabelText(c.getSite() + " " + c.getIp());

			// Adding placemarkers to the rederable layer
			layer.addRenderable(pmStandard);
			this.getWwd().redraw();

			// Adding renderable layer to the application.
			// insertBeforeCompass(getWwd(), layer);
			// this.getLayerPanel().update(this.getWwd());
			//this.getLayerPanel().update(this.getWwd());


		}

		/**
		 * Dessine une ligne entre deux sommets
		 * @param lst liste de deux sommets 
		 * @param color couleur de la ligne entre les deux sommets
		 */
		public void drawline(ArrayList<Position> lst, Color color) {
			Polyline path = new Polyline(lst);
			path.setFollowTerrain(this.followTerrain);
			path.setColor(color);
			layer.addRenderable(path);
			//insertBeforeCompass(getWwd(), layer);

			//this.getLayerPanel().update(this.getWwd());
		}

	}

	/**
	 * Calcule la distance à vol d'oisieau entre deux points grace a la latitude et longitude
	 * @param lat1 latitude du point 1
	 * @param lon1 longitude du point 1
	 * @param lat2 latitude du point 2
	 * @param lon2 longitude du point 2
	 * @return la distance entre deux points
	 */
	public static double distanceVolOiseauEntre(
			double lat1, double lon1, double lat2, double lon2) {

		return 2 * Math.asin(Math.sqrt(Math.pow((Math.sin((lat1 - lat2) / 2)),
				2) + Math.cos(lat1) * Math.cos(lat2) *

		(Math.pow(Math.sin(((lon1 - lon2) / 2)), 2))));
	}

	/**
	 * Calcule la distance entre deux positions
	 * @param pos1 position du premier point
	 * @param pos2 position du second point
	 * @return la distance entre deux positions
	 */
	public static double getDistance(Position pos1, Position pos2) {
		// source : falculté des sciences : Explication
		// http://www.ipnas.ulg.ac.be/garnir/donneesGPS/TexteTP_calcul.pdf
		// http://dotclear.placeoweb.com/post/Formule-de-calcul-entre-2-points-wgs84-pour-calculer-la-distance-qui-separe-ces-deux-points

		// r
		int r = 6371;

	

		// POINT DE DEPART
		double lat1 = Math.toRadians(pos1.getLatitude().getDegrees());
		;
		double lon1 = Math.toRadians(pos1.getLongitude().getDegrees());

		// POINT D'ARRIVE
		double lat2 = Math.toRadians(pos2.getLatitude().getDegrees());
		double lon2 = Math.toRadians(pos2.getLongitude().getDegrees());

		
		double distance = distanceVolOiseauEntre(lat1,
				lon1, lat2, lon2);


		double distanceEnKm = distance * r;


		return distanceEnKm;
	}

}
