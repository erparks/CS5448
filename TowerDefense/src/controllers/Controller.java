package controllers;

import java.awt.Color;
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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

import models.Enemy;
import models.Model;
import models.Tower;
import views.TowerEditView;
import views.View;

public class Controller implements ActionListener, MouseMotionListener, MouseListener, KeyListener {

	private boolean isPlaying;
	private boolean isPlacingTower;
	private long deltaT;

	private Point mouseLocation;

	private Model model;
	private View view;

	private UserCommands userCommands;

	public Controller() {
		this.model = new Model();
		this.view = new View();

		userCommands = new UserCommands();

		deltaT = 10;

		// Spin thread to listen for user input
//		Thread inputThread = new Thread(new Runnable() {
//
//			public void run() {
//
//				Scanner input = new Scanner(System.in);
//				String userInput;
//				while (!(userInput = input.next()).equals("Exit")) {
//					System.out.println("you said " + userInput);
//
//				}
//
//				System.exit(0);
//			}
//		});
//
//		inputThread.start();
	}

	public void control(String mapFileLocation) {
		System.out.println("Control");

		view.getBuyTowerBtn().addActionListener(this);
		view.getBuyTowerBtn().addKeyListener(this);
		view.getPauseBtn().addActionListener(this);
		view.getPauseBtn().addKeyListener(this);
		view.getGamePanel().addMouseMotionListener(this);
		view.getGamePanel().addMouseListener(this);
		view.getGamePanel().addKeyListener(this);

		initMap(mapFileLocation);

		Timer timerObj = new Timer(true);
		timerObj.scheduleAtFixedRate(new TimeController(this), 0, deltaT);
	}

	protected void update() {
		if (model.getTime() % 10000.0 == 0) {
			System.out.println("Spawn enemy");
			spawnEnemies();
		}
		model.update(deltaT);

		view.drawMap(model.getPath());
		view.drawTowers(model.getTowers());
		view.drawEnemies(model.getEnemies());

		if (isPlacingTower) {
			if (model.isValidTowerLocation(mouseLocation)) {
				view.drawPotentialTower(mouseLocation, Color.GREEN);
			} else {
				view.drawPotentialTower(mouseLocation, Color.RED);
			}

		}

		view.setTime(model.getTime());
		view.setCurrency(model.getCurrency());
		view.setScore(model.getScore());
	}

	private void spawnEnemies() {
		model.addEnemy(new Enemy(model.getPath(), 200, 0.5f, Float.MIN_VALUE, Float.MIN_VALUE, false));
	}

	private void initMap(String mapFileLocation) {

		BufferedReader br = null;
		ArrayList<Point> path = new ArrayList<Point>();

		try {
			String line;
			br = new BufferedReader(new FileReader(mapFileLocation));

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] pointValues = line.split(",");

				path.add(new Point(Integer.parseInt(pointValues[0]), Integer.parseInt(pointValues[1])));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					view.drawMap(path);
					model.setPath(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == view.getBuyTowerBtn() && model.getCurrency() >= model.getTowerCost()) {
			isPlacingTower = true;
		} else if (e.getSource() == view.getPauseBtn()) {
			System.out.println("Pause game");
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseLocation = e.getPoint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Tower clickedTower = null;

		if (isPlacingTower && model.isValidTowerLocation(mouseLocation)) {

			userCommands.executeCommand(new AddTower(new Tower(e.getPoint()), model));

		} else if ((clickedTower = model.getClickedTower(mouseLocation)) != null) {
			
			new TowerEditController(clickedTower, userCommands);
			
		}

		isPlacingTower = false;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() == KeyEvent.VK_Z) && (e.isControlDown())) {
			System.out.println("Undo");
			userCommands.undo();
		} else if ((e.getKeyCode() == KeyEvent.VK_Y) && (e.isControlDown())) {
			userCommands.redo();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
