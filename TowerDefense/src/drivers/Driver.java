package drivers;

import controllers.*;

public class Driver {
	public static void main(String[] args) {
		new Controller().control(args[0]);
	}
}
