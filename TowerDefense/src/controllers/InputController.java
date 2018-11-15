package controllers;

import java.util.Scanner;

import models.Model;

public class InputController {

	private enum Mode {
		USER, ADMIN
	}

	private Model model;
	private Mode mode;

	public InputController(Model model) {
		this.model = model;
		this.mode = Mode.USER;

		// Spin thread to listen for user input
		Thread inputThread = new Thread(new Runnable() {

			public void run() {

				Scanner input = new Scanner(System.in);
				String userInput;

				while (!(userInput = input.nextLine()).equals("Exit")) {
					if (userInput.toLowerCase().startsWith("spawn "))
						spawnInputHandler(userInput);
					else if (userInput.toLowerCase().startsWith("add ") || userInput.toLowerCase().startsWith("remove "))
						currencyInputHandler(userInput);
					else if (userInput.toLowerCase().startsWith("switch "))
						switchInputHandler(userInput);
					else if(userInput.toLowerCase().equals("new"))
						newInputHandler();
					else
						System.out.println("Command not understood");

				}

				System.exit(0);
			}
		});

		inputThread.start();
	}
	
	private void newInputHandler() {
		model.reset();
	}

	private void switchInputHandler(String userInput) {

		String[] splits = userInput.split("\\s+");

		if (splits.length != 2) {
			printSwitchUsage();
			return;
		}

		switch (splits[1].toLowerCase()) {
		case "user":
			mode = Mode.USER;
			break;
		case "admin":
			mode = Mode.ADMIN;
			break;
		default:
			printSwitchUsage();
		}
	}

	private void printSwitchUsage() {
		System.out.println("switch useage: switch <role>");
		System.out.println("switch: user|admin");
	}

	private void currencyInputHandler(String userInput) {

		String[] splits = userInput.split("\\s+");

		if (isInvalidCurrencyCommand(splits)) {
			printCurrencyUsage();
			return;
		}
		
		

		model.addCurrency(getCurrencySign(splits[0]) * Integer.parseInt(splits[1]));
	}

	private int getCurrencySign(String string) {
		return string.equalsIgnoreCase("add") ? 1 : -1;
	}

	private boolean isInvalidCurrencyCommand(String[] input) {

		if (!checkAdmin())
			return true;

		if (input.length != 2)
			return true;

		try {
			Integer.parseInt(input[1]);
		} catch (NumberFormatException e) {
			return true;
		}
		
		return false;
	}

	private void printCurrencyUsage() {
		System.out.println("currency useage: currency <amout>");
		System.out.println("amout: integer");
	}

	private void spawnInputHandler(String userInput) {

		String[] splits = userInput.split("\\s+");

		if (isInvalidSpawnCommand(splits)) {
			printSpawnUsage();
			return;
		}

		model.addUnspawnedEnemies(splits[1], Integer.parseInt(splits[2]));

	}

	private boolean isInvalidSpawnCommand(String[] input) {

		if (!checkAdmin())
			return true;

		if (input.length != 3)
			return true;

		if (!input[1].equalsIgnoreCase("air") && !input[1].equalsIgnoreCase("ground"))
			return true;

		try {
			if (Integer.parseInt(input[2]) < 0)
				return true;
		} catch (NumberFormatException e) {
			return true;
		}

		return false;

	}

	private void printSpawnUsage() {
		System.out.println("Spawn usage: spawn <type> <count>");
		System.out.println("Types: Air, Ground");
		System.out.println("Count: integer > 0");
	}

	private boolean checkAdmin() {

		if (mode == Mode.ADMIN)
			return true;

		System.out.println("Admin command only");
		return false;
	}
}
