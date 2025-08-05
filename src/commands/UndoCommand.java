package commands;

import data.Receiver;

import java.util.EmptyStackException;
import java.util.Stack;

public class UndoCommand implements Command {
    private final Receiver receiver;
    private final Stack<Command> history;

    public UndoCommand(Receiver receiver, Stack<Command> history){
       this.receiver = receiver;
       this.history = history;
    }

    @Override
    public boolean execute(){
        try{
            receiver.undo(history);
        } catch (EmptyStackException e){
            System.out.println("No commands to undo");
        }
        return false;
    }
}
