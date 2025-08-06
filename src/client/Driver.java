package client;

import data.Receiver;
import exceptions.CustomException;
import invoker.Invoker;

public class Driver {
    public static void main(String[] args) throws CustomException {
       Invoker invoker = new Invoker();
       Receiver r = new Receiver();

       //Client execution
//        Entry.undo();
//        Entry.add(r, "First_Name Last_Name <EMAIL>");
//        Entry.add(r, "joHn doe sad johndoail.com");
        Entry.add(r, "Jane Lai jane_lai@gmail.com");
//        Entry.add(r, "Bob Lim bob__lim@gmail.com");
//        Entry.add(r, "Bob Lim ");
//        Entry.list(r);
//        Entry.update(r, "1", "King Kong <EMAIL>");
        Entry.update(r, "a","Gary lim garysim@gmail.com");
//        Entry.list(r);
//        Entry.delete(r, "1");
//        Entry.list(r);
//        Entry.undo();
//        Entry.list(r);
//        Entry.update(r, 2, "Tim");
//        Entry.list(r);
//        Entry.delete(r, 4);

        r.loadFromFile();
        invoker.setCommandsForExecution(Entry.cmdArr);
        invoker.executeCommand(Entry.history);
//        System.out.println(Entry.history);

        r.storeToFile();

    }

}