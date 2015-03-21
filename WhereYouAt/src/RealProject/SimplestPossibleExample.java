package RealProject;
import java.awt.Color;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;

import javax.swing.*;

/** This example demonstrates the simplest possible way
to create a WorldWind application. */
public class SimplestPossibleExample extends JFrame
{
	public SimplestPossibleExample()
	{
		WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
		wwd.setPreferredSize(new java.awt.Dimension(1000, 800));
		this.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
		wwd.setModel(new BasicModel());
		
	}
	
	public void startt(final double latitude, final double longitude){
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				JFrame frame = new SimplestPossibleExample();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
				Position pointPosition = Position.fromDegrees(latitude, longitude);
				PointPlacemark pmStandard = new PointPlacemark(pointPosition);
				pmStandard.setLabelText("Standard placemark.");
				PointPlacemarkAttributes pointAttributeRed = new PointPlacemarkAttributes();
		        pointAttributeRed.setImageColor(Color.red);
		        pmStandard.setAttributes(pointAttributeRed);
		        RenderableLayer layer = new RenderableLayer();
		           //Adding placemarkers to the rederable layer
		           layer.addRenderable(pmStandard);
		           //layer.addRenderable(pmRed);
		           //layer.addRenderable(pmBlue);
		           //Adding renderable layer to the application.
		       



			}
		});
	}

	
}