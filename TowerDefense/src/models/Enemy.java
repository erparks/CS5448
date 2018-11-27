package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;


/**
 * Holds all data for the enemy. Handles the death and escape effects of the enemy.
 * @author Ethan Parks
 */
public abstract class Enemy extends Mobile {

	/**
	 * Current health of the enemy.
	 */
	private float health;
	/**
	 * The amount of currency awarded for killing this enemy.
	 */
	private float currencyValue;
	/**
	 * The amount to increment the score upon killing this enemy.
	 */
	private float scoreValue;
	/**
	 * Path point to which the enemy is currently traveling toward.
	 */
	private int currentPathPoint;
	/**
	 * True if the enemy has reached the last path point.
	 */
	private boolean escaped;
	/**
	 * Color to draw the enemy.
	 */
	private Color color;

	/**
	 * The path for the enemy to follow.
	 */
	private List<GameRectangle> path;

	/**
	 * Create new entity.
	 * @param path The path for the enemy to follow.
	 * @param location Current location of the enemy.
	 * @param health Current health of the enemy.
	 * @param speed Distance traveled per frame.
	 * @param dimension Size of the enemy.
	 */
	public Enemy(List<GameRectangle> path, Point.Double location, float health, double speed, Dimension dimension) {
		super(location, dimension, speed);
		
		this.path = path;
		this.health = health;
		this.currencyValue = 50;
		this.scoreValue = 50;
		this.escaped = false;

	}

	
	/**
	 * @return Color of enemy.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set color of the enemy.
	 * @param color Color to set the enemy.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Set the enemy as escaped.
	 * 
	 * The enemy will be removed from the model.
	 * @param escaped New value of escaped.
	 */
	public void setEscaped(boolean escaped) {
		this.escaped = escaped;
	}

	/**
	 * Return the path for the enemy to follow.
	 * @return The path for the enemy to follow.
	 */
	public List<GameRectangle> getPath() {
		return path;
	}

	/**
	 * Returns the currency value of the enemy.
	 * @return The currency value of the enemy.
	 */
	public float getCurrencyValue() {
		return currencyValue;
	}

	
	/**
	 * Sets the currency value of the enemy.
	 * @param currencyValue New currency value of the enemy.
	 */
	public void setCurrencyValue(float currencyValue) {
		this.currencyValue = currencyValue;
	}

	/**
	 * Returns the score value of the enemy.
	 * @return The score value of the enemy.
	 */
	public float getScoreValue() {
		return scoreValue;
	}

	/**
	 * Set the score value of the enemy.
	 * @param scoreValue New score value for the enemy.
	 */
	public void setScoreValue(float scoreValue) {
		this.scoreValue = scoreValue;
	}

	/**
	 * Returns the current health of the enemy.
	 * @return The current health of the enemy.
	 */
	public float getHealth() {
		return health;
	}

	/**
	 * Set the current health of the enemy.
	 * @param health New health for the enemy.
	 */
	public void setHealth(float health) {
		this.health = health;
	}

	/**
	 * Returns the path point to which the enemy is currently traveling.
	 * @return The path point to which the enemy is currently traveling.
	 */
	public int getCurrentPathPoint() {
		return currentPathPoint;
	}

	/**
	 * Set the path point to which the enemy will travel.
	 * @param currentPathPoint Next path point for the enemy.
	 */
	public void setCurrentPathPoint(int currentPathPoint) {
		this.currentPathPoint = currentPathPoint;
	}

	/**
	 * Subtract i from the enemy health.
	 * @param i amount to subtract from the enemy health.
	 */
	public void applyDamage(int i) {
		health -= i;

	}

	/**
	 * Returns true if the enemy has reached the last path point.
	 * @return True if the enemy has reached the last path point.
	 */
	public boolean getEscaped() {
		return escaped;
	}
	
	/**
	 * Updates the model to add to the players scores and currency.
	 * 
	 * Called when the enemy's health reaches 0.
	 * @param model Model to update.
	 */
	public void onDeath(Model model) {
		model.addCurrency(currencyValue);
		model.addScore(scoreValue);
	}

	/**
	 * Updates the model to subtract one life.
	 * @param model Model to update.
	 */
	public void onEscape(Model model) {
		model.setLives(model.getLives() - 1);
	}

}
