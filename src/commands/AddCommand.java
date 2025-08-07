package commands;

import data.Receiver;
import exceptions.CustomException;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCommand implements Command {
    private final Receiver receiver;
    private final String[] payload;
    private final String[] validatedPayload = new String[3];
    public boolean isUndoable = true;

    public AddCommand(Receiver receiver, String payload){
        this.receiver = receiver;
        this.payload = this.parsePayload(payload);
    }

    // Validation for ADD command
    public boolean validateAndExecute(String[] payloadArr) throws CustomException{
        // Layer 1: Check the number of payloads
        if (payloadArr.length != 3) {
            throw new CustomException("Invalid Add command. Add command " +
                    "format: " +
                    "<firstName> " +
                    "<lastName> <email>\n" +
                    "Example: add John Doe john@example.com");
        }

        String firstName = toTitlecase(payloadArr[0]);
        String lastName = toTitlecase(payloadArr[1]);
        String email = payloadArr[2];

        //Layer 2: Email validation
        Pattern p = Pattern.compile(
                "^\\w(\\w|[.-](?![.-]))+\\w@[\\w^_]" +
                        "([\\w^_]|([.-](?![.-])))+[\\w^_]\\.[a-z]{2,3}");
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new CustomException("Invalid email format");
        }

        validatedPayload[0] = firstName;
        validatedPayload[1] = lastName;
        validatedPayload[2] = email;
        return true;
    }



    @Override
    public boolean execute() throws CustomException{
        if (validateAndExecute(payload))
            return receiver.addEntry(validatedPayload);
        else return false;
    }

   @Override
    public void undo(){
        receiver.deleteEntry(receiver.tempDatastore.size()-1);
    }

    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
