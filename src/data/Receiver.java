package data;




import commands.Command;
import exceptions.CustomException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


//TODO add exceptions to be thrown if email string is not input in the
// correct format


public class Receiver {
    public ArrayList<String[]> tempDatastore = new ArrayList<>();
    public static String FILE_PATH = "src/dataStore.txt";

    public boolean addEntry(String[] payload){
        tempDatastore.add(payload);
        System.out.printf("Entry added: %d. ", tempDatastore.size()-1);
        for (String s : payload) {
            System.out.printf("%s ", s);
        }
        System.out.println();
        return true;
    }

    //addEntry method for .undo() methods in DeleteCommand and UpdateCommand
    public void addEntry(int index, String[] payload){
        tempDatastore.add(index, payload);
    }
    public boolean updateEntry(int index, String[] payloadArr,
                               String[] originalPayload) {
        if (tempDatastore.isEmpty()) {
            System.out.println("No entries to update");
            return false;
        }


        String[] updatedPayload = new String[3];
        switch(payloadArr.length){
            case 1:
                updatedPayload[0] = payloadArr[0];
                updatedPayload[1] = originalPayload[1];
                updatedPayload[2] = originalPayload[2];
                break;
            case 2:
                updatedPayload[0] = payloadArr[0];
                updatedPayload[1] = payloadArr[1];
                updatedPayload[2] = originalPayload[2];
                break;
            case 3:
                updatedPayload[0] = payloadArr[0];
                updatedPayload[1] = payloadArr[1];
                updatedPayload[2] = payloadArr[2];
                break;
        }

        tempDatastore.set(index, updatedPayload);
        System.out.printf("Entry updated: %d. ", index+1);
        for (String s : updatedPayload) {
            System.out.printf("%s ", s);
        }
        System.out.println();
        return true;
        }


    public boolean deleteEntry(int index){
        if (tempDatastore.isEmpty()){
            System.out.println("No entries to delete");
            return false;
        }
        try {
            String[] deletedPayload = tempDatastore.get(index);
            tempDatastore.remove(index);
            System.out.printf("Entry deleted: %d. ", index+1);
            for (String s : deletedPayload) {
                System.out.printf("%s ", s);
            }
            System.out.println();
            return true;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Index out of bounds");
            return false;
        }
    }

    public void list(){
        System.out.println("List");
        for (String[] entry : tempDatastore) {
            System.out.printf("%d. ", tempDatastore.indexOf(entry) + 1);
            for (String s : entry) {
                System.out.printf("%s ", s);
            }
            System.out.println();
        }
    }

    public void undo(Stack<Command> history) {
        history.pop().undo();
    }
    /**
     * Stores data to file
     */
    public void storeToFile() throws CustomException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String[] entry : tempDatastore) {
                if (entry.length == 3) {
                    // entry[0] = First Name, entry [1] = Last Name, entry [2] = email
                    writer.println(entry[0] + " " + entry[1] + " " + entry[2]);
                } else {
                    System.err.println("Skipping malformed entry: " + Arrays.toString(entry));
                }
            }
        } catch (IOException e) {
            throw new CustomException("Error writing to file: " + e.getMessage());
        }
    }
    /**
     * Loads data from file on startup
     */
    public void loadFromFile(){
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return;
            }
            tempDatastore.clear(); // to clear or not?
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                // Split by space into 3 parts
                String[] entry = line.split(" ", 3);
                if (entry.length == 3) {
                    tempDatastore.add(entry);
                } else {
                    System.err.println("Skipping malformed entry: " + Arrays.toString(entry));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
