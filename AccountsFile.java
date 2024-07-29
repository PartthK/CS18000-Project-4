import java.io.*;
import java.util.*;

/**
 * Project 04 -- AccountsFile.java
 * <p>
 * This class deals with file writing in relation to accounts
 * it has methods that write, read, and edit a file that has the account information for each user
 * 
 * 
 * @author Group 2 - L23
 * @version November 13, 2023
 */
public class AccountsFile {
    
    //read accounts method
    public static ArrayList<String[]> readAccounts() throws IOException {
        //reads all of the logins from the login csv file (not user inputed, the file is defined here), 
        // //in the String Array of size 3
        // in the order email,password,role
        File input = new File("accounts.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String[]> accounts = new ArrayList<>();

        String line = bfr.readLine();
        while (line != null) {
            String[] data = line.split(",", -1);
            accounts.add(data);
            line = bfr.readLine();
        }

        bfr.close();
        return accounts;
    }

    //write accounts method
    public static void writeAccounts(ArrayList<String[]> loginList) throws IOException {
        //writes all logins to the file
        FileOutputStream fos = new FileOutputStream("accounts.txt", false);
        PrintWriter pw = new PrintWriter(fos);

        ArrayList<String[]> accounts = loginList;
        for (int i = 0; i < accounts.size(); i++) {
            String[] data = accounts.get(i);
            pw.println(data[0] + "," + data[1] + "," + data[2]);
        }

        pw.flush();
        pw.close();
    }

    //add to account method
    public static void addAccount(String email, String password, String role) throws IOException {
        //appends a login to the end of the login file
        FileOutputStream fos = new FileOutputStream("accounts.txt", true);
        PrintWriter pw = new PrintWriter(fos);
        pw.println(email + "," + password + "," + role);
        pw.close();
    }
}
