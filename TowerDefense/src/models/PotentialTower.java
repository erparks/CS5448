package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class PotentialTower extends GameRectangle {
	private Point.Double location;
	private Color color;
	
	public PotentialTower(Point.Double location)
	{
		super(location,new Dimension( Model.TOWER_SIZE, Model.TOWER_SIZE));
		this.location = location;
	}

	public Point.Double getLocation() {
		return location;
	}

	public void setLocation(Point.Double location) {
		this.location = location;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

}
