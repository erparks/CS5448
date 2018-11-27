package controllers.input;

import models.Model;

/**
 * Handles command line input for new.
 * 
 * @author Ethan Parks
 *
 */
public class NewInputHandler extends InputHandler {

	private Model model;

	/**
	 * Create new input handler for new command.
	 * 
	 * @param userInput The string from the command line.
	 * @param model     The model to update.
	 */
	public NewInputHandler(String userInput, Model model) {
		super(userInput);
		this.model = model;
	}

	@Override
	protected boolean isInvalidInput(String[] userInput) {
		return !userInput[0].equalsIgnoreCase("new");
	}

	@Override
	protected void printUsage() {
		System.out.println("new usage: new");

	}

	/**
	 * Reset to model to a clean slate.
	 */
	@Override
	protected void respondToInput(String[] userInput) {
		model.reset();
	}

}
