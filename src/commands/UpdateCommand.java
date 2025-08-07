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

    public UpdateCommand(Receiver receiver, String payload)
    {
        this.receiver = receiver;
        String[] choppedPayload = this.parsePayload(payload);
        this.payload = new String[choppedPayload.length-1];
        System.arraycopy(choppedPayload, 1, this.payload, 0, choppedPayload.length-1);
        this.index = Integer.parseInt(choppedPayload[0]) - 1;
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
            Pattern p1 = Pattern.compile("@+");
            Pattern p2 = Pattern.compile("\\w+");
            Matcher m1 = p1.matcher(payloadArr[2]);
            Matcher m2 = p2.matcher(payloadArr[2]);
            if (m1.find()){
                Pattern p3 = Pattern.compile(
                        "^\\w(\\w|[.-](?![.-]))+\\w@[\\w^_]" +
                                "([\\w^_]|([.-](?![.-])))+[\\w^_]\\.[a-z]{2,3}");
                Matcher m = p3.matcher(payloadArr[2]);
                if (!m.matches()) {
                    throw new CustomException("Invalid email format");
                }
                else validatedPayload[2] = payloadArr[2];
            } else if (m2.find()){
                validatedPayload[2] = toTitlecase(payloadArr[2]);
            }

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
            if(validateAndExecute(payload)){
                System.out.println("update");
                return receiver.updateEntry(index, validatedPayload, originalPayload);
            }

            else return false;
        } catch (IndexOutOfBoundsException|NumberFormatException e){
            throw new CustomException("Update failed: Invalid index to update");
        }
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
