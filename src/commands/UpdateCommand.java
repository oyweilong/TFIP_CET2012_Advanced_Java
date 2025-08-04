package commands;

import data.Receiver;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String payload;
    private String originalpayload;

    //TODO Add try catch to catch invalid payload inputs, index etc
    public UpdateCommand(Receiver receiver, int index, String payload)
    {
        this.receiver = receiver;
        this.index = index;
        this.payload = payload;
    }

    @Override
    public boolean execute(){
        try {
            this.originalpayload = receiver.tempDatastore.get(index).getFirst();
        } catch (IndexOutOfBoundsException e){
            System.out.println("Index out of bounds");
            return false;
        }
        return receiver.updateEntry(index, this.parsePayload(payload));
    }
}
