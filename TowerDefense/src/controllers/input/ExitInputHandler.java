package controllers.input;

/**
 * Handles command line input to exit to program.
 * @author Ethan Parks
 *
 */
public class ExitInputHandler extends InputHandler {

	/**
	 * Create new input handler for exit command.
	 * @param userInput String received from command line.
	 */
	public ExitInputHandler(String userInput) {
		super(userInput);
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {
		return false;
	}

	@Override
	protected void printUsage() {

	}

	/**
	 * Closes the program.
	 */
	@Override
	protected void respondToInput(String[] splits) {
		System.exit(0);
	}

}
