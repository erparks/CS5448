package controllers.input;

/**
 * Handle command line input for switch.
 * 
 * @author Ethan Parks
 */
public class SwitchInputHandler extends InputHandler {

	/**
	 * Controller listening for user command line input.
	 */
	private InputController inputController;

	/**
	 * Create new input handler for switch command.
	 * 
	 * @param userInput       String received from the command line.
	 * @param inputController Controller listening for user command line input.
	 */
	public SwitchInputHandler(String userInput, InputController inputController) {
		super(userInput);
		this.inputController = inputController;
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {

		if (splits.length != 2)
			return true;

		if (!splits[1].equalsIgnoreCase("user") && !splits[1].equalsIgnoreCase("admin"))
			return true;

		return false;
	}

	@Override
	protected void printUsage() {
		System.out.println("switch useage: switch <role>");
		System.out.println("switch: user|admin");
	}

	/**
	 * Switch the user mode to the requested user/admin mode.
	 */
	@Override
	protected void respondToInput(String[] splits) {

		switch (splits[1].toLowerCase()) {
		case "user":
			inputController.setMode(InputController.Mode.USER);
			break;
		case "admin":
			inputController.setMode(InputController.Mode.ADMIN);
			break;
		}
	}

}
