package models;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Model {

	public static final int TOWER_SIZE = 32;
	public static final int PATH_SIZE = 64;
	public static final int DEFAULT_PROJECTILE_SIZE = 5;
	public static final int TOWER_COST = 100;
	public static final int STARTING_LIVES = 20;
	public static final int STARTING_CURRENCY = 1000;

	private static final int SPAWN_SPEED = 500;
	private static final int TIME_BETWEEN_WAVES = 10000;
	private static final int WAVE_LENGTH = 10;

	private float currency;
	private float score;
	private long time;
	private int lives;
	private long lastSpawnTime;
	private String nextSpawn;

	private ArrayList<GameRectangle> path;
	private ArrayList<Tower> towers;
	private ArrayList<Enemy> enemies;

	private Stack<Enemy> enemiesToSpawn;

	public Model() {
		path = new ArrayList<GameRectangle>();

		reset();
	}

	public void reset() {
		towers = new ArrayList<Tower>();
		enemies = new ArrayList<Enemy>();
		enemiesToSpawn = new Stack<Enemy>();

		currency = STARTING_CURRENCY;
		lives = STARTING_LIVES;
		time = 0;
		score = 0;
		nextSpawn = "ground";

	}

	public void update(float deltaT) {

		spawnEnemies(enemies, enemiesToSpawn);

		moveEnemies(enemies);

		shootTowers(towers, enemies);

		moveProjectiles(towers);

		cleanEnemies(enemies);

		time += deltaT;
	}

	public void addUnspawnedEnemies(String type, int count) {
		for (int i = 0; i < count; i++)
			enemiesToSpawn.push(EnemyFactory.getNewEnemy(type, path));
	}

	private void spawnEnemies(List<Enemy> enemies, Stack<Enemy> enemiesToSpawn) {

		if (time % TIME_BETWEEN_WAVES == 0) {
			addUnspawnedEnemies(nextSpawn, WAVE_LENGTH);

			if (nextSpawn.equals("air"))
				nextSpawn = "ground";
			else
				nextSpawn = "air";
		}

		if (enemiesToSpawn.isEmpty())
			return;

		if (time - lastSpawnTime < SPAWN_SPEED)
			return;

		enemies.add(enemiesToSpawn.pop());
		lastSpawnTime = time;
	}

	private void moveEnemies(List<Enemy> enemies) {
		for (Enemy e : enemies) {
			e.updateLocation();
		}
	}

	private void shootTowers(List<Tower> towers, List<Enemy> enemies) {

		for (Iterator<Tower> tower_it = towers.iterator(); tower_it.hasNext();) {
			Tower t = tower_it.next();
			for (Iterator<Enemy> enemy_it = enemies.iterator(); enemy_it.hasNext();) {

				Enemy e = enemy_it.next();
				t.attemptShotAt(e, time);
			}
		}
	}

	private void moveProjectiles(List<Tower> towers) {

		for (Iterator<Tower> tower_it = towers.iterator(); tower_it.hasNext();) {
			Tower t = tower_it.next();
			for (Iterator<Projectile> projectile_it = t.getProjectiles().iterator(); projectile_it.hasNext();) {

				Projectile p = projectile_it.next();
				p.updateLocation();
				
				if (p.isExploded())
					projectile_it.remove();
			}

		}
	}

	private void cleanEnemies(List<Enemy> enemies) {

		for (Iterator<Enemy> enemy_it = enemies.iterator(); enemy_it.hasNext();) {
			Enemy e = enemy_it.next();

			if (e.getHealth() <= 0) {
				e.onDeath(this);
				enemy_it.remove();
			} else if (e.getEscaped()) {
				e.onEscape(this);
				enemy_it.remove();
			}
		}

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

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void addPath(GameRectangle pathRect) {
		path.add(pathRect);
	}

	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	public void addTower(Tower t) {
		towers.add(t);
	}

	public List<GameRectangle> getPath() {
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

	public boolean isValidTowerLocation(GameRectangle rect) {

		for (Tower t : towers) {
			if (t.intersectsRectangle(rect))
				return false;
		}

		for (GameRectangle pathRect : path) {
			if (pathRect.intersectsRectangle(rect))
				return false;
		}

		return true;
	}

	public Tower getClickedTower(Point mouseLocation) {
		for (Tower t : towers) {
			if (t.intersectsPoint(new Point.Double(mouseLocation.x, mouseLocation.y)))
				return t;
		}
		return null;
	}

	public void addCurrency(float c) {
		currency += c;
	}

	public void addScore(float scoreValue) {
		score += scoreValue;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
