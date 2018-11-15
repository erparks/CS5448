package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemy extends Mobile {

	private float health;
	private float currencyValue;
	private float scoreValue;
	private int currentPathPoint;
	private boolean escaped;
	private Color color;

	private List<GameRectangle> path;

	public Enemy(List<GameRectangle> path, Point.Double location, float health, double speed, Dimension dimension) {
		super(location, dimension, speed);
		
		this.path = path;
		this.health = health;
		this.currencyValue = 50;
		this.scoreValue = 50;
		this.escaped = false;

	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setEscaped(boolean escaped) {
		this.escaped = escaped;
	}

	public List<GameRectangle> getPath() {
		return path;
	}

	public float getCurrencyValue() {
		return currencyValue;
	}

	public void setCurrencyValue(float currencyValue) {
		this.currencyValue = currencyValue;
	}

	public float getScoreValue() {
		return scoreValue;
	}

	public void setScoreValue(float scoreValue) {
		this.scoreValue = scoreValue;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public int getCurrentPathPoint() {
		return currentPathPoint;
	}

	public void setCurrentPathPoint(int currentPathPoint) {
		this.currentPathPoint = currentPathPoint;
	}

	public void applyDamage(int i) {
		health -= i;

	}

	public boolean getEscaped() {
		return escaped;
	}
	
	public void onDeath(Model model) {
		model.addCurrency(currencyValue);
		model.addScore(scoreValue);
	}

	public void onEscape(Model model) {
		model.setLives(model.getLives() - 1);
	}

}
