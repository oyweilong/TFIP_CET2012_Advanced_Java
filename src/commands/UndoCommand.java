package commands;

import data.Receiver;

import java.util.EmptyStackException;

public class UndoCommand implements Command {
    private final Receiver receiver;

    public UndoCommand(){
        receiver = new Receiver();
    }

    @Override
    public boolean execute(){
        try{
            receiver.undo();
        } catch (EmptyStackException e){
            System.out.println("No commands to undo");
        }
        return false;
    }
}
