
import commands.Command;
import data.Receiver;
import invoker.Invoker;

import java.util.Stack;

public class Driver {
    public static void main(String[] args) {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();
       Command[] cmd_arr = new Command[100];
       int cmd_counter = 0;
       Stack<Command> history = new Stack<>();


       //Client execution
        Employee e = new Employee("John", "Doe", "<EMAIL>");
        e.add(cmd_arr, r);
        Employee e2 = new Employee("Jane", "Lai", "<EMAIL>");
        e2.add(cmd_arr, r);

        invoker.setCommandsForExecution(cmd_arr);
        invoker.executeCommand(history);


    }

}