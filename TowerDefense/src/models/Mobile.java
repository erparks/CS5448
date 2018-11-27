package models;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Extended by all game entities which must move. 
 * @author Ethan Parks
 */
public abstract class Mobile extends GameRectangle {

	/**
	 * Distance to be traveled in one frame.
	 */
	private double speed;
	
	/**
	 * Create new mobile object with given location, dimension, and speed.
	 * @param location Screen coordinates of the entity
	 * @param dimension Size of the entity
	 * @param speed Distance to be traveled in one frame.
	 */
	public Mobile(Point.Double location, Dimension dimension,  double speed) {
		super(location, dimension);
		this.speed = speed;
	}

	/**
	 * Move the entity and track moving destinations.
	 */
	public abstract void updateLocation();

	/**
	 * Returns new position given that the source is moving toward the destination at the given speed.
	 * 
	 * @param x1 Destination x
	 * @param y1 Destination y
	 * @param x2 Source x
	 * @param y2 Source y
	 * @param speed Distance to be moved.
	 * @return New position given that the source is moving toward the destination at the given speed.
	 */
	protected Point2D.Double stepLocation(double x1, double y1, double x2, double y2, double speed) {
		Point2D.Double between = new Point2D.Double(x1 - x2, y1 - y2);

		double magnitude = getMagnitude(between);

		if (magnitude < speed) {
			speed = magnitude;
		}

		// make between a unit vector
		between.setLocation(between.getX() / getMagnitude(between), between.getY() / getMagnitude(between));

		Point2D.Double p = new Point2D.Double(x2 + speed * between.getX(), y2 + speed * between.getY());

		return p;
	}

	/**
	 * Gets magnitude of given vector.
	 * @param vector Vector to measure the magnitude of.
	 * @return Magnitude of given vector.
	 */
	private double getMagnitude(Point.Double vector) {
		return Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
	}
	
	/**
	 * Returns the speed of the entity.
	 * @return The speed of the entity.
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Set the speed of the entity.
	 * @param speed New speed for the entity.
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
