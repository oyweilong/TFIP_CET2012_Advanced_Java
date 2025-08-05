package commands;

public interface Command {
    boolean execute();
    default void undo(){}
    default String[] parsePayload(String payload){
        String[] payloadArr = payload.split(" ");
        for (int i = 0; i < payloadArr.length; i++) {
            payloadArr[i] = payloadArr[i].trim();
        }
        return payloadArr;
    }

    default String toTitlecase(String string){
        char[] charArray = string.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        for (int i = 1; i < charArray.length; i++) {
            charArray[i] = Character.toLowerCase(charArray[i]);
        }
        return String.valueOf(charArray);
    }

}
