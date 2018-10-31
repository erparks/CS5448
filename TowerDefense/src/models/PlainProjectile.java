package models;

public class PlainProjectile extends Projectile {

	public PlainProjectile(float x, float y, float speed, Enemy target) {
		setX(x);
		setY(y);
		setSpeed(speed);
		setTarget(target);
		setExploded(false);
	}

	@Override
	public void explode() {
		System.out.println("normal explosion");
		getTarget().applyDamage(10);
		setExploded(true);
	}

	@Override
	public Projectile clone() {
		return new PlainProjectile(getX(), getY(), getSpeed(), getTarget());
	}



}
