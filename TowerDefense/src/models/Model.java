package models;

import java.awt.Point;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Backend data for the game.
 * 
 * @author Ethan Parks
 */
public class Model implements Serializable {

	/**
	 * Width and height of a tower.
	 */
	public static final int TOWER_SIZE = 32;
	/**
	 * Width and height of a path rectangle.
	 */
	public static final int PATH_SIZE = 64;
	/**
	 * Size of a projectile.
	 */
	public static final int DEFAULT_PROJECTILE_SIZE = 5;
	/**
	 * Currency cost of purchasing a tower.
	 */
	public static final int TOWER_COST = 100;
	/**
	 * Number of lives the user starts with.
	 */
	public static final int STARTING_LIVES = 20;
	/**
	 * Amount of currency the user starts with.
	 */
	public static final int STARTING_CURRENCY = 1000;

	/**
	 * Minimum time between enemy spawns
	 */
	private static final int SPAWN_SPEED = 500;
	/**
	 * Time between waves spawning.
	 */
	private static final int TIME_BETWEEN_WAVES = 10000;
	/**
	 * Number of enemies within a wave.
	 */
	private static final int WAVE_LENGTH = 5;

	/**
	 * Current amount of currency the player has.
	 */
	private float currency;
	/**
	 * Current score in the game.
	 */
	private float score;
	/**
	 * Amount of time that has passed in the game.
	 */
	private long time;
	/**
	 * Number of lives the player has remaining.
	 */
	private int lives;
	/**
	 * Spawn time of the last enemy.
	 */
	private long lastSpawnTime;
	/**
	 * The type of enemy to spawn next.
	 */
	private String nextSpawn;

	/**
	 * The points which the enemies follow across the screen.
	 */
	private ArrayList<GameRectangle> path;
	/**
	 * Current towers within the game.
	 */
	private ArrayList<Tower> towers;
	/**
	 * Current enemies within the game.
	 */
	private ArrayList<Enemy> enemies;

	/**
	 * Enemies waiting to be spawned.
	 */
	private Stack<Enemy> enemiesToSpawn;

	/**
	 * File name of the current map.
	 */
	private String map;

	/**
	 * Create model with empty no towers or enemies.
	 * 
	 * The path is not reset.
	 */
	public Model() {
		path = new ArrayList<GameRectangle>();

		reset();
	}

	/**
	 * Clear out all enemies and towers. Reset all counters.
	 */
	public void reset() {
		towers = new ArrayList<Tower>();
		enemies = new ArrayList<Enemy>();
		enemiesToSpawn = new Stack<Enemy>();

		currency = STARTING_CURRENCY;
		lives = STARTING_LIVES;
		time = 0;
		score = 0;
		lastSpawnTime = 0;
		nextSpawn = "ground";

	}

	/**
	 * Updates all game entities one frame. Updates the total time passed.
	 * 
	 * @param deltaT Amount of time passed since last update.
	 */
	public void update(float deltaT) {

		spawnEnemies(enemies, enemiesToSpawn);

		moveEnemies(enemies);

		shootTowers(towers, enemies);

		moveProjectiles(towers);

		cleanEnemies(enemies);

		time += deltaT;
	}

	/**
	 * Add enemies to be spawned.
	 * 
	 * @param type  The type of enemy to spawn.
	 * @param count The number of enemies to spawn.
	 */
	public void addUnspawnedEnemies(String type, int count) {
		for (int i = 0; i < count; i++)
			enemiesToSpawn.push(EnemyFactory.getNewEnemy(type, path));
	}

	/**
	 * Spawn the next enemy in the stack of enemies waiting to be spawned.
	 * 
	 * An enemy is only spawned if enough time has passed since the last one
	 * spawened.
	 * 
	 * A new wave is added to the stack of waiting enemies if enough time has passed
	 * since the last wave.
	 * 
	 * @param enemies        List of currently active enemies.
	 * @param enemiesToSpawn Stack of enemies waiting to be spawned.
	 */
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

	/**
	 * Update the enemies' location by one frame.
	 * 
	 * @param enemies Enemies to update.
	 */
	private void moveEnemies(List<Enemy> enemies) {
		for (Enemy e : enemies) {
			e.updateLocation();
		}
	}

	/**
	 * Make towers spawn new projectiles based on surrounding enemies.
	 * 
	 * @param towers  Towers to potentially shoot.
	 * @param enemies Enemies for the towers to shoot at.
	 */
	private void shootTowers(List<Tower> towers, List<Enemy> enemies) {

		for (Iterator<Tower> tower_it = towers.iterator(); tower_it.hasNext();) {
			Tower t = tower_it.next();
			for (Iterator<Enemy> enemy_it = enemies.iterator(); enemy_it.hasNext();) {

				Enemy e = enemy_it.next();
				t.attemptShotAt(e, time);
			}
		}
	}

	/**
	 * Move each projectile and remove from list if it has exploded.
	 * 
	 * @param towers Towers whose projectiles are to be moved.
	 */
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

	/**
	 * Remove all dead and escaped enemies and update the model accordingly.
	 * 
	 * @param enemies All enemies currently in the game.
	 */
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

	/**
	 * Returns the current currency.
	 * 
	 * @return The current currency.
	 */
	public float getCurrency() {
		return currency;
	}

	/**
	 * Set the current currency.
	 * 
	 * @param currency The new currency value.
	 */
	public void setCurrency(float currency) {
		this.currency = currency;
	}

	/**
	 * Returns the current score.
	 * 
	 * @return The current score.
	 */
	public float getScore() {
		return score;
	}

	/**
	 * Set the current score.
	 * 
	 * @param score The new score value.
	 */
	public void setScore(float score) {
		this.score = score;
	}

	/**
	 * Returns the current game time.
	 * 
	 * @return The current game time.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Set the current time.
	 * 
	 * @param time The new time value.
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Add a point to the end of the path.
	 * 
	 * @param pathRect GameRectangle representing the new path point.
	 */
	public void addPath(GameRectangle pathRect) {
		path.add(pathRect);
	}

	/**
	 * Add enemy to the list of active enemies.
	 * 
	 * @param e Enemy to add to the list of active enemies.
	 */
	public void addEnemy(Enemy e) {
		enemies.add(e);
	}

	/**
	 * Add tower to the list of towers.
	 * 
	 * @param t The tower to add.
	 */
	public void addTower(Tower t) {
		towers.add(t);
	}

	/**
	 * Returns the list of GameRectangles representing the path.
	 * 
	 * @return The list of GameRectangles representing the path.
	 */
	public List<GameRectangle> getPath() {
		return path;
	}

	/**
	 * Returns the list of towers currently in the game.
	 * 
	 * @return The list of towers currently in the game.
	 */
	public ArrayList<Tower> getTowers() {
		return towers;
	}

	/**
	 * Returns the list of enemies currently in the game.
	 * 
	 * @return The list of enemies currently in the game.
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * Removes the given tower from the game.
	 * 
	 * @param tower Tower to remove from the game.
	 */
	public void removeTower(Tower tower) {
		towers.remove(tower);
	}

	/**
	 * Returns true if the given GameRectangle represents a valid tower location.
	 * 
	 * @param rect Potential Tower location
	 * @return True if the given GameRectangle represents a valid tower location.
	 */
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

	/**
	 * Add to the current currency value
	 * 
	 * @param c Amount to add to the current currency value.
	 */
	public void addCurrency(float c) {
		currency += c;
	}

	/**
	 * Add to the current score value.
	 * 
	 * @param scoreValue Amount to add to the current score value.
	 */
	public void addScore(float scoreValue) {
		score += scoreValue;
	}

	/**
	 * Returns the number of lives remaining.
	 * 
	 * @return The number of lives remaining.
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Set the current lives.
	 * 
	 * @param lives The new lives value.
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Returns the location of the map file.
	 * 
	 * @return The location of the map file.
	 */
	public String getMap() {
		return map;
	}

	/**
	 * Set the map.
	 * 
	 * @param map The new map value.
	 */
	public void setMap(String map) {
		this.map = map;
	}

	/**
	 * Returns the location of the high scores file associated with the current map.
	 * 
	 * @return The location of the high scores file associated with the current map.
	 */
	public File getHighscoresFile() {
		return new File(map + ".highscore");
	}
}
