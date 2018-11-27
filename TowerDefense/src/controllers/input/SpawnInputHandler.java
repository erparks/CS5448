package controllers.input;

import models.Model;

/**
 * Handles the command line input for spawn.
 * 
 * @author Ethan Parks
 *
 */
public class SpawnInputHandler extends InputHandler {

	/**
	 * The model to update.
	 */
	private Model model;
	/**
	 * THe controller for the command line interface.
	 */
	private InputController inputController;

	/**
	 * Create new input handler for spawn command.
	 * 
	 * @param userInput       The string from the command line.
	 * @param model           The model to update.
	 * @param inputController The controller for the command line interface.
	 */
	public SpawnInputHandler(String userInput, Model model, InputController inputController) {
		super(userInput);
		this.model = model;
		this.inputController = inputController;
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {
		if (splits.length != 3)
			return true;

		if (!checkAdmin(inputController.getMode()))
			return true;

		if (!splits[1].equalsIgnoreCase("air") && !splits[1].equalsIgnoreCase("ground"))
			return true;

		try {
			if (Integer.parseInt(splits[2]) < 0)
				return true;
		} catch (NumberFormatException e) {
			return true;
		}

		return false;
	}

	@Override
	protected void printUsage() {
		System.out.println("Spawn usage: spawn <type> <count>");
		System.out.println("Types: Air, Ground");
		System.out.println("Count: integer > 0");
	}

	/**
	 * Add the indicated enemies to the unspawned enemies stack.
	 */
	@Override
	protected void respondToInput(String[] splits) {
		model.addUnspawnedEnemies(splits[1], Integer.parseInt(splits[2]));
	}

}
