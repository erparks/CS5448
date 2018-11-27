package models;

import java.awt.Dimension;
import java.awt.geom.Point2D.Float;

/**
 * Abstract class used for the decorator design pattern for projectiles.
 * @author Ethan Parks
 */
public abstract class Projectile extends Mobile {

	/**
	 * Enemy towards which the projectile moves.
	 */
	private Enemy target;
	/**
	 * True if the projectile has reached the target.
	 */
	private boolean exploded;
	
	/**
	 * Create projectile with the given location, dimension, and speed.
	 * @param location Screen coordinates of the projectile
	 * @param dimension Size of the projectile
	 * @param speed Distance to move the projectile each frame.
	 */
	public Projectile(Float.Double location, Dimension dimension, double speed) {
		super(location, dimension, speed);
	}
	
	/**
	 * Called when the prjectile reaches the target.
	 */
	public abstract void explode();
	
	/**
	 * Returns a copy of the projectile
	 * @return A copy of the projectile
	 */
	public abstract Projectile clone();

	/**
	 * Move the projectile towards the updating target location.
	 * 
	 * If the projectile intersets the target, call explode()
	 */
	@Override
	public void updateLocation() {

		setLocation(stepLocation(getTarget().getLocation().x, getTarget().getLocation().y, getLocation().x, getLocation().y, getSpeed()));

		if(intersectsRectangle(getTarget())) 
			explode();
		
	}
	

	/**
	 * Returns the enemy the projectile is moving towards
	 * @return The enemy the projectile is moving towards
	 */
	public Enemy getTarget() {
		return target;
	}
	/**
	 * Set the enemy the projectile is moving towards
	 * @param target The new enemy the projectile is moving towards
	 */
	public void setTarget(Enemy target) {
		this.target = target;
	}

	/**
	 * Returns true if the projectile has called explode.
	 * @return True if the projectile has called explode.
	 */
	public boolean isExploded() {
		return exploded;
	}

	/**
	 * Set the value of exploded.
	 * @param exploded New value of exploded.
	 */
	public void setExploded(boolean exploded) {
		this.exploded = exploded;
	}
}
