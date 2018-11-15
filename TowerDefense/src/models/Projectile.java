package models;

import java.awt.Dimension;
import java.awt.geom.Point2D.Float;

public abstract class Projectile extends Mobile{

	private Enemy target;
	private boolean exploded;
	
	public Projectile(Float.Double location, Dimension dimension, double speed) {
		super(location, dimension, speed);
	}
	
	public abstract void explode();
	public abstract Projectile clone();

	
	@Override
	public void updateLocation() {

		setLocation(stepLocation(getTarget().getLocation().x, getTarget().getLocation().y, getLocation().x, getLocation().y, getSpeed()));

		if(intersectsRectangle(getTarget())) 
			explode();
		
	}
	

	public Enemy getTarget() {
		return target;
	}

	public void setTarget(Enemy target) {
		this.target = target;
	}

	public boolean isExploded() {
		return exploded;
	}

	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}
}
