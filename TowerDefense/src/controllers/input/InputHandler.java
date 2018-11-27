package controllers.input;

import controllers.input.InputController.Mode;

/**
 * Handles user command line input.
 * 
 * @author Ethan Parks
 * 
 */
public abstract class InputHandler {

	/**
	 * The string from the command line.
	 */
	private String userInput;

	/**
	 * Create new input handler.
	 * 
	 * @param userInput The string from the command line.
	 */
	public InputHandler(String userInput) {
		this.userInput = userInput;
	}

	/**
	 * If valid, execute the user command.
	 */
	public void handleInput() {
		String[] splits = userInput.split("\\s+");

		if (isInvalidInput(splits)) {
			printUsage();
			return;
		}

		respondToInput(splits);
	}

	/**
	 * Validates the user input.
	 * 
	 * @param splits User input string split by spaces.
	 * @return True if the user input was not valid.
	 */
	protected abstract boolean isInvalidInput(String[] splits);

	/**
	 * Print proper usage of this command.
	 */
	protected abstract void printUsage();

	/**
	 * Respond to user input.
	 * 
	 * @param splits User input string split by spaces.
	 */
	protected abstract void respondToInput(String[] splits);

	/**
	 * True if the user is currently in admin mode.
	 * 
	 * @param mode Current user mode.
	 * @return True if the user is currently in admin mode. False otherwise.
	 */
	protected boolean checkAdmin(InputController.Mode mode) {

		if (mode == Mode.ADMIN)
			return true;

		System.out.println("Admin command only");
		return false;
	}
}
