package commands;

import data.Receiver;

public class DeleteCommand implements Command {
    private final int index;
    private final Receiver receiver;
    private String[] deletedPayload;

    public DeleteCommand(Receiver receiver, int index){
        this.receiver = receiver;
        this.index = index;
    }
    @Override
    public boolean execute(){
        this.deletedPayload = receiver.tempDatastore.get(index);
        return receiver.deleteEntry(index);
    }
    @Override
    public void undo(){
        receiver.addEntry(index, deletedPayload);
    }
}
