package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

public class GroundEnemy extends Enemy {

	
	public GroundEnemy(List<GameRectangle> path) {
		super(path, new Point.Double(-1,-1), 30.0f, 0.75, new Dimension(10, 10));
		setColor(Color.RED);
	}
	
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
