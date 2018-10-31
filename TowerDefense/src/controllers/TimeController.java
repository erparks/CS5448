package controllers;

import java.util.TimerTask;

public class TimeController extends TimerTask {

	private Controller controller;

	public TimeController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void run() {
		controller.update();
	}

}
