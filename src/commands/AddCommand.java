package commands;

import data.Receiver;

import java.util.Stack;

public class AddCommand implements Command {
    private final Receiver receiver;
    private final String payload;


    public AddCommand(Receiver receiver, String payload){
        this.receiver = receiver;
        this.payload = payload;
    }

    // Validation for ADD command
    public void validateAndExecute(String[] payloadArr) {
        if (payloadArr.length != 4) {
            System.out.println("Add command format: add <firstName> <lastName> <email>\n" +
                    "Example: add John Doe john@example.com");
            return;
        }

    // Layer 1: Basic parameter checks
    String firstName = payloadArr[1];
    String lastName = payloadArr[2];
    String email = payloadArr[3];

    if (firstName.isEmpty()){
        System.out.println("First name is empty");
        return;
    }
    if (lastName.isEmpty()){
        System.out.println("Last name is empty");
    }
    if (email.isEmpty()){
        System.out.println("Email is empty");
    }


    }

    @Override
    public boolean execute(){
        return receiver.addEntry(payload);
    }


}
