package commands;

import data.Receiver;
import exceptions.CustomException;

/**
 * Concrete Command Class to implement Delete Command
 */
public class DeleteCommand implements Command {
    // ===== FIELDS =====
    private String tempIndex;
    private int index;
    private final Receiver receiver;
    private String[] deletedPayload;
    public boolean isUndoable = true;
    // ===== CONSTRUCTORS =====

    /**
     * Constructor for Delete Command
     * @param receiver receiver instance to accept commands
     * @param index index of where the data is to be deleted
     */
    public DeleteCommand(Receiver receiver, String index){
        this.receiver = receiver;
        tempIndex = index;

    }

    /**
     * Execution method for Delete Command
     * @return true if deletion is successful
     * @throws IndexOutOfBoundsException if the index given is invalid
     */
    @Override
    public boolean execute() throws IndexOutOfBoundsException,NumberFormatException{
        try{
            index = Integer.parseInt(tempIndex)-1;
            deletedPayload = receiver.tempDatastore.get(index);
            System.out.println("Delete");
            receiver.deleteEntry(index);
            return true;
        } catch (IndexOutOfBoundsException| NumberFormatException e){
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
