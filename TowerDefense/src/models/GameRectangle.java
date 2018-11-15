package models;

import java.awt.Dimension;
import java.awt.Point;

public class GameRectangle {

	private Point.Double location;
	private Dimension dimension;

	public GameRectangle(Point.Double location, Dimension dimension) {
		this.location = location;
		this.dimension = dimension;
	}

	public boolean intersectsRectangle(GameRectangle rect) {

		return intersectsPoint(rect.getUpperLeft()) || 
			   intersectsPoint(rect.getUpperRight()) || 
			   intersectsPoint(rect.getLowerRight()) || 
			   intersectsPoint(rect.getLowerLeft());
	}

	public boolean intersectsPoint(Point.Double p) {
		return ((p.x >= getLocation().x) && 
				(p.x <= getLocation().x + dimension.getWidth()) && 
				(p.y >= getLocation().y) && 
				(p.y <= getLocation().y + dimension.getHeight()));
	}

	public Point.Double getUpperLeft() {
		return location;
	}

	public Point.Double getUpperRight() {
		return new Point.Double(location.x + dimension.getWidth(), location.y);
	}

	public Point.Double getLowerLeft() {
		return new Point.Double(location.x, location.y + dimension.getHeight());
	}

	public Point.Double getLowerRight() {
		return new Point.Double(location.x + dimension.getWidth(), location.y + dimension.getHeight());
	}

	public Point.Double getCenter() {
		return new Point.Double(location.x + dimension.getWidth() / 2, location.y + dimension.getHeight() / 2);
	}

	public Point.Double getLocation() {
		return location;
	}

	public void setLocation(Point.Double location) {
		this.location = location;
	}

	public void setCenterLocation(Point.Double location) {
		this.location.x = location.x - dimension.getWidth() / 2;
		this.location.y = location.y - dimension.getHeight() / 2;
	}

	public Dimension getDimension() {
		return dimension;
	}
}
