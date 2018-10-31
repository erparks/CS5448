package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import models.AoEExplodeProjectile;
import models.SlowingProjectile;
import models.Tower;
import views.TowerEditView;

public class TowerEditController implements ActionListener {

	private TowerEditView tev;
	private Tower tower;
	private UserCommands userCommands;
	
	public TowerEditController(Tower tower, UserCommands userCommands) {
		this.tower = tower;
		this.userCommands = userCommands;
		tev = new TowerEditView();
		tev.getConfirmBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedUpgrade = (String) tev.getComboBox().getSelectedItem();

		if (selectedUpgrade.contains("Slow")) {
			userCommands.executeCommand(new UpgradeTower(new SlowingProjectile(tower.getProjectile()), tower));
		} else if (selectedUpgrade.contains("Area")){
			userCommands.executeCommand(new UpgradeTower(new AoEExplodeProjectile(tower.getProjectile()), tower));
		}
		tev.getFrame().dispatchEvent(new WindowEvent(tev.getFrame(), WindowEvent.WINDOW_CLOSING));
	}
	

}
