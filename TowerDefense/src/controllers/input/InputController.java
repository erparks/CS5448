package controllers.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import controllers.Controller;
import models.Model;

/**
 * Starts a new thread and responds to user command line input.
 * @author Ethan Parks
 */
public class InputController {

	/**
	 * Signals if the user is in user or admin mode.
	 */
	public static enum Mode {
		USER, ADMIN
	}

	/**
	 * Current user/admin role of the user.
	 */
	private Mode mode;
	/**
	 * Main controller.
	 */
	private Controller controller;
	/**
	 * This class. Used to pass to the InputHandlerFactory in the running thread.
	 */
	private InputController inputController;

	/**
	 * Starts new thread and responds to user command line input.
	 * @param model Model being affected by user input.
	 * @param controller Main controller.
	 */
	public InputController(Model model, Controller controller) {

		this.mode = Mode.USER;
		this.controller = controller;
		this.inputController = this;

		// Spin thread to listen for user input
		Thread inputThread = new Thread(new Runnable() {

			public void run() {

				Scanner input = new Scanner(System.in);
				String userInput;

				while (true) {
					userInput = input.nextLine();

					InputHandlerFactory.GetInputHandler(userInput, model, inputController).handleInput();
				}
			}

		});

		inputThread.start();
	}

	/**
	 * Set Input Controller mode.
	 * @param mode new mode value.
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	 * Set the model in the main controller
	 * @param model new Model.
	 */
	public void setModel(Model model) {
		controller.setModel(model);
	}

	/**
	 * Returns current mode.
	 * @return Returns current mode.
	 */
	public Mode getMode() {
		return mode;
	}

	
}
