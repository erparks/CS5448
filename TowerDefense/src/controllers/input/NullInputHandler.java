package controllers.input;

/**
 * Handles command line input for unknown commands.
 * 
 * @author Ethan Parks
 *
 */
public class NullInputHandler extends InputHandler {

	/**
	 * Create new input handler for unknown command.
	 * 
	 * @param userInput The string from the command line.
	 */
	public NullInputHandler(String userInput) {
		super(userInput);
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {
		return true;
	}

	@Override
	protected void printUsage() {
		System.out.println("Command not understood.");
	}

	@Override
	protected void respondToInput(String[] splits) {

	}

}
