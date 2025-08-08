package data;

import exceptions.CustomException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files; // ver. 1.7
import java.nio.file.Paths;
import java.util.ArrayList; // ver. 1.2, updated 21
import java.util.Arrays;
import java.util.List;

/**
 * Receiver class to perform operations when received commands
 * tempDatastore to temporarily store array list of data items
 * FIlE_PATH to store Relative Path for storing and loading dataStore text file
 */
public class Receiver {
    public ArrayList<String[]> tempDatastore = new ArrayList<>();
    public static String FILE_PATH = "src/dataStore.txt";

    public Receiver(){
        loadFromFile();
    }

    /**
     *  Default method to Add New Entry, also used for .undo() in
     *  DeleteCommand() & UpdateCommand()
     * @param index for referencing to which specific data items to undo
     * @param payload input containing data items
     */
    public void addEntry(int index, String[] payload){
        tempDatastore.add(index, payload);
    }

    /**
     * Method to Update Entry, undoable
     * @param index for referencing to which specific line of data items to undo
     * @param payloadArr new array of data items to update existing data items
     * @param originalPayload array of existing data items
     */
    public void updateEntry(int index, String[] payloadArr,
                               String[] originalPayload)
        {
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
        }

    /**
     * Method to delete Entry, is undoable
     * @param index for referencing to which specific line of data items to delete
     */
    public void deleteEntry(int index){
        tempDatastore.remove(index);
    }

    /**
     * Method to list entries of data items
     */
    public void list(){
        System.out.println("List");
        for (String[] entry : tempDatastore) {
            // Format index to have at least 2 digits, padding with leading zeros.
            // Using %0Nd, where N is minimum number of digits, i.e. N =2
            System.out.printf("%02d. ", tempDatastore.indexOf(entry) + 1);
            for (String s : entry) {
                System.out.printf("%s ", s);
            }
            System.out.println();
        }
    }
    /**
     * Method to store data items to file
     * Throws CustomExceptions for:
     * 1. Invalid entries
     * 2. Cannot write file
     */
    public void storeToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String[] entry : tempDatastore) {
                if (entry.length == 3) {
                    // entry[0] = First Name, entry [1] = Last Name, entry [2] = email
                    writer.println(entry[0] + " " + entry[1] + " " + entry[2]);
                } else {
                    throw new CustomException("Skipping malformed entry: " +  Arrays.toString(entry));
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }catch (CustomException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     * Loads data from file on startup
     * Throws CustomExceptions for:
     * 1. Invalid entries
     * 2. Cannot load file
     */
    public void loadFromFile(){
        try {
            if (!Files.exists(Paths.get(FILE_PATH))) {
                return;
            }
            tempDatastore.clear(); // clear existing payload memory
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                // Split by space into 3 parts
                String[] entry = line.split(" ", 3);
                if (entry.length == 3) {
                    tempDatastore.add(entry);
                } else {
                    throw new CustomException("Skipping malformed entry: " + Arrays.toString(entry));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (CustomException e){
            System.out.println(e.getMessage());
        }
    }
}
