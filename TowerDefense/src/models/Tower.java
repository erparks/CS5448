package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Tower extends GameRectangle {

	private float range;
	private float timeOfLastShoot;
	private float shootCooldown;
	private Color color;
	private Projectile projectile;
	private ArrayList<Projectile> projectiles;

	public Tower(Point.Double location) {
		super(location, new Dimension(Model.TOWER_SIZE, Model.TOWER_SIZE));

		setLocation(location);

		this.shootCooldown = 1000;
		this.timeOfLastShoot = -this.shootCooldown;
		this.range = 150;
		this.projectile = new PlainProjectile(getCenter(), 2.0, null,
				new Dimension(Model.DEFAULT_PROJECTILE_SIZE, Model.DEFAULT_PROJECTILE_SIZE));
		this.projectiles = new ArrayList<Projectile>();
	}

	public void fire(Enemy e) {
		Projectile p = projectile.clone();
		p.setTarget(e);
		
		projectiles.add(p);
	}

	public void attemptShotAt(Enemy e, long time) {
		if (time - getTimeOfLastShoot() < getShootCooldown())
			return;

		if (Point2D.distance(getLocation().x, getLocation().y, e.getLocation().x, e.getLocation().y) < getRange()) {
			setTimeOfLastShoot(time);
			fire(e);
		}
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public void setProjectiles(ArrayList<Projectile> projectiles) {
		this.projectiles = projectiles;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public float getTimeOfLastShoot() {
		return timeOfLastShoot;
	}

	public void setTimeOfLastShoot(float timeOfLastShoot) {
		this.timeOfLastShoot = timeOfLastShoot;
	}

	public float getShootCooldown() {
		return shootCooldown;
	}

	public void setShootCooldown(float shootCooldown) {
		this.shootCooldown = shootCooldown;
	}
}
