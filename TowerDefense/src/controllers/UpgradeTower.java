package controllers;

import models.ProjectileDecorator;
import models.Tower;

/**
 * User command to upgrade a tower.
 * @author Ethan Parks
 * 
 */
public class UpgradeTower implements Command {

	/**
	 * New projectile for the tower.
	 */
	private ProjectileDecorator projectile;
	/**
	 * Tower being upgraded.
	 */
	private Tower tower;
	
	/**
	 * Create new UpgradeTower command to be executed by the UserCommands class.
	 * @param projectile New projectile for the tower.
	 * @param tower Tower being upgraded.
	 */
	public UpgradeTower(ProjectileDecorator projectile, Tower tower) {
		this.projectile = projectile;
		this.tower = tower;
	}
	
	/**
	 * Upgrade the tower.
	 */
	@Override
	public void execute() {
		System.out.println("Upgrading Tower");
		tower.setProjectile(projectile);
	}

	/**
	 * Set the towers projectile back to the previous state.
	 */
	@Override
	public void undo() {
		tower.setProjectile(projectile.getProjectile());
	}
}











