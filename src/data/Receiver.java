package data;

import java.util.ArrayList;
import java.util.List;

//TODO add FileIO to write tempDatastore to a dataStore.txt file
//TODO add FileIO to read in existing dataStore.txt (load saved file)

//TODO add regex validation for email string to methods
//TODO add exceptions to be thrown if email string is not input in the
// correct format

public class Receiver {
    ArrayList<List<String>> tempDatastore = new ArrayList<>();


    public void addEmployee(String firstname, String lastname, String email){
        System.out.println("add");
        System.out.println(firstname + " " + lastname + " " + email);
        List<String> entry = new ArrayList<>();
        entry.add(firstname);
        entry.add(lastname);
        entry.add(email);
        if (tempDatastore.isEmpty()){
            List<String> header = new ArrayList<>();
            header.add("First_Name Last_Name Email");
            tempDatastore.add(header);
        }
        tempDatastore.add(entry);
        System.out.println(tempDatastore);
    }


}
