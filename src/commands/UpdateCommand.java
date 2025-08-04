package commands;

import data.Receiver;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String payload;
    private String[] originalPayload;

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
            this.originalPayload = receiver.tempDatastore.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Update failed: Invalid index to update");
            return false;
        }
        return receiver.updateEntry(index,
                this.parsePayload(payload),
                this.originalPayload);
    }

    @Override
    public void undo(){
        receiver.deleteEntry(index);
        receiver.addEntry(index, originalPayload);
    }
}
