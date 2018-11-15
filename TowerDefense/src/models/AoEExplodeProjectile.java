package models;

import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;

public class AoEExplodeProjectile extends ProjectileDecorator {

	private static final int AOE_SIZE = 200;
	private static final int AOE_DAMAGE = 5;
	
	private List<Enemy> enemies;
	
	public AoEExplodeProjectile(Projectile baseProjectile, List<Enemy> enemies) {
		super(baseProjectile);
		this.enemies = enemies;
	}


	public void explode() {
		super.explode();

		for(Iterator<Enemy> enemy_it = enemies.iterator(); enemy_it.hasNext();)
		{
			Enemy e = enemy_it.next();
			
			if(Point2D.distance(e.getLocation().x, e.getLocation().y, getLocation().x, getLocation().y) < AOE_SIZE) {
				e.applyDamage(AOE_DAMAGE);
			}
		}
		
		setExploded(true);
	}
	
	public AoEExplodeProjectile clone()
	{
		return new AoEExplodeProjectile(getProjectile().clone(), enemies);
	}
	
	public void setTarget(Enemy e) {
		super.setTarget(e);
		getProjectile().setTarget(e);
	}
}
