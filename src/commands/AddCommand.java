package commands;

import data.Receiver;

public class AddCommand implements Command {
    private final Receiver receiver;
    private final String payload;


    public AddCommand(Receiver receiver, String payload){
        this.receiver = receiver;
        this.payload = payload;
    }

    @Override
    public boolean execute(){
        return receiver.addEntry(payload);
    }


}
