package commands;

import data.Receiver;

public class ListCommand implements Command {
    private final Receiver receiver;
    public ListCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public boolean execute(){
        receiver.list();
        return false;
    }
}
