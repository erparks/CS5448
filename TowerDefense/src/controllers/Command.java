package controllers;

/**
 * 
 * Interface for commands used in the command design pattern
 * @author Ethan Parks
 */
public interface Command {
	/**
	 * Execute the command
	 */
	public void execute();
	/**
	 * Undo the execution of the command
	 */
	public void undo();
}
