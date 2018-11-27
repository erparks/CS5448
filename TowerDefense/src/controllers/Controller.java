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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Timer;

import controllers.input.InputController;
import models.GameRectangle;
import models.Model;
import models.PotentialTower;
import models.Tower;
import views.View;

/**
 * Handles all direct interaction with the main window.
 *         Updates the game every time step.
 * @author Ethan Parks 
 */
public class Controller implements ActionListener, MouseMotionListener, MouseListener, KeyListener {

	/**
	 * Time step between frames.
	 */
	private static long DELTA_T = 10;

	/**
	 * True if game is currently playing and not paused.
	 */
	private boolean isPlaying;
	/**
	 * True if the player has last clicked the "Placing Tower" button. If true, a
	 * potential tower is drawn at the mouse location.
	 */
	private boolean isPlacingTower;
	/**
	 * True if the user has lost the game and not yet restarted.
	 */
	private boolean gameOver;

	/**
	 * X,Y screen coordinates of the cursor.
	 */
	private Point mouseLocation;

	/**
	 * Back end data for the game.
	 */
	private Model model;
	/**
	 * Front end display for the game.
	 */
	private View view;

	/**
	 * Class which tracks command execution, undo, and redo.
	 */
	private UserCommands userCommands;

	/**
	 * Initialize all necessary classes for the game to run. Starts a thread to
	 * listen for user input on the command line.
	 */
	public Controller() {
		model = new Model();
		view = new View();

		userCommands = new UserCommands();

		new InputController(model, this);

		isPlaying = true;
	}

	/**
	 * Set this class as the listener for the view created in the constructor. Load
	 * map into the model. Start the timer for the update function.
	 * 
	 * @param mapFileLocation Location of the map file to be read.
	 */
	public void control(String mapFileLocation) {

		model.setMap(mapFileLocation);

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

	/**
	 * The model and view are updated one time step.
	 * 
	 * If the game ends this time step, a window is shown to alert the user and the
	 * game is stopped.
	 */
	protected void update() {

		if (!isPlaying)
			return;

		if (model.getLives() == 0) {
			gameOver = true;
			isPlaying = false;
			new GameOverController(this).control();

			saveScore();
		}

		model.update(DELTA_T);

		view.draw(model.getPath(), model.getEnemies(), model.getTowers(), getPotentialTower(mouseLocation));

		view.setTime(model.getTime());
		view.setCurrency(model.getCurrency());
		view.setScore(model.getScore());
		view.setLives(model.getLives());
	}

	/**
	 * Reset to the game to a blank slate.
	 */
	public void reset() {
		model.reset();
		gameOver = false;
		isPlaying = true;
	}

	/**
	 * Writes the current score to the .highscores file associated with the map.
	 */
	private void saveScore() {
		// Write to save file
		File highScoresFile = model.getHighscoresFile();
		if (!highScoresFile.isFile()) {
			try {
				highScoresFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(highScoresFile, true));
			out.write(model.getScore() + "\n");
			out.close();
		} catch (IOException e) {
			System.out.println("exception occoured" + e);
		}
	}

	/**
	 * Returns null if the user is not placing a tower. Otherwise returns a
	 * PotentialTower with the color set to red if mouse is in an invalid tower
	 * location and green if the mouse is in a valid tower location.
	 * 
	 * @param mouseLocation Screen location to place the PotentialTower around.
	 * @return Null if the user is not placing a tower. Otherwise returns a
	 *         PotentialTower with the color set to red if mouse is in an invalid
	 *         tower location and green if the mouse is in a valid tower location.
	 */
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

	/**
	 * Initialize the map in the model from the given map file.
	 * 
	 * @param mapFileLocation Location of the map file.
	 */
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
	/**
	 * Returns the upper left corner of a tower centered at the cursor.
	 * 
	 * @param mouselocation Location of the cursor
	 * @return The upper left corner of a tower centered at the cursor.
	 */
	private Point.Double getTowerLocationFromMouseLocation(Point mouselocation) {
		return new Point.Double(mouseLocation.x - Model.TOWER_SIZE / 2, mouseLocation.y - Model.TOWER_SIZE / 2);
	}

	public void setModel(Model model) {
		this.model = model;
	}

	/**
	 * Responds to button presses in the main window.
	 *  
	 * If "Buy Tower" is clicked, the game begins the potential tower placement. if
	 * "Pause" is clicked, the game switches from paused to playing or vice versa.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getBuyTowerBtn() && model.getCurrency() >= Model.TOWER_COST) {
			isPlacingTower = true;
		} else if (e.getSource() == view.getPauseBtn()) {
			if (!gameOver)
				isPlaying = !isPlaying;
		}
	}

	/**
	 * Tracks the mouse location
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLocation = e.getPoint();
	}

	/**
	 * Responds to mouse clicks in the main window.
	 * 
	 * If the user is placing a tower, tower creation is attempted. If the user
	 * clicked a tower, the tower edit view is shown.
	 */
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

	/**
	 * Listens for ctrl-z or ctrl-y to call undo or redo respectively.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown())) {
			userCommands.undo();
		} else if ((e.getKeyCode() == KeyEvent.VK_Y) && (e.isControlDown())) {
			userCommands.redo();
		}

	}
	/**
	 * Unused.
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	/**
	 * Unused.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}
	/**
	 * Unused.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {

	}
	/**
	 * Unused.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	/**
	 * Unused.
	 */
	@Override
	public void mouseExited(MouseEvent e) {

	}
	/**
	 * Unused.
	 */
	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Unused.
	 */
	@Override
	public void keyReleased(KeyEvent e) {

	}

}
