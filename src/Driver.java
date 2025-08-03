
import commands.Command;
import data.Receiver;
import invoker.Invoker;
import java.util.Stack;

public class Driver {
    public static void main(String[] args) {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();

       //Client execution
        Entry.delete(r, 1);
        Entry.add(r, "John", "Doe", "<EMAIL>");
        Entry.add(r, "Jane", "Lai", "<EMAIL>");
        Entry.delete(r, 1);
        Entry.list(r);


        invoker.setCommandsForExecution(Entry.cmdArr);
        invoker.executeCommand(Entry.history);
        System.out.println(Entry.history);

    }

}