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

public class GamePanel extends JPanel {
	private BufferedImage img;
	private Graphics2D imgG2;

	private BufferedImage backgroundTile;
	private BufferedImage pathTile;

	private int width;
	private int height;
	private int tileSize;

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

	public void draw(List<GameRectangle> path, ArrayList<Enemy> enemies, ArrayList<Tower> towers,
			PotentialTower potentialTower) {
		drawMap(path);
		drawEnemies(enemies);
		drawTowers(towers);
		drawPotentialTower(potentialTower);

		repaint();
	}

	private void drawMap(List<GameRectangle> path) {
		drawBackground();
		drawPath(path);
	}

	private void drawEnemies(ArrayList<Enemy> enemies) {
		for (Enemy e : enemies) {
			drawEnemy(e);
		}
	}

	private void drawTowers(ArrayList<Tower> towers) {
		for (Tower t : towers) {
			drawTower(t);
		}
	}

	private void drawPath(List<GameRectangle> path) {
		for (GameRectangle pathRect : path) {
			imgG2.drawImage(pathTile, (int) pathRect.getUpperLeft().x,
					(int) pathRect.getUpperLeft().y, Model.PATH_SIZE, Model.PATH_SIZE, this);
		}
	}

	private void drawBackground() {
		for (int i = 0; i < width; i += tileSize) {
			for (int j = 0; j < height; j += tileSize) {
				imgG2.drawImage(backgroundTile, i, j, Model.PATH_SIZE, Model.PATH_SIZE, this);
			}
		}
	}

	private void drawPotentialTower(PotentialTower potentialTower) {

		if (potentialTower == null)
			return;

		imgG2.setColor(potentialTower.getColor());

		imgG2.drawRect((int) (potentialTower.getLocation().x),
				(int) (potentialTower.getLocation().y), Model.TOWER_SIZE, Model.TOWER_SIZE);
	}

	private void drawTower(Tower t) {
		imgG2.setColor(Color.WHITE);
		imgG2.fillRect((int) (t.getLocation().x), (int) (t.getLocation().y), Model.TOWER_SIZE, Model.TOWER_SIZE);

		imgG2.setColor(Color.BLUE);
		for (Projectile bp : t.getProjectiles()) {
			imgG2.fillOval((int) bp.getLocation().x, (int) bp.getLocation().y, Model.DEFAULT_PROJECTILE_SIZE,
					Model.DEFAULT_PROJECTILE_SIZE);
		}

	}

	private void drawEnemy(Enemy e) {
		imgG2.setColor(e.getColor());
		imgG2.fillOval((int) e.getLocation().x, (int) e.getLocation().y, (int) e.getDimension().getWidth(), (int) e.getDimension().getHeight());
		imgG2.setColor(Color.BLACK);
		imgG2.drawString("" + e.getHealth(), (int) e.getLocation().x, (int) e.getLocation().y - 10);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(img, 0, 0, width, height, null);
	}

}
