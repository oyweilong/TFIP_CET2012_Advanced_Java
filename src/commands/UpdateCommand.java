package commands;

import data.Receiver;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String firstname;
    private String lastname;
    private String email;

    public UpdateCommand(Receiver receiver, int index, String firstname)
    {
        this.receiver = receiver;
        this.index = index;
        this.firstname = firstname;
    }

    public UpdateCommand(Receiver receiver, int index, String firstname,
                         String lastname)
    {
        this(receiver, index, firstname);
        this.lastname = lastname;
    }

    public UpdateCommand(Receiver receiver, int index, String firstname,
                         String lastname, String email)
    {
        this(receiver, index, firstname, lastname);
        this.email = email;
    }
    @Override
    public boolean execute(){
        return receiver.updateEntry(index, firstname, lastname, email);
    }
}
