package commands;

import data.Receiver;
import exceptions.CustomException;


public class DeleteCommand implements Command {
    private final int index;
    private final Receiver receiver;
    private String[] deletedPayload;
    public boolean isUndoable = true;

    public DeleteCommand(Receiver receiver, String index){
        this.receiver = receiver;
        this.index = Integer.parseInt(index)-1;
    }
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
    @Override
    public void undo(){
        receiver.addEntry(index, deletedPayload);
    }

    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
