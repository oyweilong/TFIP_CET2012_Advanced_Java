package invoker;

import commands.Command;
import java.util.Stack;

/**
 * Invoker class to initiate and trigger commands.
 * Contains Command array to manage command execution and history.
 */
public class Invoker {
    private Command[] cmdToExecute;

    public void setCommandsForExecution(Command[] cmdToExecute){
        this.cmdToExecute = cmdToExecute;
    }
    public void executeCommand(Stack<Command> history){
        for(Command cmd : cmdToExecute){
                if (cmd.execute())
                    history.push(cmd);
        }
    }
}
