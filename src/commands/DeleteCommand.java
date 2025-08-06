package commands;

import data.Receiver;


public class DeleteCommand implements Command {
    private final int index;
    private final Receiver receiver;
    private String[] deletedPayload;
    public boolean isUndoable = true;
    public String cmdType = "Delete";

    public DeleteCommand(Receiver receiver, String index){
        this.receiver = receiver;
        this.index = Integer.parseInt(index)-1;
    }
    @Override
    public boolean execute() throws IndexOutOfBoundsException{
        deletedPayload = receiver.tempDatastore.get(index);
        return receiver.deleteEntry(index);
    }
    @Override
    public void undo(){
        receiver.addEntry(index, deletedPayload);
    }

    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }

    @Override
    public String checkCmdType(){
        return cmdType;
    }
}
