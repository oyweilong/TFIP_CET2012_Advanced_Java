package data;

import commands.*;

import java.util.Stack;

public class Entry {
    public static Command[] cmdArr= new Command[1];
    private static int cmdCount = 0;
    public static Stack<Command> history = new Stack<>();

    public static void add(Receiver r, String payload){
        AddCommand add = new AddCommand(r, payload);
        addToCmdArr(add);
    }

   public static void update(Receiver r, String index, String payload){
        UpdateCommand update = new UpdateCommand(r, index, payload);
        addToCmdArr(update);
   }

    public static void delete(Receiver r, String index){
        DeleteCommand delete = new DeleteCommand(r, index);
        addToCmdArr(delete);
    }
    public static void list(Receiver r){
        ListCommand list = new ListCommand(r);
        addToCmdArr(list);
    }

    public static void undo(){
        UndoCommand undo = new UndoCommand();
        addToCmdArr(undo);
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

