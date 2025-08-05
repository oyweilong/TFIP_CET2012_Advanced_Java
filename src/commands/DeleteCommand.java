package commands;

import data.Receiver;


public class DeleteCommand implements Command {
    private final int index;
    private final Receiver receiver;
    private String[] deletedPayload;

    public DeleteCommand(Receiver receiver, String index){
        this.receiver = receiver;
        this.index = Integer.parseInt(index)-1;
    }
    @Override
    public boolean execute(){
        try{
            deletedPayload = receiver.tempDatastore.get(index);
            return receiver.deleteEntry(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Delete failed: Invalid index for deletion");
            return false;
        }

    }
    @Override
    public void undo(){
        receiver.addEntry(index, deletedPayload);
    }
}
