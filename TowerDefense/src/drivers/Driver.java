package drivers;

import controllers.*;

/** 
 * Starts the program.
 * 
 * The first command line argument is taken as the map file.
 * @author Ethan Parks
 */
public class Driver {
	public static void main(String[] args) {
		new Controller().control(args[0]);
	}
}
