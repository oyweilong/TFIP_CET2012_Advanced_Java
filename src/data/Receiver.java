package data;



import exceptions.CustomException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


//TODO add FileIO to write tempDatastore to a dataStore.txt file
//TODO add FileIO to read in existing dataStore.txt (load saved file)

//TODO add regex validation for email string to methods
//TODO add exceptions to be thrown if email string is not input in the
// correct format

//TODO add return values to methods to indicate if command has been performed
// successfully (before file IO stage)

public class Receiver {
    public ArrayList<String[]> tempDatastore = new ArrayList<>();
    public static String FILE_PATH = "src/dataStore.txt";

    private void setListHeader(){
        if (tempDatastore.isEmpty()){
            String[] header = {"First_Name Last_Name Email"};
            tempDatastore.add(header);
        }
    }

    public boolean addEntry(String[] payload){
        this.setListHeader();
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
        System.out.printf("Entry updated: %d. ", index);
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
            System.out.printf("Entry deleted: %d. ", index);
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
            System.out.printf("%d. ", tempDatastore.indexOf(entry));
            for (String s : entry) {
                System.out.printf("%s ", s);
            }
            System.out.println();
        }
    }

    public void undo() {
            Entry.history.pop().undo();
    }
    /**
     * Stores data to file
     */
    public void storeToFile() throws CustomException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String[] entry : tempDatastore) {
                // Join array into one CSV line
                writer.println(String.join(",", entry));
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

            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                String[] entry = line.split(",");
                if (entry.length == 3) {
                    tempDatastore.add(entry);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
