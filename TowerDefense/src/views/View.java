package views;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.Enemy;
import models.Tower;

public class View {
	private JFrame frame;

	private JButton pauseBtn;
	private JButton buyTowerBtn;

	private GamePanel gamePanel;
	private JPanel controlPanel;

	private Canvas canvas;

	private Graphics graphics;

	private JLabel currencyLbl;
	private JLabel timeLbl;
	private JLabel scoreLbl;

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

		pauseBtn = new JButton("Pause");
		buyTowerBtn = new JButton("Buy Tower");

		gamePanel = new GamePanel(width, height);
		controlPanel = new JPanel();

		controlPanel.setLayout(new GridLayout(2, 3));
		controlPanel.add(timeLbl);
		controlPanel.add(pauseBtn);
		controlPanel.add(buyTowerBtn);
		controlPanel.add(new JPanel());
		controlPanel.add(currencyLbl);
		controlPanel.add(new JPanel());

		frame.add(scoreLbl, BorderLayout.NORTH);
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

	public void drawMap(ArrayList<Point> path) {
		gamePanel.drawMap(path);
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
	
	public void drawPotentialTower(Point point, Color c) {
		gamePanel.drawPotentialTower(point, c);
		
	}

	public void drawTowers(ArrayList<Tower> towers) {
		for(Tower t : towers) {
			gamePanel.drawTower(t);
		}
		
		gamePanel.repaint();
		
	}

	public void drawEnemies(ArrayList<Enemy> enemies) {
		for(Enemy e : enemies) {
			gamePanel.drawEnemy(e);
		}
		
		gamePanel.repaint();
	}

	public void setCurrency(float currency) {
		currencyLbl.setText(""+currency);
		
	}

	public void setScore(float score) {
		scoreLbl.setText(""+score);
	}
}
