package views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Enemy;
import models.GameRectangle;
import models.PotentialTower;
import models.Tower;

public class View {
	private JFrame frame;

	private JButton pauseBtn;
	private JButton buyTowerBtn;

	private GamePanel gamePanel;
	private JPanel controlPanel;
	private JPanel topPanel;

	private JLabel currencyLbl;
	private JLabel timeLbl;
	private JLabel scoreLbl;
	private JLabel livesLbl;

	private int width;
	private int height;

	public View() {
		width = 512;
		height = 512;

		frame = new JFrame("Tower Defense");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);

		currencyLbl = new JLabel("0", JLabel.CENTER);
		timeLbl = new JLabel("00:00", JLabel.CENTER);
		
		
		scoreLbl = new JLabel("Score: 0", JLabel.CENTER);
		livesLbl = new JLabel("Lives: " , JLabel.RIGHT);
		
		pauseBtn = new JButton("Pause");
		buyTowerBtn = new JButton("Buy Tower");

		gamePanel = new GamePanel(width, height);
		controlPanel = new JPanel();
		topPanel = new JPanel();
		
		topPanel.setLayout(new BorderLayout());
		topPanel.add(scoreLbl, BorderLayout.CENTER);
		topPanel.add(livesLbl, BorderLayout.EAST);
		
		controlPanel.setLayout(new GridLayout(2, 3));
		controlPanel.add(timeLbl);
		controlPanel.add(pauseBtn);
		controlPanel.add(buyTowerBtn);
		controlPanel.add(new JPanel());
		controlPanel.add(currencyLbl);
		controlPanel.add(new JPanel());

		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(controlPanel, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}

	public JButton getPauseBtn() {
		return pauseBtn;
	}

	public JButton getBuyTowerBtn() {
		return buyTowerBtn;
	}

	public JLabel getCurrencyLbl() {
		return currencyLbl;
	}

	public JLabel getTimeLbl() {
		return timeLbl;
	}

	public JLabel getScoreLbl() {
		return scoreLbl;
	}

	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public void setTime(long millis)
	{
		timeLbl.setText(TimeUnit.MILLISECONDS.toMinutes(millis) + ":" + TimeUnit.MILLISECONDS.toSeconds(millis));
	}
	
	public void draw(List<GameRectangle> path, ArrayList<Enemy> enemies, ArrayList<Tower> towers, 
			PotentialTower potentialTower){
		gamePanel.draw(path, enemies, towers, potentialTower);
	}

	public void setCurrency(float currency) {
		currencyLbl.setText(""+currency);
		
	}

	public void setScore(float score) {
		scoreLbl.setText(""+score);
	}

	public void setLives(int lives) {
		livesLbl.setText("Lives: " + lives);
	}
}
