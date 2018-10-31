package models;

import java.awt.geom.Point2D;

public abstract class Projectile extends Mobile{

	private float x;
	private float y;
	private float speed;
	private Enemy target;
	private boolean exploded;
	
	public abstract void explode();

	public abstract Projectile clone();

	@Override
	public void updateLocation() {
		double dist = Point2D.distance(getX(), getY(), getTarget().getX(), getTarget().getY());
		
		setLocation(stepLocation(getTarget().getX(), getTarget().getY(), getX(), getY(), getSpeed()));
		
		if (dist <= 5) {
			explode();
		}
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public Enemy getTarget() {
		return target;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public void setLocation(Point2D.Float p) {
		this.x = (float) p.getX();
		this.y = (float) p.getY();

	}

	public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}
}
