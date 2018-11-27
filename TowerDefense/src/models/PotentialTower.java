package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 * Representation of a potential location for a tower.
 * @author Ethan Parks
 */
public class PotentialTower extends GameRectangle {
	/**
	 * Screen coordinates of the potential tower.
	 */
	private Point.Double location;
	/**
	 * Color to draw the potential tower.
	 */
	private Color color;
	
	/**
	 * Create representation of where a tower may be placed.
	 * @param location Screen coordinates of the potential tower.
	 */
	public PotentialTower(Point.Double location)
	{
		super(location,new Dimension( Model.TOWER_SIZE, Model.TOWER_SIZE));
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see models.GameRectangle#getLocation()
	 */
	public Point.Double getLocation() {
		return location;
	}

	/* (non-Javadoc)
	 * @see models.GameRectangle#setLocation(java.awt.geom.Point2D.Double)
	 */
	public void setLocation(Point.Double location) {
		this.location = location;
	}

	/**
	 * Returns the color to draw the potential tower.
	 * @return The color to draw the potential tower.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Sets the color to draw the potential tower.
	 * @param color New color for the potential tower.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
