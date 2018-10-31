package models;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Model {

	private float currency;
	private float score;
	private int time;
	private float towerCost;
	private float killReward;

	private ArrayList<Point> path;
	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;

	public Model() {
		towers = new ArrayList<Tower>();
		enemies = new ArrayList<Enemy>();

		towerCost = 500;
		currency = 500;
		killReward = 250;
	}

	public void update(float deltaT) {

		// Move enemies
		for (Enemy e : enemies) {
			e.updateLocation();
		}

		// Shoot towers
		for (Tower t : towers) {
			for (Enemy e : enemies) {
				if (Point2D.distance(t.getLocation().x, t.getLocation().y, e.getX(), e.getY()) < t.getRange()
						&& time - t.getTimeOfLastShoot() >= t.getShootCooldown()) {
					t.setTimeOfLastShoot(time);
					t.fire(e);
//					System.out.println("Fire");
				}
			}
		}

		// Move projectiles
		for (Tower t : towers) {
			ArrayList<Projectile> exploded = new ArrayList<Projectile>();

			for (Projectile p : t.getProjectiles()) {
				p.updateLocation();
				if(p.isExploded()) {
					exploded.add(p);
				}
			}

			//Remove exploded projectiles
			for (Projectile bp : exploded) {
				t.getProjectiles().remove(bp);
			}
		}
		


		ArrayList<Enemy> deadEnemies = new ArrayList<Enemy>();
		// Remove Dead enemies
		for (Enemy e : enemies) {
			if (e.getHealth() <= 0) {
				deadEnemies.add(e);
				e.onDeath(this);
			}
		}
		for (Enemy e : deadEnemies) {
			enemies.remove(e);
		}

		time += deltaT;
	}

	private boolean updateProjectileLocation(Projectile bp) {
		double dist = Point2D.distance(bp.getX(), bp.getY(), bp.getTarget().getX(), bp.getTarget().getY());
		//System.out.println("Moving: " + bp.getX() + "," + bp.getY() + " to " + bp.getTarget().getX() + "," + bp.getTarget().getY());
		bp.setLocation(moveToward(bp.getTarget().getX(), bp.getTarget().getY(), bp.getX(), bp.getY(), bp.getSpeed()));
		if (dist <= 5) {
			bp.explode();
//			System.out.println("Explode proj");
			return true;
		}
		return false;
	}

	private Point2D.Float moveToward(float x1, float y1, float x2, float y2, float speed) {
		Point2D.Float between = new Point2D.Float(x1 - x2, y1 - y2);

		float magnitude = (float) Math.sqrt(between.getX() * between.getX() + between.getY() * between.getY());

		between.setLocation(between.getX() / magnitude, between.getY() / magnitude);

		Point2D.Float p = new Point2D.Float((float) (x2 + speed * between.getX()),
				(float) (y2 + speed * between.getY()));
		return p;
	}

	public float getTowerCost() {
		return towerCost;
	}

	public void setTowerCost(float towerCost) {
		this.towerCost = towerCost;
	}

	public float getCurrency() {
		return currency;
	}

	public void setCurrency(float currency) {
		this.currency = currency;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setPath(ArrayList<Point> path) {
		this.path = path;
	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public void addTower(Tower t) {
		towers.add(t);
	}

	public ArrayList<Point> getPath() {
		return path;
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void removeTower(Tower tower) {
		towers.remove(tower);
	}

	public boolean isValidTowerLocation(Point mouseLocation) {

		Point upperLeft = new Point(mouseLocation.x, mouseLocation.y);
		Point upperRight = new Point(mouseLocation.x + 64, mouseLocation.y);
		Point lowerLeft = new Point(mouseLocation.x, mouseLocation.y + 64);
		Point lowerRight = new Point(mouseLocation.x + 64, mouseLocation.y + 64);

		for (Tower t : towers) {

			// If tower would overlap other tower
			if (rectContains(upperLeft, t.getLocation(), 64, 64) || rectContains(upperRight, t.getLocation(), 64, 64)
					|| rectContains(lowerLeft, t.getLocation(), 64, 64)
					|| rectContains(lowerRight, t.getLocation(), 64, 64)) {
				return false;
			}

		}

		// If tower would overlap the path
		if (pathContains(upperLeft) || pathContains(upperRight) || pathContains(lowerLeft)
				|| pathContains(lowerRight)) {
			return false;
		}

		return true;
	}

	private boolean pathContains(Point p) {

		for (Point pathPoint : path) {
			if (rectContains(p, pathPoint, 64, 64)) {
				return true;
			}
		}
		return false;
	}

	private boolean rectContains(Point p, Point rectPoint, int width, int height) {
		if ((p.x > rectPoint.x) && (p.x < rectPoint.x + width) && (p.y > rectPoint.y) && (p.y < rectPoint.y + height)) {
			return true;
		} else
			return false;
	}

	public Tower getClickedTower(Point mouseLocation) {
		for (Tower t : towers) {

			if (rectContains(new Point(mouseLocation.x + 32, mouseLocation.y + 32), t.getLocation(), 64, 64)) {
				return t;
			}

		}
		return null;
	}

	public void spendCurrency(float c) {
		currency -= c;
	}

	public void addCurrency(float c) {
		currency += c;
	}

	public void addScore(float scoreValue) {
		score += scoreValue;
	}

}
