package invoker;

import commands.Command;
import java.util.Stack;

public class Invoker {
    private Command[] cmdToExecute;

    public void setCommandsForExecution(Command[] cmdToExecute){
        this.cmdToExecute = cmdToExecute;
    }
    public void executeCommand(Stack<Command> history){
        for(Command cmd : cmdToExecute){
            if (cmd != null) {
                cmd.execute();
                history.push(cmd);
            }
        }
    }
}
