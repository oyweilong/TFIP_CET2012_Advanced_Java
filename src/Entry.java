import commands.AddCommand;
import commands.Command;
import commands.DeleteCommand;
import commands.ListCommand;
import data.Receiver;

import java.util.Stack;

public class Entry {
    static Command[] cmdArr= new Command[1];
    private static int cmdCount = 0;
    static Stack<Command> history = new Stack<>();

    public static void add(Receiver r, String firstname, String lastname,
                     String email){
        AddCommand add = new AddCommand(r, firstname, lastname, email );
        addToCmdArr(add);
    }

    public static void delete(Receiver r, int index){
        DeleteCommand delete = new DeleteCommand(r, index);
        addToCmdArr(delete);
    }
    public static void list(Receiver r){
        ListCommand list = new ListCommand(r);
        addToCmdArr(list);
    }

    private static void addToCmdArr(Command command){
        //Try-catch block for dynamically increasing size of cmdArr when needed
        try{
            cmdArr[cmdCount] = command;
            cmdCount++;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Command[] new_cmdArr = new Command[cmdArr.length+1];
            System.arraycopy(cmdArr, 0, new_cmdArr, 0, cmdArr.length);
            cmdArr = new_cmdArr;
            cmdArr[cmdCount] = command;
            cmdCount++;
        }
    }


}

