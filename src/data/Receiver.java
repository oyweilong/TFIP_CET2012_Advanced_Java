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
    ArrayList<List<String>> tempDatastore = new ArrayList<>();

    private void setListHeader(){
        if (tempDatastore.isEmpty()){
            List<String> header = new ArrayList<>();
            header.add("First_Name");
            header.add("Last_Name");
            header.add("Email");
            tempDatastore.add(header);
        }
    }

    public boolean addEntry(String firstname, String lastname, String email){
        System.out.println("add");
        List<String> entry = new ArrayList<>();
        entry.add(firstname);
        entry.add(lastname);
        entry.add(email);

        this.setListHeader();
        tempDatastore.add(entry);
        return true;

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
            System.out.printf("%1s %2s %3s\n",
                    entry.get(0), entry.get(1), entry.get(2));
        }
    }


}
