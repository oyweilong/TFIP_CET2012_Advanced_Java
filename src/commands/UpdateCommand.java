package commands;

import data.Receiver;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String firstname;
    private final String lastname;
    private final String email;

    public UpdateCommand(Receiver receiver, int index, String firstname,
                         String lastname, String email){
        this.receiver = receiver;
        this.index = index;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    @Override
    public boolean execute(){
        return receiver.updateEntry(index, firstname, lastname, email);
    }
}
