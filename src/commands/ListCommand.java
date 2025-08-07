package commands;

import data.Receiver;
import exceptions.CustomException;

public class ListCommand implements Command {
    private final Receiver receiver;
    public boolean isUndoable = false;

    public ListCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public boolean execute() throws CustomException {
        if (receiver.tempDatastore.isEmpty()){
            throw new CustomException("No file loaded or no entries to list");
        }
        receiver.list();
        return false;
    }

    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
