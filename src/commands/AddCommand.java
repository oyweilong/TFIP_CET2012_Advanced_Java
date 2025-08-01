package commands;

import data.Receiver;

public class AddCommand implements Command {
    private Receiver receiver;
    private String firstname;
    private String lastname;
    private String email;

    public AddCommand(Receiver receiver, String firstname, String lastname,
                      String email){
        this.receiver = receiver;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    @Override
    public void execute(){
        receiver.add(firstname, lastname, email);
    }


}
