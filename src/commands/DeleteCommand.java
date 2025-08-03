package commands;

import data.Receiver;

public class DeleteCommand implements Command {
    private int index;
    private Receiver receiver;

    public DeleteCommand(Receiver receiver, int index){
        this.receiver = receiver;
        this.index = index;
    }
    @Override
    public void execute(){
        receiver.deleteEntry(index);
    }

}
