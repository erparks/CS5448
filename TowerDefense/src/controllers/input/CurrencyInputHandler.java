package controllers.input;

import models.Model;

/**
 * Handles command line input to add/remove currency.
 * Only available in Admin mode.
 * @author Ethan Parks
 *
 */
public class CurrencyInputHandler extends InputHandler {

	/**
	 * Model to add/remove currency to/from.
	 */
	private Model model;
	/**
	 * Controller listening for command line input.
	 */
	private InputController inputController;

	/**
	 * Create new input handler for currency command.
	 * @param userInput String received from command line.
	 * @param model Model to add/remove currency to/from.
	 * @param inputController Controller listening for command line input.
	 */
	public CurrencyInputHandler(String userInput, Model model, InputController inputController) {
		super(userInput);
		this.model = model;
		this.inputController = inputController;

	}

	@Override
	protected boolean isInvalidInput(String[] splits) {

		if (splits.length != 2)
			return true;

		if (!checkAdmin(inputController.getMode()))
			return true;

		try {
			Integer.parseInt(splits[1]);
		} catch (NumberFormatException e) {
			return true;
		}

		return false;
	}

	@Override
	protected void printUsage() {
		System.out.println("currency useage: currency <amout>");
		System.out.println("amout: integer");
	}

	/**
	 * Adds/Removes currency to/from the user.
	 */
	@Override
	protected void respondToInput(String[] splits) {

		model.addCurrency(getCurrencySign(splits[0]) * Integer.parseInt(splits[1]));
	}

	/**
	 * Returns 1 if the user input add and -1 if the user input remove.
	 * @param string First word of user input.
	 * @return 1 if the user input add and -1 if the user input remove.
	 */
	private int getCurrencySign(String string) {
		return string.equalsIgnoreCase("add") ? 1 : -1;
	}

}
