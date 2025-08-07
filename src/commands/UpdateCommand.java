package commands;

import data.Receiver;
import exceptions.CustomException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String[] payload;
    private String[] originalPayload;
    private final String[] validatedPayload;
    public boolean isUndoable = true;

    public UpdateCommand(Receiver receiver, String index, String payload)
    {
        this.receiver = receiver;
        this.index = Integer.parseInt(index) - 1;
        this.payload = parsePayload(payload);
        this.validatedPayload = new String[this.payload.length];
    }

    // Validation for UPDATE command
    public boolean validateAndExecute(String[] payloadArr) throws CustomException {
        // Layer 1: Check the number of payloads
        if (payloadArr.length ==0 || payloadArr.length > 3) {
            throw new CustomException("Invalid Update command. \n" +
                    "Update command format: <firstName> <lastName> <email>,\n"+
                    "<firstName> <lastName>,\n" +
                    "<firstName>\n" +
                    "Example: John Doe john@example.com");
        }
        //Layer 2: Email validation
        if (payloadArr.length == 3) {
            Pattern p = Pattern.compile(
                    "^\\w(\\w|[.-](?![.-]))+\\w@[\\w^_]" +
                            "([\\w^_]|([.-](?![.-])))+[\\w^_]\\.[a-z]{2,3}");
            Matcher m = p.matcher(payloadArr[2]);
            if (!m.matches()) {
                throw new CustomException("Invalid email format");
            }
            else validatedPayload[2] = payloadArr[2];
        }
        //set the first name and last name to Titlecase
        switch (payloadArr.length){
            case 1:
                validatedPayload[0] = toTitlecase(payloadArr[0]);
                break;
            case 2,3:
                validatedPayload[0] = toTitlecase(payloadArr[0]);
                validatedPayload[1] = toTitlecase(payloadArr[1]);
                break;
        }
        return true;
    }
    @Override
    public boolean execute() throws RuntimeException{
        try{
            originalPayload = receiver.tempDatastore.get(index);
        } catch (IndexOutOfBoundsException e){
            throw new CustomException("Update failed: Invalid index to update");
        }
        if(validateAndExecute(payload))
            return receiver.updateEntry(index, validatedPayload, originalPayload);
        else return false;
    }

    @Override
    public void undo(){
        receiver.deleteEntry(index);
        receiver.addEntry(index, originalPayload);
    }

    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
