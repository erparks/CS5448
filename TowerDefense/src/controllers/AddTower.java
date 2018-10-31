package controllers;

import models.*;

public class AddTower implements Command {

	private Tower tower;
	private Model model;
	
	public AddTower(Tower tower, Model model)
	{
		this.tower = tower;
		this.model = model;
	}
	
	@Override
	public void execute() {
		model.addTower(tower);
		model.spendCurrency(model.getTowerCost());
	}

	@Override
	public void undo() {
		tower.getProjectiles().clear();
		model.removeTower(tower);
		model.addCurrency(model.getTowerCost());
	}

}
