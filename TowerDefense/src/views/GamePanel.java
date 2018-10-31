package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import models.Projectile;
import models.Enemy;
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
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		imgG2 = img.createGraphics();
		try {
			System.out.println("Working Directory = " + System.getProperty("user.dir"));
			backgroundTile = ImageIO.read(new File("./images/dirt.jpeg"));
			pathTile = ImageIO.read(new File("./images/path.jpeg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void drawMap(ArrayList<Point> path) {
		drawBackground();
		drawPath(path);

		repaint();
	}

	private void drawPath(ArrayList<Point> path) {
		for (Point p : path) {
			imgG2.drawImage(pathTile, p.x-32, p.y-32, tileSize, tileSize, this);
		}
	}

	private void drawBackground() {
		for (int i = 0; i < width; i += tileSize) {
			for (int j = 0; j < height; j += tileSize) {
				imgG2.drawImage(backgroundTile, i, j, tileSize, tileSize, this);
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.drawImage(img, 0, 0, width, height, null);
	}

	public void drawPotentialTower(Point point, Color c) {
		imgG2.setColor(c);
		imgG2.drawRect(point.x-32, point.y-32, 64, 64);
		
		repaint();
	}

	public void drawTower(Tower t) {
		imgG2.setColor(Color.WHITE);
		imgG2.fillRect(t.getLocation().x-32, t.getLocation().y-32, 64, 64);
		
		imgG2.setColor(Color.BLUE);
		for(Projectile bp : t.getProjectiles()) {
			imgG2.fillOval((int)bp.getX(), (int)bp.getY(), 5, 5);
		}
		
	}

	public void drawEnemy(Enemy e) {
		imgG2.setColor(Color.RED);
		imgG2.fillOval((int)e.getX(), (int)e.getY(), 10, 10);
		imgG2.setColor(Color.BLACK);
		imgG2.drawString(""+e.getHealth(), (int)e.getX(), (int)e.getY() - 10);
	}
	

}
