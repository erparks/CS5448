package models;

public class SlowingProjectile extends ProjectileDecorator {

	private static final double SLOW_SPEED = 0.5;
	
	public SlowingProjectile(Projectile projectile) {
		super(projectile);
	}

	public void explode() {

		super.explode();
		getTarget().setSpeed(SLOW_SPEED);
		setExploded(true);
	}

	public SlowingProjectile clone() {
		return new SlowingProjectile(getProjectile().clone());
	}

	public void setTarget(Enemy e) {
		super.setTarget(e);
		getProjectile().setTarget(e);
	}
}
