package data;

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
    public ArrayList<List<String>> tempDatastore = new ArrayList<>();

    private void setListHeader(){
        if (tempDatastore.isEmpty()){
            List<String> header = new ArrayList<>();
            header.add("First_Name Last_Name Email");
            tempDatastore.add(header);
        }
    }

    public boolean addEntry(String payload){
        System.out.println("add");
        List<String> entry = new ArrayList<>();
        entry.add(payload);

        this.setListHeader();
        tempDatastore.add(entry);
        return true;

    }
    public boolean updateEntry(int index, String[] payloadArr){
        if (tempDatastore.isEmpty()){
            System.out.println("No entries to update");
            return false;
        }

        return false;
    }

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
        for (List<String> entry : tempDatastore) {
            System.out.printf("%d. ", tempDatastore.indexOf(entry));
            System.out.printf("%s\n", entry.getFirst());
        }
    }


}
