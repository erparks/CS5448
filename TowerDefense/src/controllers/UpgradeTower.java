package controllers;

import models.ProjectileDecorator;
import models.Tower;

public class UpgradeTower implements Command {

	private ProjectileDecorator projectile;
	private Tower tower;
	
	public UpgradeTower(ProjectileDecorator projectile, Tower tower) {
		this.projectile = projectile;
		this.tower = tower;
	}
	
	@Override
	public void execute() {
		System.out.println("Upgrading Tower");
		tower.setProjectile(projectile);
	}

	@Override
	public void undo() {
		System.out.println("Undoing tower upgrade");
		tower.setProjectile(projectile.getProjectile());
	}

}
