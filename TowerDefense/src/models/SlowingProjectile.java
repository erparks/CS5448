package models;

/**
 * Decorator for the projectile which causes enemies to be slowed.
 * 
 * @author Ethan Parks
 */
public class SlowingProjectile extends ProjectileDecorator {

	/**
	 * Speed to set the enemies at after explosion
	 */
	private static final double SLOW_SPEED = 0.5;

	/**
	 * Create projectile decorator which applies a slow to enemies.
	 * 
	 * @param projectile Projectile to decorate
	 */
	public SlowingProjectile(Projectile projectile) {
		super(projectile);
	}

	/**
	 * Set the target's speed to SLOW_SPEED and call the super explode method.
	 */
	public void explode() {

		super.explode();
		getTarget().setSpeed(SLOW_SPEED);
		setExploded(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see models.ProjectileDecorator#clone()
	 */
	public SlowingProjectile clone() {
		return new SlowingProjectile(getProjectile().clone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see models.Projectile#setTarget(models.Enemy)
	 */
	public void setTarget(Enemy e) {
		super.setTarget(e);
		getProjectile().setTarget(e);
	}
}
