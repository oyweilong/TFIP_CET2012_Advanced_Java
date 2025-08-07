package commands;

import data.Receiver;
import exceptions.CustomException;

import java.util.EmptyStackException;
import java.util.Stack;

public class UndoCommand implements Command {
    private final Receiver receiver;
    private Stack<Command> history;
    public boolean isUndoable = false;

    public UndoCommand(Receiver receiver, Stack<Command> history){
       this.receiver = receiver;
       this.history = history;
    }

    @Override
    public boolean execute() throws EmptyStackException{
        try{
            //Cleaning history of Commands that are not undoable
            for (int i = 0; i < history.size(); i++) {
                if (!history.get(i).checkUndoable())
                    history.remove(i);
            }
            history.pop().undo();
            System.out.println("Undo");
            return false;
        } catch (EmptyStackException e){
            throw new CustomException("No commands to undo");
        }
    }
    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
