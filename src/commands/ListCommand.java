package commands;

import data.Receiver;
import exceptions.CustomException;

/**
 * Concrete Command Class to implement List Command
 */

public class ListCommand implements Command {
    // ===== FIELDS =====
    private final Receiver receiver;

    /**
     * boolean to record if command is undoable, false for List Command
     */
    public boolean isUndoable = false;

    // ===== CONSTRUCTORS =====
    /**
     * Constructor for List Command
     * @param receiver  receiver instance to accept commands
     */
    public ListCommand(Receiver receiver){
        this.receiver = receiver;
    }

    /**
     * Execution method for List Command
     * @return false by default to prevent insertion into history Stack
     * @throws CustomException
     */
    @Override
    public boolean execute() throws CustomException {
        if (receiver.tempDatastore.isEmpty()){
            throw new CustomException("No file loaded or no entries to list");
        }
        receiver.list();
        return false;
    }

    /**
     * Getter method for an undoable flag for List Command
     * @return isUndoable field
     */
    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
