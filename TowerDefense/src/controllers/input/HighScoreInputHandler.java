package controllers.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Handles command line input for highscores.
 * @author Ethan Parks 
 */
public class HighScoreInputHandler extends InputHandler {

	/**
	 * File to read high scores from
	 */
	File highScoresFile;

	/**
	 * Create new input handler for	highscores command.
	 * Reads high scores and prints top 5.
	 * 
	 * @param userInput      String received from command line.
	 * @param highScoresFile File to read high scores from.
	 */
	public HighScoreInputHandler(String userInput, File highScoresFile) {
		super(userInput);
		this.highScoresFile = highScoresFile;
	}

	@Override
	protected boolean isInvalidInput(String[] splits) {
		return splits.length != 1;
	}

	@Override
	protected void printUsage() {
		System.out.println("usage: highscores");

	}

	/**
	 * Prints top 5 high scores from the high score file.
	 */
	@Override
	protected void respondToInput(String[] splits) {
		ArrayList<Double> highScores = new ArrayList<Double>();

		BufferedReader br = null;

		try {
			String line;
			br = new BufferedReader(new FileReader(highScoresFile));

			while ((line = br.readLine()) != null) {
				highScores.add(Double.parseDouble(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Collections.sort(highScores, Collections.reverseOrder());

		System.out.println("High scores for the current map:");
		for (int i = 0; i < 5 && i < highScores.size(); i++) {
			System.out.println(highScores.get(i));
		}

	}

}
