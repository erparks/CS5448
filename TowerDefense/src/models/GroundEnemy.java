package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

/**
 * Represents a ground enemy.
 * @author Ethan Parks
 */
public class GroundEnemy extends Enemy {

	
	/**
	 * Create new ground enemy.
	 * @param path Path for the enemy to follow.
	 */
	public GroundEnemy(List<GameRectangle> path) {
		super(path, new Point.Double(-1,-1), 30.0f, 0.75, new Dimension(10, 10));
		setColor(Color.RED);
	}
	
	/**
	 * Update the enemy location one frame.
	 * 
	 * If the enemy has reached the current path point, they begin moving towards the next path point.
	 */
	@Override
	public void updateLocation() {
		if (getLocation().x < 0) {
			setCurrentPathPoint(1);
			setLocation(new Point.Double(getPath().get(0).getCenter().getX(), getPath().get(0).getCenter().getY()));
			return;
		}

		if (getCurrentPathPoint() == getPath().size() - 1) {
			setEscaped(true);
			return;
		}
		
		// enemy ready for next point
		if (Math.abs(getCenter().x - getPath().get(getCurrentPathPoint()).getCenter().x) == 0
		 && Math.abs(getCenter().y - getPath().get(getCurrentPathPoint()).getCenter().y) == 0) 
		{
			setCurrentPathPoint(getCurrentPathPoint() + 1);
		}

		setCenterLocation(stepLocation(getPath().get(getCurrentPathPoint()).getCenter().x,
				getPath().get(getCurrentPathPoint()).getCenter().y, getCenter().x, getCenter().y, getSpeed()));
	}

}
