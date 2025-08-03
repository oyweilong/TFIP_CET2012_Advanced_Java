import commands.AddCommand;
import commands.Command;
import commands.ListCommand;
import data.Receiver;

public class Entry {
    private String firstname;
    private String lastname;
    private String email;
    static Command[] cmd_arr= new Command[1];
    private static int cmdCount = 0;

    public Entry(String firstname, String lastname, String email){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public void add(Receiver r){
        AddCommand add = new AddCommand(r, firstname, lastname, email );
        try{
            cmd_arr[cmdCount] = add;
            cmdCount++;}
        catch (ArrayIndexOutOfBoundsException e){
            Command[] new_cmd_arr = new Command[cmd_arr.length+1];
            System.arraycopy(cmd_arr, 0, new_cmd_arr, 0, cmd_arr.length);
            cmd_arr = new_cmd_arr;
            cmd_arr[cmdCount] = add;
            cmdCount++;
        }
    }

    public static void list(Receiver r){
        ListCommand list = new ListCommand(r);
        try{
            cmd_arr[cmdCount] = list;
            cmdCount++;}
        catch (ArrayIndexOutOfBoundsException e) {
            Command[] new_cmd_arr = new Command[cmd_arr.length+1];
            System.arraycopy(cmd_arr, 0, new_cmd_arr, 0, cmd_arr.length);
            cmd_arr = new_cmd_arr;
            cmd_arr[cmdCount] = list;
            cmdCount++;
        }
    }


}

