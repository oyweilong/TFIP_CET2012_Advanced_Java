package commands;

import java.util.ArrayList;
import java.util.Arrays;

public interface Command {

    boolean execute();
    default String[] parsePayload(String payload){
        String[] payloadArr = payload.split(" ");
        for (int i = 0; i < payloadArr.length; i++) {
            payloadArr[i] = payloadArr[i].trim();
        }

        return payloadArr;
    }
}
