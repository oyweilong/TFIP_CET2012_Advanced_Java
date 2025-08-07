package commands;

import data.Receiver;
import exceptions.CustomException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Concrete Command Class to implement Add Command
 */
public class AddCommand implements Command {
    // ===== FIELDS =====
    private final Receiver receiver;
    private final String[] payload; // string array of original payload
    private final String[] validatedPayload = new String[3]; // email validated payload string array
    public boolean isUndoable = true; // boolean to record if command is undoable

    // ===== CONSTRUCTORS =====
    /**
     * Constructor for Add Command
     * @param receiver receiver instance to accept commands
     * @param payload input containing data items
     */
    public AddCommand(Receiver receiver, String payload){
        this.receiver = receiver;
        this.payload = this.parsePayload(payload);
    }

    /**
     *  Validation method for Add Command, checking number of data items and email format.
     * @param payloadArr String array of original payload.
     * @return returns validated string array of payload.
     * @throws CustomException thrown if missing data items or invalid email format.
     */
    public boolean validateAndExecute(String[] payloadArr) throws CustomException{
        // Layer 1: Check the number of data items from payload
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

        Pattern p1 = Pattern.compile("@+");
        Pattern p2 = Pattern.compile("\\w+");
        Matcher m1 = p1.matcher(payloadArr[2]);
        Matcher m2 = p2.matcher(payloadArr[2]);
        if (m1.find()){
            //Layer 2: Email validation
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

        validatedPayload[0] = firstName;
        validatedPayload[1] = lastName;
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
