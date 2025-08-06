package client;

import commands.*;
import data.Receiver;
import invoker.Invoker;

import java.util.Stack;

import static client.Entry.cmdArr;

public class Driver {
    static int cmdCount = 0;
    static Command[] cmdArr= new Command[1];
    public static void main(String[] args) {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();
       Stack<Command> history = new Stack<>();



       //Client execution

        add(r, "Jane Lai jane_lai@gmail.com");
        update(r, "a","Gary lim garysim@gmail.com");
        list(r);
        delete(r, "1");
        undo(r, history);
        list(r);
//
//        Entry.list(r);
//        Entry.update(r, 2, "Tim");
//        Entry.list(r);
//        Entry.delete(r, 4);

//        r.loadFromFile();
        invoker.setCommandsForExecution(Driver.cmdArr);
        invoker.executeCommand(history);
//        System.out.println(Entry.history);

        r.storeToFile();

    }
    public static void add(Receiver r, String payload){
        AddCommand add = new AddCommand(r, payload);
        addToCmdArr(add,cmdArr, cmdCount);
    }

    public static void update(Receiver r, String index, String payload){
        try{
            UpdateCommand update = new UpdateCommand(r, index, payload);
            addToCmdArr(update, cmdArr, cmdCount);
        } catch(NumberFormatException e){
            System.out.println("Invalid index for update");
        }
    }

    public static void delete(Receiver r, String index){
        try{
            DeleteCommand delete = new DeleteCommand(r, index);
            addToCmdArr(delete, cmdArr, cmdCount);
        } catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid index for deletion");
        }

    }
    public static void list(Receiver r){
        ListCommand list = new ListCommand(r);
        addToCmdArr(list, cmdArr, cmdCount);
    }

    public static void undo(Receiver r, Stack<Command> history){
        UndoCommand undo = new UndoCommand(r, history);
        addToCmdArr(undo, cmdArr, cmdCount);
    }
    private static void addToCmdArr(Command command, Command[] cmdArr,
                                    int cmdCount){
        //Try-catch block for dynamically increasing size of cmdArr when needed
        try{
            cmdArr[cmdCount] = command;
            Driver.cmdCount++;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Command[] new_cmdArr = new Command[cmdArr.length+1];
            System.arraycopy(cmdArr, 0, new_cmdArr, 0, cmdArr.length);
            cmdArr = new_cmdArr;
            cmdArr[cmdCount] = command;
            Driver.cmdCount++;
        }
    }

}