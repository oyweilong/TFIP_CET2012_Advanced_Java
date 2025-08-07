package client;

import commands.*;
import data.Receiver;
import invoker.Invoker;

import java.util.Stack;

public class Driver {
    static int cmdCount = 0;
    static Command[] cmdArr= new Command[1];

    public static void main(String[] args) {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();
       Stack<Command> history = new Stack<>();

       //Client execution

//        add(r, "Jane Lai .jane_lai@gmail.com");
//        add(r, "King koNg king__.kong@gmail.com");
//        add(r, "Sam peReZ _.sam-perez._@gmail.com");
//        update(r, "1","Gary lim garysim@gmail.com");
//        list(r);
//        delete(r, "5");
//        Command cmd = new ListCommand(r);
//        history.push(cmd);
//        undo(r, history);
//        list(r);

        add (r, "First_name Last_name <EMAIL>");
        add (r, "John Doe simple@example.com");
        add (r, "Hanna Moon tetter.tots@potatoesarelife.com");
        add (r, "Ah Boon green-tea@teaforlife.com");
        list(r);
        update(r, "3", "Adam");
        list(r);
        update(r, "1", "blue bell ice-cream@alaskaFields.org");
        list(r);
        delete(r, "1");
        list(r);
        undo(r, history);
        list(r);

//        r.loadFromFile();
        invoker.setCommandsForExecution(Driver.cmdArr);
        invoker.executeCommand(history);
        r.storeToFile();

    }
    public static void add(Receiver r, String payload){
        AddCommand add = new AddCommand(r, payload);
        addToCmdArr(add);
    }

    public static void update(Receiver r, String index, String payload){
        try{
            UpdateCommand update = new UpdateCommand(r, index, payload);
            addToCmdArr(update);
        } catch(NumberFormatException e){
            System.out.println("Invalid index for update");
        }
    }

    public static void delete(Receiver r, String index){
        try{
            DeleteCommand delete = new DeleteCommand(r, index);
            addToCmdArr(delete);
        } catch(NumberFormatException | ArrayIndexOutOfBoundsException e){
            System.out.println("Invalid index for deletion");
        }

    }
    public static void list(Receiver r){
        ListCommand list = new ListCommand(r);
        addToCmdArr(list);
    }

    public static void undo(Receiver r, Stack<Command> history){
        UndoCommand undo = new UndoCommand(r, history);
        addToCmdArr(undo);
    }
    private static void addToCmdArr(Command command){
        //Try-catch block for dynamically increasing size of cmdArr when needed
        try{
            Driver.cmdArr[cmdCount] = command;
            Driver.cmdCount++;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            Command[] new_cmdArr = new Command[Driver.cmdArr.length+1];
            System.arraycopy(cmdArr, 0, new_cmdArr, 0,  Driver.cmdArr.length);
            Driver.cmdArr = new_cmdArr;
            Driver.cmdArr[cmdCount] = command;
            Driver.cmdCount++;
        }
    }

}