package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Game entity which creates projectiles which target nearby enemies.
 * @author Ethan Parks
 */
public class Tower extends GameRectangle {

	/**
	 * Maximum distance between turret and enemy for the tower to fire.
	 */
	private final static float TOWER_RANGE = 150;
	
	/**
	 * Amount of time which must pass between shooting
	 */
	private final static float SHOOT_COOLDOWN = 1000;
	
	/**
	 * Last time this tower shot.
	 */
	private float timeOfLastShoot;
	
	/**
	 * Projectile which is copied when shooting using the prototype design pattern.
	 */
	private Projectile projectile;
	/**
	 * List of active projectiles originating at the tower.
	 */
	private ArrayList<Projectile> projectiles;

	/**
	 * Create new tower at given location.
	 * 
	 * @param location Screen coordinates of the tower.
	 */
	public Tower(Point.Double location) {
		super(location, new Dimension(Model.TOWER_SIZE, Model.TOWER_SIZE));

		setLocation(location);

		this.timeOfLastShoot = -this.SHOOT_COOLDOWN;
		this.projectile = new PlainProjectile(getCenter(), 2.0, null,
				new Dimension(Projectile.DEFAULT_PROJECTILE_SIZE, Projectile.DEFAULT_PROJECTILE_SIZE));
		this.projectiles = new ArrayList<Projectile>();
	}

	/**
	 * Create a new projectile with the given target.
	 * @param e Target for the projectile to seek.
	 */
	public void fire(Enemy e) {
		Projectile p = projectile.clone();
		p.setTarget(e);
		
		projectiles.add(p);
	}

	/**
	 * Check if enough time has passed and an enemy is close enought to fire at. If so, fire.
	 * @param e Enemy to attempt a shot at.
	 * @param time Current game time.
	 */
	public void attemptShotAt(Enemy e, long time) {
		if (time - timeOfLastShoot < SHOOT_COOLDOWN)
			return;

		if (Point2D.distance(getLocation().x, getLocation().y, e.getLocation().x, e.getLocation().y) < TOWER_RANGE) {
			timeOfLastShoot = time;
			fire(e);
		}
	}

	/**
	 * Returns the projectile used by this tower.
	 * @return The projectile used by this tower.
	 */
	public Projectile getProjectile() {
		return projectile;
	}

	/**
	 * Set the projectile used by this tower.
	 * @param projectile New projectile for this tower to use.
	 */
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

	/**
	 * Returns the list of all active projectiles originating from this tower.
	 * @return The list of all active projectiles originating from this tower.
	 */
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
}
