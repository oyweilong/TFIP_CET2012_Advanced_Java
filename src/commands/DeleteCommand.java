package commands;

import data.Receiver;

public class DeleteCommand implements Command {
    private final int index;
    private final Receiver receiver;

    public DeleteCommand(Receiver receiver, int index){
        this.receiver = receiver;
        this.index = index;
    }
    @Override
    public boolean execute(){
        return (receiver.deleteEntry(index));
    }

}
