
import commands.Command;
import data.Receiver;
import invoker.Invoker;
import java.util.Stack;

public class Driver {
    public static void main(String[] args) {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();
       Stack<Command> history = new Stack<>();


       //Client execution
        Entry e = new Entry("John", "Doe", "<EMAIL>");
        e.add(r);
        Entry e2 = new Entry("Jane", "Lai", "<EMAIL>");
        e2.add(r);
        Entry.list(r);


        invoker.setCommandsForExecution(Entry.cmd_arr);
        invoker.executeCommand(history);

    }

}