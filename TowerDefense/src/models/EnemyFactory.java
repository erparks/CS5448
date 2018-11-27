package models;

import java.util.List;

/**
 * Create the appropriate enemy as requested.
 * @author Ethan Parks
 */
public class EnemyFactory {

	/**
	 * Unused.
	 */
	private EnemyFactory() {

	}

	/**
	 * Return the type of enemy requested.
	 * @param type Type of enemy to create.
	 * @param path Path to set the enemy on.
	 * @return The type of enemy requested.
	 */
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
