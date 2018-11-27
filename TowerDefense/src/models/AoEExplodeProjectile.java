package models;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

/**
 * Projectile decorator which causes all enemies near the target to take damage.
 * 
 * @author Ethan Parks
 * 
 * 
 */
public class AoEExplodeProjectile extends ProjectileDecorator {

	/**
	 * Distance from the target entity within which other enemies are affected.
	 */
	private static final int AOE_SIZE = 100;
	/**
	 * Damage taken by enemies within the AOE_SIZE.
	 */
	private static final int AOE_DAMAGE = 5;

	/**
	 * Enemies within the game.
	 */
	private List<Enemy> enemies;

	/**
	 * Create new decorator which causes AoE explosions.
	 * @param baseProjectile Projectile to which this is being added as a decorator.
	 * @param enemies        Enemies within the game.
	 */
	public AoEExplodeProjectile(Projectile baseProjectile, List<Enemy> enemies) {
		super(baseProjectile);
		this.enemies = enemies;
	}

	/**
	 * Apply damage to all near by enemies.
	 * 
	 * Called when the projectile reaches the target enemy.
	 */
	public void explode() {
		super.explode();

		for (Iterator<Enemy> enemy_it = enemies.iterator(); enemy_it.hasNext();) {
			Enemy e = enemy_it.next();

			if (Point2D.distance(e.getLocation().x, e.getLocation().y, getLocation().x, getLocation().y) < AOE_SIZE) {
				e.applyDamage(AOE_DAMAGE);
			}
		}

		setExploded(true);
	}

	/**
	 * Returns a copy of this projectile. Used for the protype design pattern.
	 * 
	 * @return A copy of this projectile
	 */
	public AoEExplodeProjectile clone() {
		return new AoEExplodeProjectile(getProjectile().clone(), enemies);
	}

	/**
	 * Sets the target of this projectile.
	 */
	public void setTarget(Enemy e) {
		super.setTarget(e);
		getProjectile().setTarget(e);
	}
}
