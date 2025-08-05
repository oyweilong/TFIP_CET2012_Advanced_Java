package commands;

import data.Receiver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateCommand implements Command {
    private final Receiver receiver;
    private final int index;
    private final String[] payload;
    private String[] originalPayload;
    private String[] validatedPayload;

    //TODO Add try catch to catch invalid payload inputs, index etc
    public UpdateCommand(Receiver receiver, String index, String payload)
    {
        this.receiver = receiver;
        this.index = Integer.parseInt(index) - 1;
        this.payload = parsePayload(payload);
        this.validatedPayload = new String[this.payload.length];
    }

    // Validation for UPDATE command
    public boolean validateAndExecute(String[] payloadArr) {
        // Layer 1: Check the number of payloads
        if (payloadArr.length ==0 || payloadArr.length > 3) {
            System.out.print("Invalid Update command. \n" +
                    "Update command format: <firstName> <lastName> <email>,\n"+
                    "<firstName> <lastName>,\n" +
                    "<firstName>\n" +
                    "Example: John Doe john@example.com");
            return false;
        }
        //Layer 2: Email validation
        if (payloadArr.length == 3) {
            Pattern p = Pattern.compile(
                    "^[A-Za-z0-9]([A-Za-z0-9]|[_.-](?![_.-]))+[A-Za-z0-9]@[A-Za-z0-9]" +
                            "([A-Za-z0-9]|([.-](?![.-])))+[A-Za-z0-9]\\.[a-z]{2,3}");
            Matcher m = p.matcher(payloadArr[2]);
            if (!m.matches()) {
                System.out.println("Invalid email format");
                return false;
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
    public boolean execute(){
        try {
            originalPayload = receiver.tempDatastore.get(index);
        } catch (IndexOutOfBoundsException e){
            System.out.println("Update failed: Invalid index to update");
            return false;
        }
        if(validateAndExecute(payload)){
            return receiver.updateEntry(index, validatedPayload, originalPayload);
        }
        else return false;

    }

    @Override
    public void undo(){
        receiver.deleteEntry(index);
        receiver.addEntry(index, originalPayload);
    }
}
