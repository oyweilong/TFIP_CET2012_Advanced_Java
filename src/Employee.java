import commands.AddCommand;
import commands.Command;
import data.Receiver;

public class Employee {
    private String firstname;
    private String lastname;
    private String email;
    private static int cmdCount = 0;

    public Employee(String firstname, String lastname, String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public void add(Command[] cmd_arr, Receiver r){
        AddCommand add = new AddCommand(r, firstname, lastname, email );
        cmd_arr[++cmdCount-1] = add;
    }
}
