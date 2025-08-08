package commands;

import data.Receiver;
import exceptions.CustomException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Concrete Command Class to implement Update Command
 */
public class UpdateCommand implements Command {
    // ===== FIELDS =====
    private final Receiver receiver;
    private String tempIndex;
    private int index;
    private final String[] unvalidatedPayload; // payload without index, unvalidated
    private String[] originalPayload; //
    private final String[] validatedPayload; // payload without index, validated
    public boolean isUndoable = true;

    // ===== CONSTRUCTORS =====
    /**
     * Constructor for Update Command
     * @param receiver receiver instance to accept commands
     * @param payload input containing data items
     */
    public UpdateCommand(Receiver receiver, String payload) {
        this.receiver = receiver;
        String[] choppedPayload = parsePayload(payload);
        unvalidatedPayload = new String[choppedPayload.length-1];
        // NegativeArraySizeException if choppedPayload.length = 0, which result in String[-1]
        System.arraycopy(choppedPayload, 1, unvalidatedPayload, 0, choppedPayload.length-1);
        tempIndex = choppedPayload[0];
        validatedPayload = new String[unvalidatedPayload.length];
    }

    /**
     * Validation method for Update Command, checking number of data items and email format.
     * @param payloadArr String array of original payload.
     * @throws CustomException thrown if missing data items or invalid email format.
     */
    // Validation for UPDATE command
    public void validateAndExecute(String[] payloadArr) throws CustomException {
        // Layer 1: Check the number of payloads
        if (payloadArr.length ==0 || payloadArr.length > 3) {
            throw new CustomException("Invalid Update command. \n" +
                    "Update command format: <index> <firstName> <lastName> <email>,\n"+
                    "<index> <firstName> <lastName>,\n" +
                    "<index> <firstName>\n" +
                    "Example: 1 John Doe john@example.com");
            // text block only compatible with Java 15 onwards
            // program required backward compatibility up to Java 8
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
        //Layer 2: Email validation
        if (payloadArr.length == 3) {
            Pattern p1 = Pattern.compile("@+"); // check for email address using '@' char
            Pattern p2 = Pattern.compile("\\w+"); // check for alphanumeric
            Matcher m1 = p1.matcher(payloadArr[2]);
            Matcher m2 = p2.matcher(payloadArr[2]);
            if (m1.find()){
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
    }

    /**
     * Execution method for Update Command.
     * @return true if payload validation and update is successful.
     * @throws RuntimeException if update index is invalid or out of bounds.
     */
    @Override
    public boolean execute() throws IndexOutOfBoundsException,
                                    NumberFormatException,
                                    NegativeArraySizeException{
        try{
            index = Integer.parseInt(tempIndex) - 1;
            originalPayload = receiver.tempDatastore.get(index);
            validateAndExecute(unvalidatedPayload);
            receiver.updateEntry(index, validatedPayload, originalPayload);
            System.out.println("update");
            return true;
        } catch (IndexOutOfBoundsException|NumberFormatException|NegativeArraySizeException e){
            throw new CustomException("Invalid index to update");
        }
        // NegativeArraySizeException occurs if update index is " " due to parsePayload()
        // parsePayload() will return null for " " after .split(" ")
        // unvalidatedPayload = new String[choppedPayload.length-1] means
        // unvalidatedPayload = new String[null-1], gives String[-1] hence NegativeArraySizeException
    }

    /**
     * Undo method for Update, to undo update of either delete or add entry
     */
    @Override
    public void undo(){
        receiver.deleteEntry(index);
        receiver.addEntry(index, originalPayload);
    }

    /**
     * Method to state that Update Command is undoable
     * @return true that Update Command can undo
     */
    @Override
    public boolean checkUndoable(){
        return isUndoable;
    }
}
