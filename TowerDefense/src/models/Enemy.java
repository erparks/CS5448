package models;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Enemy extends Mobile {
	
	private float health;
	private float speed;
	private float currencyValue;
	private float scoreValue;

	private boolean isFlying;
	private int currentPathPoint;

	private ArrayList<Point> path;
	
	public Enemy(ArrayList<Point> path, float health, float speed, float x, float y, boolean isFlying) {
		this.path = path;
		this.health = health;
		this.speed = speed;
		this.isFlying = isFlying;
		this.setX(x);
		this.setY(y);
		this.currencyValue = 50;
		this.scoreValue = 50;
	}

	@Override
	public void updateLocation() {
		if (getX() == Float.MIN_VALUE) {
			setCurrentPathPoint(1);
			setLocation((float) path.get(0).getX(), (float) path.get(0).getY());
		}
		// enemy ready for next point
		else if (getX() == path.get(getCurrentPathPoint()).x && getY() == path.get(getCurrentPathPoint()).y) {
			if (getCurrentPathPoint() == path.size() - 1)
				return;
			else
				setCurrentPathPoint(getCurrentPathPoint() + 1);
		}

		setLocation(stepLocation(path.get(getCurrentPathPoint()).x, path.get(getCurrentPathPoint()).y, getX(),
				getY(), getSpeed()));
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

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}

	public int getCurrentPathPoint() {
		return currentPathPoint;
	}

	public void setCurrentPathPoint(int currentPathPoint) {
		this.currentPathPoint = currentPathPoint;
	}

	public void setLocation(float x, float y) {
		this.setX(x);
		this.setY(y);
	}

	public void setLocation(Point2D.Float p) {
		this.setX((float) p.getX());
		this.setY((float) p.getY());
	}

	public void applyDamage(int i) {
		health -= i;

	}

	public void onDeath(Model model) {
		model.addCurrency(currencyValue);
		model.addScore(scoreValue);
	}

}
