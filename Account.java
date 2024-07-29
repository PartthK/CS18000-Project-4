import java.io.*;
import java.util.ArrayList;

/**
 * Project 04 -- Account.java
 * <p>
 * Creates an account (for either customer or seller)
 * has getters, setters, and a variety of methods for the account
 *
 * @author Group 2 - L32
 * @version November 13, 2023
 */

public class Account {
    //fields
    private String email;
    private String password;
    private String role;

    //Constructor
    public Account(String email, String password, String role) throws IOException {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    //setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    //getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    //login method
    public static Account login(String email, String password) throws IOException {
        //Returns true if the login was successful or false if it wasn't
        ArrayList<String[]> accounts = AccountsFile.readAccounts();

        for (String[] account : accounts) {
            if (account[0].equals(email)) {
                if (account[1].equals(password)) {
                    System.out.println("Login Successful");
                    return new Account(account[0], account[1], account[2]);
                }
                System.out.println("Incorrect Password");
                return null;
            }
        }
        System.out.println("Email Doesn't Exist");
        return null;
    }

    //create account method
    public boolean createAccount() throws IOException {
        //Returns true if the account creation was successful or false if it wasn't
        ArrayList<String[]> accounts = AccountsFile.readAccounts();

        for (String[] account : accounts) {
            if (account[0].equals(email)) {
                System.out.println("Email Already Exists");
                return false;
            }
        }
        AccountsFile.addAccount(email, password, role);
        System.out.println("Account Made");
        return true;
    }

    //edit account method
    public boolean editAccount(String newEmail, String newPassword, String newRole) throws IOException {
        //Returns true if the edit was successful or false if it wasn't
        ArrayList<String[]> accounts = AccountsFile.readAccounts();

        if (getEmail().equals(newEmail) && getPassword().equals(newPassword) && getRole().equals(newRole)) {
            System.out.println("Edits Requested Are The Same");
            return false;
        }

        String oEmail = getEmail();

        setEmail(newEmail);
        setPassword(newPassword);
        setRole(newRole);

        for (String[] account : accounts) {
            if (account[0].equals(oEmail)) {
                account[0] = getEmail();
                account[1] = getPassword();
                account[2] = getRole();
                AccountsFile.writeAccounts(accounts);
                return true;
            }
        }
        return false;
    }

    //remove account method
    public boolean removeAccount() throws IOException {
        //Returns true if the removal was successful or false if it wasn't
        ArrayList<String[]> accounts = AccountsFile.readAccounts();

        int count = 0;
        for (String[] account : accounts) {
            if (account[0].equals(getEmail())) {
                if (getRole().equals("seller")) {
//                    File input = new File("allStores.txt");
//                    FileReader fr = new FileReader(input);
//                    BufferedReader bfr = new BufferedReader(fr);
//
//                    FileOutputStream fos = new FileOutputStream("allStores.txt", false);
//                    PrintWriter pw = new PrintWriter(fos);
//
//                    String line = bfr.readLine();
//
//                    while (line != null) {
//                        String[] data = line.split("-", -1);
//                        if (!(data[0].equals(getEmail())))
//                            line = bfr.readLine();
//                    }
//                    bfr.close();
//                    pw.close();
                }
                accounts.remove(count);
                AccountsFile.writeAccounts(accounts);
                return true;
            }
            count++;
        }
        return false;
    }

    //check for invalid characters method
    public static boolean checkForInvalidCharacters(String toCheck) {
        if (toCheck.contains(",") || toCheck.contains(";") || toCheck.contains("-")) {
            System.out.println("Error: Please do not include commas, semicolons, or dashes in your input.");
            return false;
        } else {
            return true;
        }
    }

}
