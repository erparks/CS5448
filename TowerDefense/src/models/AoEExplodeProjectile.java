package models;

public class AoEExplodeProjectile extends ProjectileDecorator {

	public AoEExplodeProjectile(Projectile baseProjectile) {
		super(baseProjectile);
	}


	public void explode() {
		super.explode();
		System.out.println("AoE Explosion");
		setExploded(true);
	}
	
	public AoEExplodeProjectile clone()
	{
		return new AoEExplodeProjectile(getProjectile().clone());
	}
	
	public void setTarget(Enemy e) {
		super.setTarget(e);
		getProjectile().setTarget(e);
	}
}
