
import data.Entry;
import data.Receiver;
import invoker.Invoker;

public class Driver {
    public static void main(String[] args) {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();

       //Client execution
        Entry.undo();
        Entry.add(r, "John Doe <EMAIL>");
        Entry.add(r, "Jane Lai <EMAIL>");
        Entry.add(r, "Bob Lim <EMAIL>");
        Entry.list(r);
        Entry.update(r, 2, "King Kong <EMAIL>");
        Entry.delete(r, 1);
        Entry.list(r);
        Entry.undo();
        Entry.list(r);
        Entry.update(r, 2, "Tim");
        Entry.list(r);
        Entry.delete(r, 4);


        invoker.setCommandsForExecution(Entry.cmdArr);
        invoker.executeCommand(Entry.history);
        System.out.println(Entry.history);

    }

}