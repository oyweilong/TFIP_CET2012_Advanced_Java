package invoker;

import commands.Command;
import exceptions.CustomException;

import java.util.Stack;

/**
 * Invoker class to initiate and trigger commands.
 * Contains Command[] to manage command execution and history.
 */
public class Invoker {
    private Command[] cmdToExecute;
    public void setCommandsForExecution(Command[] cmdToExecute){
        this.cmdToExecute = cmdToExecute;
    }
    public void executeCommand(Stack<Command> history){
        for(Command cmd : cmdToExecute){
            boolean cmdSuccess = false;
            try{
                if(cmd.execute())
                    cmdSuccess = true;
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            } finally{
                if (cmdSuccess)
                    history.push(cmd);
            }
        }
    }
}
