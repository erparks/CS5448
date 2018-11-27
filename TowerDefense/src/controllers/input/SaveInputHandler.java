package controllers.input;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import models.Model;

/**
 * Handles command line input for saving.
 * 
 * @author Ethan Parks
 *
 */
public class SaveInputHandler extends InputHandler {

	private Model model;

	/**
	 * Create new input handler for save command.
	 * 
	 * @param userInput The string from the command line.
	 * @param model     THe model to save.
	 */
	public SaveInputHandler(String userInput, Model model) {
		super(userInput);
		this.model = model;
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {
		return splits.length != 2;
	}

	@Override
	protected void printUsage() {
		System.out.println("save usage: save <file name>");
	}

	/**
	 * Save the given model class.
	 */
	@Override
	protected void respondToInput(String[] splits) {
		try {
			FileOutputStream file = new FileOutputStream(splits[1]);
			ObjectOutputStream out = new ObjectOutputStream(file);

			out.writeObject(model);

			out.close();
			file.close();
		}

		catch (IOException ex) {

		}
	}
}
