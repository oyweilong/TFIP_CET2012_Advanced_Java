package commands;

import data.Receiver;
import exceptions.CustomException;

/**
 * Concrete Command Class to implement Delete Command
 */
public class DeleteCommand implements Command {
    // ===== FIELDS =====
    private final int index;
    private final Receiver receiver;
    private String[] deletedPayload;
    public boolean isUndoable = true;
    // ===== CONSTRUCTORS =====

    /**
     *
     * @param receiver receiver instance to accept commands
     * @param index index of where the data is to be deleted
     */
    public DeleteCommand(Receiver receiver, String index){
        this.receiver = receiver;
        this.index = Integer.parseInt(index)-1;
    }

    /**
     * Execution method for Delete Command
     * @return true if deletion is successful
     * @throws IndexOutOfBoundsException if the index given is invalid
     */
    @Override
    public boolean execute() throws IndexOutOfBoundsException{
        try{
            deletedPayload = receiver.tempDatastore.get(index);
            System.out.println("Delete");
            receiver.deleteEntry(index);
            return true;
        } catch (IndexOutOfBoundsException e){
            throw new CustomException("Invalid index for deletion");
        }
    }

    /**
     * Undo method for Delete, to undo deletion using add method
     */
    @Override
    public void undo(){
        receiver.addEntry(index, deletedPayload);
    }

    /**
     * Getter method for an undoable flag for Delete Command
     * @return isUndoable field
     */
    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
