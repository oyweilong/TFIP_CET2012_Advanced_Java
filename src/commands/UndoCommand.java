package commands;

import data.Receiver;
import exceptions.CustomException;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Concrete Command Class to implement Undo Command
 */
public class UndoCommand implements Command {
    // ===== FIELDS =====
    private final Receiver receiver;
    private Stack<Command> history;
    public boolean isUndoable = false;

    // ===== CONSTRUCTORS =====

    /**
     * Constructor for Undo Command
     * @param receiver receiver instance to accept commands
     * @param history input containing data items
     */
    public UndoCommand(Receiver receiver, Stack<Command> history){
       this.receiver = receiver;
       this.history = history;
    }

    /**
     * Execution method for Undo Command.
     * @return true if there is command to undo.
     * @throws EmptyStackException if there is no command to undo.
     */
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

    /**
     * Getter method for an undoable flag for Undo Command
     * @return isUndoable field
     */
    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
