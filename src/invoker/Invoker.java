package invoker;

import commands.Command;
import exceptions.CustomException;
import java.util.Stack; // ver. 1.0

/**
 * Invoker class to initiate and trigger commands.
 * Contains Command[] to manage command execution and history.
 */
public class Invoker {
    /**
     * Array of commands to be executed in sequence
     */
    private Command[] cmdToExecute;

    /**
     * Sets the array of commands to execute when executeCommand(Stack) is called
     * @param cmdToExecute the array of commands to execute. Can be null or empty.
     */
    public void setCommandsForExecution(Command[] cmdToExecute){
        this.cmdToExecute = cmdToExecute;
    }

    /**
     * Executes all commands in command array in sequence
     * @param history stack used to store successfully executed commands,ref for undo operations
     */
    public void executeCommand(Stack<Command> history){
        for(Command cmd : cmdToExecute){
            try{
                if(cmd.execute())
                    history.push(cmd);
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
