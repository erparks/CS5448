package controllers.input;

import models.Model;

/**
 * Factory to return the appropriate InputHandler
 * @author Ethan Parks
 */
public class InputHandlerFactory {

	/**
	 * Unused.
	 */
	private InputHandlerFactory() {

	}

	/**
	 * Returns the appropriate InputHandler based on the user input.
	 * @param input String received from the command line.
	 * @param model Model to update.
	 * @param inputController Controller which received the command line input.
	 * @return The appropriate InputHandler based on the user input.
	 */
	public static InputHandler GetInputHandler(String input, Model model, InputController inputController) {
		
		if (input.toLowerCase().startsWith("spawn "))
			return new SpawnInputHandler(input, model, inputController);

		else if (input.toLowerCase().startsWith("add ") || input.toLowerCase().startsWith("remove "))
			return new CurrencyInputHandler(input, model, inputController);
		
		else if (input.toLowerCase().startsWith("switch "))
			return new SwitchInputHandler(input, inputController);
		
		else if (input.toLowerCase().equals("new"))
			return new NewInputHandler(input, model);
		
		else if (input.toLowerCase().startsWith("save"))
			return new SaveInputHandler(input, model);
		
		else if (input.toLowerCase().startsWith("load"))
			return new LoadInputHandler(input, inputController);
		
		else if (input.toLowerCase().startsWith("highscores"))
			return new HighScoreInputHandler(input, model.getHighscoresFile());
		
		else if (input.toLowerCase().startsWith("exit"))
			return new ExitInputHandler(input);

		
		return new NullInputHandler(input);

	}
}
