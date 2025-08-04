package commands;

import data.Receiver;

public class UndoCommand implements Command {
    private final Receiver receiver;

    public UndoCommand(){
        receiver = new Receiver();
    }

    @Override
    public boolean execute(){
        receiver.undo();
        return false;
    }
}
