package controllers;

import models.*;


/**
 * Class to hold the Add tower command for the command design pattern.
 * @author Ethan Parks
 */
public class AddTower implements Command {

	/**
	 * Tower to add/undo
	 */
	private Tower tower;
	
	
	/**
	 * Model to add or remove tower from
	 */
	private Model model;
	
	/**
	 * Adds the given tower when executed
	 * @param tower Tower to add to the model
	 * @param model Model to add the tower to
	 */
	public AddTower(Tower tower, Model model)
	{
		this.tower = tower;
		this.model = model;
	}
	
	
	/** 
	 * Adds the tower to the model and subtracts the cost from the currency
	 */
	@Override
	public void execute() {
		model.addTower(tower);
		model.addCurrency(-Model.TOWER_COST);
	}

	/**
	 * Removes tower from model and restores the currency
	 */
	@Override
	public void undo() {
		tower.getProjectiles().clear();
		model.removeTower(tower);
		model.addCurrency(Model.TOWER_COST);
	}

}
