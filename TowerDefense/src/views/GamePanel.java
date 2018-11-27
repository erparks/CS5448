package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import models.Enemy;
import models.GameRectangle;
import models.Model;
import models.PotentialTower;
import models.Projectile;
import models.Tower;

/**
 * @author Ethan Parks Panel for the game to be drawn on.
 */
public class GamePanel extends JPanel {
	/**
	 * Buffered image to drawn the game on.
	 */
	private BufferedImage img;
	/**
	 * Graphics object to draw to.
	 */
	private Graphics2D imgG2;

	/**
	 * Texture for the non-path portion of the panel.
	 */
	private BufferedImage backgroundTile;
	/**
	 * Texture to be used as the path.
	 */
	private BufferedImage pathTile;

	/**
	 * Panel width.
	 */
	private int width;
	/**
	 * Panel height.
	 */
	private int height;
	/**
	 * Size of the path and background textures.
	 */
	private int tileSize;

	/**
	 * Loads textures, initializes panel, initializes images.
	 * 
	 * @param width  Panel width.
	 * @param height Panel height.
	 */
	public GamePanel(int width, int height) {
		this.width = width;
		this.height = height;
		this.tileSize = 64;

		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		setDoubleBuffered(true);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		imgG2 = img.createGraphics();

		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			backgroundTile = ImageIO.read(new File("./images/dirt.jpeg"));
			pathTile = ImageIO.read(new File("./images/path.jpeg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Draws all game compnents to the buffered image and the paints to the screen.
	 * 
	 * @param path           GameRectangles representing the path point locations
	 *                       and sizes.
	 * @param enemies        Active enemies to be drawn.
	 * @param towers         Towers to be drawn.
	 * @param potentialTower PotentialTower to draw.
	 */
	public void draw(List<GameRectangle> path, ArrayList<Enemy> enemies, ArrayList<Tower> towers,
			PotentialTower potentialTower) {
		drawMap(path);
		drawEnemies(enemies);
		drawTowers(towers);
		drawPotentialTower(potentialTower);

		repaint();
	}

	/**
	 * Use background texture to fill panel and then draw the path texture along the
	 * path.
	 * 
	 * @param path
	 */
	private void drawMap(List<GameRectangle> path) {
		drawBackground();
		drawPath(path);
	}

	/**
	 * Draw all active enemies.
	 * 
	 * @param enemies Active enemies.
	 */
	private void drawEnemies(ArrayList<Enemy> enemies) {
		for (Enemy e : enemies) {
			drawEnemy(e);
		}
	}

	/**
	 * Draw all towers.
	 * 
	 * @param towers All towers to be drawn.
	 */
	private void drawTowers(ArrayList<Tower> towers) {
		for (Tower t : towers) {
			drawTower(t);
		}
	}

	/**
	 * Draw path texture at each path point.
	 * 
	 * @param path GameRectangles representing size and location of path points.
	 */
	private void drawPath(List<GameRectangle> path) {
		for (GameRectangle pathRect : path) {
			imgG2.drawImage(pathTile, (int) pathRect.getUpperLeft().x, (int) pathRect.getUpperLeft().y, Model.PATH_SIZE,
					Model.PATH_SIZE, this);
		}
	}

	/**
	 * Draw the background texture over the whole panel.
	 */
	private void drawBackground() {
		for (int i = 0; i < width; i += tileSize) {
			for (int j = 0; j < height; j += tileSize) {
				imgG2.drawImage(backgroundTile, i, j, Model.PATH_SIZE, Model.PATH_SIZE, this);
			}
		}
	}

	/**
	 * If not null, draw the potential tower being placed.
	 * 
	 * @param potentialTower
	 */
	private void drawPotentialTower(PotentialTower potentialTower) {

		if (potentialTower == null)
			return;

		imgG2.setColor(potentialTower.getColor());

		imgG2.drawRect((int) (potentialTower.getLocation().x), (int) (potentialTower.getLocation().y), Model.TOWER_SIZE,
				Model.TOWER_SIZE);
	}

	/**
	 * Draw rectanlge at each tower location and blue circles at all projectile
	 * locations.
	 * 
	 * @param t Towers to draw and whose projectiles will be drawn.
	 */
	private void drawTower(Tower t) {
		imgG2.setColor(Color.WHITE);
		imgG2.fillRect((int) (t.getLocation().x), (int) (t.getLocation().y), Model.TOWER_SIZE, Model.TOWER_SIZE);

		imgG2.setColor(Color.BLUE);
		for (Projectile bp : t.getProjectiles()) {
			imgG2.fillOval((int) bp.getLocation().x, (int) bp.getLocation().y, Model.DEFAULT_PROJECTILE_SIZE,
					Model.DEFAULT_PROJECTILE_SIZE);
		}

	}

	/**
	 * Draw circle at each enemy location colored by individual enemy. Draw enemy
	 * health above their circle.
	 * 
	 * @param e
	 */
	private void drawEnemy(Enemy e) {
		imgG2.setColor(e.getColor());
		imgG2.fillOval((int) e.getLocation().x, (int) e.getLocation().y, (int) e.getDimension().getWidth(),
				(int) e.getDimension().getHeight());
		imgG2.setColor(Color.BLACK);
		imgG2.drawString("" + e.getHealth(), (int) e.getLocation().x, (int) e.getLocation().y - 10);
	}

	/**
	 * Draw the image to the screen.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(img, 0, 0, width, height, null);
	}

}
