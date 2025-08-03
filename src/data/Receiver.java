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


    public void addEntry(String firstname, String lastname, String email){
        System.out.println("add");
//        System.out.println(firstname + " " + lastname + " " + email);
        List<String> entry = new ArrayList<>();
        entry.add(firstname);
        entry.add(lastname);
        entry.add(email);
        if (tempDatastore.isEmpty()){
            List<String> header = new ArrayList<>();
            header.add("First_Name");
            header.add("Last_Name");
            header.add("Email");
            tempDatastore.add(header);
        }
        tempDatastore.add(entry);
//        System.out.println(tempDatastore);
    }

    public void deleteEntry(int index){
        try{tempDatastore.remove(index);}
        catch (IndexOutOfBoundsException e){
            System.out.println("Index out of bounds");
        }
    }

    public void list(){
        System.out.println("List");
        for (List<String> entry : tempDatastore){
            System.out.printf("%d. ",tempDatastore.indexOf(entry));
            System.out.printf("%1s %2s %3s\n",
                    entry.get(0), entry.get(1), entry.get(2));
        }

    }


}
