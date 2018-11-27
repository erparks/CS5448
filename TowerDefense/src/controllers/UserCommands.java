package controllers;

import java.util.Stack;

/**
 * Holds the undo and redo stack.
 * Executes commands
 * @author Ethan Parks
 */
public class UserCommands {
	/**
	 * Commands to be reversed when the used presses cmd-z.
	 */
	private Stack<Command> undoStack;
	
	/**
	 * Commands to be re-executed after being undone when the user presses cmd-y.
	 */
	private Stack<Command> redoStack;
	
	/**
	 * Initializes undo and redo commands stacks.
	 */
	public UserCommands()
	{
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
	}
	
	/**
	 * Reverse the commands last executed.
	 */
	public void undo()
	{
		if(undoStack.isEmpty()) return;
		
		Command cmd = undoStack.pop();
		cmd.undo();
		redoStack.push(cmd);
		
	}
	
	/**
	 * Execute the command that was last undone.
	 */
	public void redo()
	{
		if(redoStack.isEmpty()) return;
		
		Command cmd = redoStack.pop();
		executeCommand(cmd);
	}
	
	
	/**
	 * Execute given command and push to the undo stack.
	 * @param command Command to be executed
	 */
	public void executeCommand(Command command)
	{
		command.execute();
		undoStack.push(command);
	}
	
	
}
