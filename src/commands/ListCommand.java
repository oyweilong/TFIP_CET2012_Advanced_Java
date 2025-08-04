package commands;

import data.Receiver;

public class ListCommand implements Command {
    private final Receiver receiver;
    public ListCommand(Receiver receiver){
        this.receiver = receiver;
    }
    @Override
    public boolean execute(){
        if (receiver.tempDatastore.isEmpty()){
            System.out.println("No file loaded or no entries to list");
            return false;
        }
        receiver.list();
        return false;
    }
}
