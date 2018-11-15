package views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GameOverView {
	private JFrame frame;
	private JLabel gameOverLbl;
	private JButton playAgainBtn;
	private JButton quitBtn;
	private JPanel buttonPanel;
	
	public GameOverView() {
		frame = new JFrame("Game Over");
		frame.setSize(200, 200);
		frame.setLayout(new BorderLayout());
		
		gameOverLbl = new JLabel("Game Over", SwingConstants.CENTER);
		
		buttonPanel = new JPanel();
		
		playAgainBtn = new JButton("Play Again");
		quitBtn = new JButton("Quit");
		
		buttonPanel.setLayout(new BorderLayout());
		
		buttonPanel.add(playAgainBtn, BorderLayout.EAST);
		buttonPanel.add(quitBtn, BorderLayout.WEST);
		
		frame.add(gameOverLbl, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
	}

	public JButton getPlayAgainBtn() {
		return playAgainBtn;
	}

	public void setPlayAgainBtn(JButton playAgainBtn) {
		this.playAgainBtn = playAgainBtn;
	}

	public JButton getQuitBtn() {
		return quitBtn;
	}

	public void setQuitBtn(JButton quitBtn) {
		this.quitBtn = quitBtn;
	}
	
	public JFrame getFrame() {
		return frame;
	}
}
