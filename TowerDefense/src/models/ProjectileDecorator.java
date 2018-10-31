package models;

public class ProjectileDecorator extends Projectile {

	private Projectile projectile;

	public ProjectileDecorator(Projectile projectile) {
		this.setProjectile(projectile);
		setX(projectile.getX());
		setY(projectile.getY());
		setSpeed(projectile.getSpeed());
		setTarget(projectile.getTarget());
	}

	@Override
	public void explode() {
		getProjectile().explode();
	}

	@Override
	public Projectile clone() {
		return new ProjectileDecorator(this);
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

}
