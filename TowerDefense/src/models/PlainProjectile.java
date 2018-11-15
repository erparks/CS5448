package models;

import java.awt.Dimension;
import java.awt.Point;

public class PlainProjectile extends Projectile {

	private static final int DAMAGE = 10;
	
	public PlainProjectile(Point.Double location, double speed, Enemy target, Dimension dimension) {
		super(location, dimension, speed);
		setSpeed(speed);
		setTarget(target);
		setExploded(false);
	}

	@Override
	public void explode() {
		getTarget().applyDamage(DAMAGE);
		setExploded(true);
	}

	@Override
	public Projectile clone() {
		return new PlainProjectile(getLocation(), getSpeed(), getTarget(), getDimension());
	}



}
