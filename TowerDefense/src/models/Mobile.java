package models;

import java.awt.geom.Point2D;

public abstract class Mobile {
	private float x;
	private float y;
	
	public abstract void updateLocation();
	
	protected Point2D.Float stepLocation(float x1, float y1, float x2, float y2, float speed) {
		Point2D.Float between = new Point2D.Float(x1 - x2, y1 - y2);

		float magnitude = (float) Math.sqrt(between.getX() * between.getX() + between.getY() * between.getY());

		between.setLocation(between.getX() / magnitude, between.getY() / magnitude);

		Point2D.Float p = new Point2D.Float((float) (x2 + speed * between.getX()),
				(float) (y2 + speed * between.getY()));
		return p;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
}
