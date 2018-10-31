package models;

import java.awt.Point;
import java.util.ArrayList;

public class Tower {

	private Point location;
	private float range;
	private float timeOfLastShoot;
	private float shootCooldown;
	private Projectile projectile;
	private ArrayList<Projectile> projectiles;

	public Tower(Point location) {
		this.location = location;
		this.shootCooldown = 1000;
		this.timeOfLastShoot = -this.shootCooldown;
		this.range = 150;
		this.projectile = new PlainProjectile(location.x, location.y, 2, null);
		this.projectiles = new ArrayList<Projectile>();
	}

	public void fire(Enemy e) {
		Projectile p = projectile.clone();
		p.setTarget(e);
		projectiles.add(p);
//		System.out.println("cloned proj at x=" + bp.getX());
//		System.out.println("org proj at x=" + projectile.getX());
//		System.out.println("Proj in flight: " + projectiles.size());
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

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
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
