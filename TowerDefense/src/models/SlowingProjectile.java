package models;

public class SlowingProjectile extends ProjectileDecorator {

	public SlowingProjectile(Projectile projectile) {
		super(projectile);
	}

	public void explode() {

		super.explode();
		System.out.println("Slow explode: " + getTarget().getSpeed() * 0.75f);
		getTarget().setSpeed(getTarget().getSpeed() * 0.75f);
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
