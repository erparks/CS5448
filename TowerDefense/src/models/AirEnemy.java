package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

public class AirEnemy extends Enemy {
	
	public AirEnemy(List<GameRectangle> path) {
		super(path, new Point.Double(-1, -1), 20.0f, 1.0, new Dimension(10, 10));
		setColor(Color.YELLOW);

	}

	@Override
	public void updateLocation() {
		if (getLocation().x < 0) {
			setCurrentPathPoint(getPath().size() - 2);
			setCenterLocation(new Point.Double(getPath().get(0).getCenter().getX(), getPath().get(0).getCenter().getY()));
			return;
		}

		// enemy ready for next point
		if (Math.abs(getCenter().x - getPath().get(getCurrentPathPoint()).getCenter().x) == 0
		 && Math.abs(getCenter().y - getPath().get(getCurrentPathPoint()).getCenter().y) == 0) {
			setEscaped(true);
			return;
		}

		setCenterLocation(stepLocation(getPath().get(getCurrentPathPoint()).getCenter().x,
				getPath().get(getCurrentPathPoint()).getCenter().y, getCenter().x, getCenter().y, getSpeed()));
	}

}
