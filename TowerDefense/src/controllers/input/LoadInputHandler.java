package controllers.input;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import models.Model;

/**
 * Handles command line input for loading.
 * 
 * @author Ethan Parks
 *
 */
public class LoadInputHandler extends InputHandler {

	/**
	 * Controller for the command line input.
	 */
	private InputController inputController;

	/**
	 * Create new input handler for load command.
	 * 
	 * @param userInput String from command line.
	 * @param inputController Controller for the command line input.
	 */
	public LoadInputHandler(String userInput, InputController inputController) {
		super(userInput);
		this.inputController = inputController;
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {
		return splits.length != 2;
	}

	@Override
	protected void printUsage() {
		System.out.println("load usage: load <file name>");
	}

	/**
	 * Read the saved model class and update controller with the new model.
	 */
	@Override
	protected void respondToInput(String[] splits) {
		try {
			FileInputStream file = new FileInputStream(splits[1]);
			ObjectInputStream in = new ObjectInputStream(file);

			Model model = (Model) in.readObject();
			inputController.setModel(model);

			in.close();
			file.close();

		} catch (IOException ex) {
		} catch (ClassNotFoundException ex) {
		}

	}

}
