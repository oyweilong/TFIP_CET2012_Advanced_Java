package invoker;

import commands.Command;
import java.util.Stack;

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
            boolean cmdSuccess = false;
            try{
                if(cmd.execute())
                    cmdSuccess = true;
            } catch (RuntimeException e) {
                switch (e.getClass().getSimpleName()){
                    case "CustomException":
                        System.out.println(e.getMessage());
                        break;
                    case "EmptyStackException":
                        System.out.println("No commands to undo");
                        break;
                    case "IndexOutOfBoundsException":
                        if (cmd.checkCmdType().equals("Delete"))
                            System.out.println("Delete failed: Invalid index for deletion");
                        else if (cmd.checkCmdType().equals("Update"))
                            System.out.println("Update failed: Invalid index to update");
                }
            } finally{
                if (cmdSuccess)
                    history.push(cmd);
            }
        }
    }
}
