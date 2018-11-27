package models;

import java.awt.Dimension;
import java.awt.Point;

/**
 * The base projectile object for the decorator design pattern.
 * @author Ethan Parks
 */
public class PlainProjectile extends Projectile {

	/**
	 * Damage of the base projectile
	 */
	private static final int DAMAGE = 10;
	
	/**
	 * Create base projectile for the decorator design pattern.
	 * @param location Starting X, Y coordinates for the projectile
	 * @param speed Distance to move the projectile every frame.
	 * @param target Enemy to move the projectile toward.
	 * @param dimension Size of the projectile
	 */
	public PlainProjectile(Point.Double location, double speed, Enemy target, Dimension dimension) {
		super(location, dimension, speed);
		setSpeed(speed);
		setTarget(target);
		setExploded(false);
	}

	/**
	 * Apply damage to the target.
	 */
	@Override
	public void explode() {
		getTarget().applyDamage(DAMAGE);
		setExploded(true);
	}

	/**
	 * Returns a copy of this projectile for the prototype design pattern.
	 * @return a copy of this projectile.
	 */
	@Override
	public Projectile clone() {
		return new PlainProjectile(getLocation(), getSpeed(), getTarget(), getDimension());
	}



}
