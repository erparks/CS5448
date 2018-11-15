package models;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

public abstract class Mobile extends GameRectangle {

	private double speed;
	
	public Mobile(Point.Double location, Dimension dimension,  double speed) {
		super(location, dimension);
		this.speed = speed;
	}

	public abstract void updateLocation();

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

	private double getMagnitude(Point.Double vector) {
		return Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

}
