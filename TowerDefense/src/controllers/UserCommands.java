package controllers;

import java.util.Stack;

public class UserCommands {
	private Stack<Command> undoStack;
	private Stack<Command> redoStack;
	
	public UserCommands()
	{
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
	}
	
	public void undo()
	{
		if(undoStack.isEmpty()) return;
		
		Command cmd = undoStack.pop();
		cmd.undo();
		redoStack.push(cmd);
		
	}
	
	public void redo()
	{
		if(redoStack.isEmpty()) return;
		
		Command cmd = redoStack.pop();
		executeCommand(cmd);
	}
	
	public void executeCommand(Command command)
	{
		command.execute();
		undoStack.push(command);
	}
	
	
}
