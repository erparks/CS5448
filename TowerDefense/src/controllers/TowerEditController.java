package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;

import models.AoEExplodeProjectile;
import models.Enemy;
import models.SlowingProjectile;
import models.Tower;
import views.TowerEditView;

/**
 * Handles input from the Tower Edit window.
 * @author Ethan Parks
 *  
 */
public class TowerEditController implements ActionListener {

	/**
	 * View which creates the corresponding Tower Edit window.
	 */
	private TowerEditView tev;
	/**
	 * Tower being edited.
	 */
	private Tower tower;
	/**
	 * UserCommands object to track the commands originating from this window.
	 */
	private UserCommands userCommands;
	/**
	 * List of enemies in the game.
	 * 
	 * This is needed for the AoE projectile to find affected enemies when exploding.
	 */
	private List<Enemy> enemies;
	
	/**
	 * Create view for the Tower Edit window.
	 * Sets itself as the listener for the Tower Edit window.
	 * @param tower Tower being edited.
	 * @param enemies Enemies in the game.
	 * @param userCommands Usercommands object to track the commands originating from this window.
	 */
	public TowerEditController(Tower tower, List<Enemy> enemies, UserCommands userCommands) {
		this.tower = tower;
		this.enemies = enemies;
		this.userCommands = userCommands;
		tev = new TowerEditView();
		tev.getConfirmBtn().addActionListener(this);
	}
	
	/**
	 * Responds to button presses in the Tower Edit window.
	 * 
	 * When "Confirm" is pressed, the combobox in the window is read and 
	 * the projectiles for the tower are updated accordingly.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedUpgrade = (String) tev.getComboBox().getSelectedItem();

		if (selectedUpgrade.contains("Slow")) {
			userCommands.executeCommand(new UpgradeTower(new SlowingProjectile(tower.getProjectile()), tower));
		} else if (selectedUpgrade.contains("Area")){
			userCommands.executeCommand(new UpgradeTower(new AoEExplodeProjectile(tower.getProjectile(), enemies), tower));
		}
		tev.getFrame().dispatchEvent(new WindowEvent(tev.getFrame(), WindowEvent.WINDOW_CLOSING));
	}
	

}
