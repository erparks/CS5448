package models;

import java.awt.Dimension;

/**
 * Concrete class for the projectile decorator design pattern.
 * @author Ethan Parks
 */
public abstract class ProjectileDecorator extends Projectile {

	/**
	 * The base projectile
	 */
	private Projectile projectile;

	/**
	 * Create concrete class for the decorator design pattern applied to the projectiles.
	 * 
	 * @param projectile The base projectile
	 */
	public ProjectileDecorator(Projectile projectile) {
		super(projectile.getLocation(), projectile.getDimension(), projectile.getSpeed());
		this.setProjectile(projectile);
		setSpeed(projectile.getSpeed());
		setTarget(projectile.getTarget());
	}

	@Override
	public void explode() {
		getProjectile().explode();
	}


	/**
	 * Returns the base projectile.
	 * @return The base projectile.
	 */
	public Projectile getProjectile() {
		return projectile;
	}

	/**
	 * Sets the base projectile.
	 * @param projectile New base projectile.
	 */
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

}
