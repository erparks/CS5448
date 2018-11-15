package models;

import java.util.List;

public class EnemyFactory {

	private EnemyFactory() {

	}

	public static Enemy getNewEnemy(String type, List<GameRectangle> path) {
		switch (type.toLowerCase()) {
		case "air":
			return new AirEnemy(path);
		case "ground":
			return new GroundEnemy(path);
		}

		return null;
	}

}
