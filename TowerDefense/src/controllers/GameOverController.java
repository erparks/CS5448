package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import views.GameOverView;

public class GameOverController implements ActionListener {

	private GameOverView gov;
	private Controller controller;

	public GameOverController(Controller controller) {
		this.controller = controller;

		gov = new GameOverView();
		gov.getPlayAgainBtn().addActionListener(this);
		gov.getQuitBtn().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == gov.getPlayAgainBtn()) {
			controller.reset();
			gov.getFrame().dispatchEvent(new WindowEvent(gov.getFrame(), WindowEvent.WINDOW_CLOSING));
		} else if (e.getSource() == gov.getQuitBtn()) {
			System.exit(1);
		}
	}

}
