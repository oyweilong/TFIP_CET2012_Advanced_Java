package commands;

import data.Receiver;
import exceptions.CustomException;
import java.util.regex.Matcher; // ver. 1.4
import java.util.regex.Pattern; // ver. 1.4

/**
 * Concrete Command Class to implement Add Command
 */
public class AddCommand implements Command {
    // ===== FIELDS =====
    private final Receiver receiver;
    private final String[] payload; // string array of original payload
    private final String[] validatedPayload = new String[3]; // email validated payload string array

    /**
     * boolean to record if command is undoable, true for Add Command
     */
    public boolean isUndoable = true;

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
     * @throws CustomException thrown if missing data items or invalid email format.
     */
    public void validateAndExecute(String[] payloadArr) throws CustomException{
        // Layer 1: Check the number of data items from payload
        if (payloadArr.length != 3) {
            throw new CustomException("Invalid Add command. Add command " +
                    "format: " +
                    "<firstName> " +
                    "<lastName> <email>\n" +
                    "Example: add John Doe john@example.com");
        }

        // Sort data items into array for processing
        validatedPayload[0] = toTitlecase(payloadArr[0]);
        validatedPayload[1] = toTitlecase(payloadArr[1]);

        Pattern p1 = Pattern.compile("@+"); // // check for email address using '@' char
        Pattern p2 = Pattern.compile("\\w+"); // check for alphanumeric
        Matcher m1 = p1.matcher(payloadArr[2]);
        Matcher m2 = p2.matcher(payloadArr[2]);
        if (m1.find()){
            //Layer 2: Email validation
            Pattern p3 = Pattern.compile(
                    "^\\w(\\w|[.-](?![.-]))+\\w@[\\w^_]" +
                            "([\\w^_]|([.-](?![.-])))+[\\w^_]\\.[a-z]{2,3}");
            Matcher m3 = p3.matcher(payloadArr[2]);
            if (!m3.matches()) {
                throw new CustomException("Invalid email format");
            }
            // if email address is valid, assign to index <data3>
            else validatedPayload[2] = payloadArr[2];
        }
        // if alphanumeric is valid, assign to index <data3>
        else if (m2.matches()){
            validatedPayload[2] = toTitlecase(payloadArr[2]);
        }
        // if neither email address nor alphanumeric
        else
            throw new CustomException("Invalid email format");
    }

    /**
     * Execution method for Add Command.
     * @return true if payload validation and addition is successful.
     * @throws CustomException if validation or addition is unsuccessful.
     */
    @Override
    public boolean execute() throws CustomException{
        validateAndExecute(payload);
        receiver.addEntry(receiver.tempDatastore.size(), validatedPayload);
        System.out.println("add");
        return true;
    }

    /**
     * Undo method for Add, to undo addition using delete method
     */
   @Override
    public void undo(){
        receiver.deleteEntry(receiver.tempDatastore.size()-1);
    }

    /**
     * Getter method for an undoable flag for Add Command
     * @return isUndoable field
     */
    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
