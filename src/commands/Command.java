package commands;

/**
 * Command interface to declare methods for executing commands.
 */
public interface Command {
    /**
     * Executes command
     * @return true if successful, else false.
     * @throws RuntimeException if execute fails unexpectedly.
     */
    boolean execute() throws RuntimeException;

    /**
     * Checks if command can be undone
     * @return true if undoable, else false.
     */
    boolean checkUndoable();

    /**
     * Returns the type of command
     * @return command type
     */
    String checkCmdType();

    /**
     * Undoes command. Default is no action.
     */
    default void undo(){
    }

    /**
     * Splits single payload string into an array of individual data items
     * @param payload original input string
     * @return array of data items
     */
    default String[] parsePayload(String payload){
        String[] payloadArr = payload.split(" ");
        for (int i = 0; i < payloadArr.length; i++) {
            payloadArr[i] = payloadArr[i].trim();
        }
        return payloadArr;
    }

    /**
     * To set First Name and Last Name to Title Case
     * @param string input of names
     * @return title-cased names
     */
    default String toTitlecase(String string){
        char[] charArray = string.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        for (int i = 1; i < charArray.length; i++) {
            charArray[i] = Character.toLowerCase(charArray[i]);
        }
        return String.valueOf(charArray);
    }



}
