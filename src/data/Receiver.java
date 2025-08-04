package data;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Arrays;
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

    private void setListHeader(){
        if (tempDatastore.isEmpty()){
            String[] header = {"First_Name Last_Name Email"};
            tempDatastore.add(header);
        }
    }

    public boolean addEntry(String[] payload){
        System.out.println("add");
        this.setListHeader();
        tempDatastore.add(payload);
        return true;

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
        return true;
        }



//    }

    public boolean deleteEntry(int index){
        if (tempDatastore.isEmpty()){
            System.out.println("No entries to delete");
            return false;
        }
        try {
            tempDatastore.remove(index);
            return true;
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Index out of bounds");
            return false;
        }
    }

    public void list(){
        if (tempDatastore.isEmpty()){
            System.out.println("No entries to list");
            return;
        }

        System.out.println("List");
        for (String[] entry : tempDatastore) {
            System.out.printf("%d. ", tempDatastore.indexOf(entry));
            for (String s : entry) {
                System.out.printf("%s ", s);
            }
            System.out.println();
        }
    }

    public void loadFromFile(){
        Path path = Paths.get("src/dataStore.txt");
        if (!Files.exists(path)) return;

    }


}
