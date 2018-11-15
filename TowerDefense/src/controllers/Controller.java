package controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

import models.AirEnemy;
import models.Enemy;
import models.EnemyFactory;
import models.GameRectangle;
import models.Model;
import models.PotentialTower;
import models.Tower;
import views.View;

public class Controller implements ActionListener, MouseMotionListener, MouseListener, KeyListener {

	private static long DELTA_T = 10;
	
	private boolean isPlaying;
	private boolean isPlacingTower;
	private boolean gameOver;

	private Point mouseLocation;

	private Model model;
	private View view;

	private UserCommands userCommands;

	public Controller() {
		model = new Model();
		view = new View();

		userCommands = new UserCommands();

		new InputController(model);

		isPlaying = true;
	}

	public void control(String mapFileLocation) {

		view.getBuyTowerBtn().addActionListener(this);
		view.getBuyTowerBtn().addKeyListener(this);
		view.getPauseBtn().addActionListener(this);
		view.getPauseBtn().addKeyListener(this);
		view.getGamePanel().addMouseMotionListener(this);
		view.getGamePanel().addMouseListener(this);
		view.getGamePanel().addKeyListener(this);

		initMap(mapFileLocation);

		Timer timerObj = new Timer(true);
		timerObj.scheduleAtFixedRate(new TimeController(this), 0, DELTA_T);
	}

	protected void update() {

		if (!isPlaying)
			return;

		if (model.getLives() == 0) {
			gameOver = true;
			isPlaying = false;
			new GameOverController(this);
		}
		
		model.update(DELTA_T);

		view.draw(model.getPath(), model.getEnemies(), model.getTowers(), getPotentialTower(mouseLocation));

		view.setTime(model.getTime());
		view.setCurrency(model.getCurrency());
		view.setScore(model.getScore());
		view.setLives(model.getLives());
	}

	public void reset() {
		model.reset();
		gameOver = false;
		isPlaying = true;
	}

	private PotentialTower getPotentialTower(Point mouseLocation) {
		if (!isPlacingTower)
			return null;
		if (mouseLocation == null)
			return null;

		PotentialTower potentialTower = new PotentialTower(
				new Point.Double(mouseLocation.x - Model.TOWER_SIZE / 2, mouseLocation.y - Model.TOWER_SIZE / 2));

		if (!model.isValidTowerLocation(potentialTower))
			potentialTower.setColor(Color.RED);
		else
			potentialTower.setColor(Color.GREEN);

		return potentialTower;
	}

	private void initMap(String mapFileLocation) {

		BufferedReader br = null;

		try {
			String line;
			br = new BufferedReader(new FileReader(mapFileLocation));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] pointValues = line.split(",");

				model.addPath(new GameRectangle(
						new Point.Double(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1])),
						new Dimension(Model.PATH_SIZE, Model.PATH_SIZE)));

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// This gives the upper left corner of the tower centered at the cursor.
	private Point.Double getTowerLocationFromMouseLocation(Point mouselocation) {
		return new Point.Double(mouseLocation.x - Model.TOWER_SIZE / 2, mouseLocation.y - Model.TOWER_SIZE / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getBuyTowerBtn() && model.getCurrency() >= Model.TOWER_COST) {
			isPlacingTower = true;
		} else if (e.getSource() == view.getPauseBtn()) {
			if (!gameOver)
				isPlaying = !isPlaying;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLocation = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tower clickedTower = null;

		// If placing tower and valid location
		if (isPlacingTower
				&& model.isValidTowerLocation(new PotentialTower(getTowerLocationFromMouseLocation(e.getPoint())))) {

			userCommands
					.executeCommand(new AddTower(new Tower(getTowerLocationFromMouseLocation(e.getPoint())), model));

		}

		// If tower was clicked
		else if ((clickedTower = model.getClickedTower(mouseLocation)) != null) {

			new TowerEditController(clickedTower, model.getEnemies(), userCommands);
		}

		isPlacingTower = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown())) {
			userCommands.undo();
		} else if ((e.getKeyCode() == KeyEvent.VK_Y) && (e.isControlDown())) {
			userCommands.redo();
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
