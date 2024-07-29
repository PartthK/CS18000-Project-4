import java.io.*;
import java.util.ArrayList;

/**
 * Project 04 -- Customer
 * <p>
 * Creates a customer that can take required actions (ie.purchase item and get purchase history)
 *
 * @author Group 2- L23
 * @version November 13, 2023
 */

public class Customer {

    //fields
    private String email;
    private ArrayList<Item> purchaseHistory;


    //Constructor
    public Customer(String email) {
        this.email = email;
    }

    // Getter/setters for logging in
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    //allows the customer to receive their purchase history when they ask for it
    public void getPurchaseHistory(String customerEmail) throws IOException {
        File input = new File("allCustomerPurchases.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        FileOutputStream fos = new FileOutputStream(customerEmail + "-purchase-history.csv");

        PrintWriter pw = new PrintWriter(fos);
        ArrayList<Item> items = new ArrayList<>();

        String line = bfr.readLine();

        while (line != null) {
            String[] data = line.split(";", -1);
            if (data[0].equals(customerEmail)) {
                Item item = new Item(data[1], data[2], data[3], Integer.parseInt(data[4]), Double.parseDouble(data[5]));
                items.add(item);
            }
            line = bfr.readLine();
        }

        bfr.close();

        for (Item item : items) {
            pw.println(item.displayToString());
        }
        pw.flush();
        pw.close();

    }


    //reads from a file + prints out; same thing as before but simply prints in terminal
    public static void getPurchaseHistoryStream(String customerEmail) throws IOException {
        File input = new File("allCustomerPurchases.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<Item> items = new ArrayList<>();

        String line = bfr.readLine();

        while (line != null) {
            String[] data = line.split(";", -1);
            if (data[0].equals(customerEmail)) {
                Item item = new Item(data[1], data[2], data[3], Integer.parseInt(data[4]), Double.parseDouble(data[5]));
                items.add(item);
            }
            line = bfr.readLine();
        }


        bfr.close();

        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).displayToString());
        }
    }

    //when customer purchase item --> all the things that are related to this
    //includes changing the revenue, availability, add to the file, etc
    public void purchaseItem(String storeName, Item item, String customerEmail) throws IOException {
        Store store = new Store(storeName, item.getPrice());
        store.storeRevenue();
        item.setAvailable(item.getAvailable() - 1);

        FileOutputStream fos = new FileOutputStream("allCustomerPurchases.txt", true);
        PrintWriter pw = new PrintWriter(fos);

        pw.println(customerEmail + ";" + item.semiColonfileToString());
        pw.flush();
        pw.close();

        String sellerEmail = Seller.getEmailFromStoreName(storeName);
        //System.out.println(storeName);
        Seller seller = new Seller(sellerEmail);
        seller.readStores("allStores.txt");
        seller.editProductInTheStoreCustomer(storeName, item.getItemName(), item);
    }

    //purchase history
    public void reviewPurchaseHistory() {
        System.out.println("Here is the purchase history for " + email + ":");
        for (Item purchasedItem : purchaseHistory) {
            System.out.println(purchasedItem.displayToString());
        }
    }

    //the following two methods are related to the statistics dashboard portion
    //Customers can see all stores and how many products they’ve sold
    public void seeAllStoresAndSales() throws IOException {
        File input = new File("customerPurchaseHistory.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        File input2 = new File("allStores.txt");
        FileReader fr2 = new FileReader(input2);
        BufferedReader bfr2 = new BufferedReader(fr2);

        String line = bfr2.readLine();

        ArrayList<String> store = new ArrayList<>();

        while (line != null) {
            String[] data = line.split("-", -1);
            for (int i = 1; i < data.length - 1; i++) {
                String[] items = data[i].split(";", -1);
                store.add(items[0].split(",", -1)[0]);
            }
            line = bfr2.readLine();
        }
        bfr2.close();

        ArrayList<String[]> productFromStore = new ArrayList<String[]>();

        line = bfr.readLine();

        while (line != null) {
            productFromStore.add(line.split(";"));
            line = bfr.readLine();
        }

        String firstName = "";
        String storeName = "";
        int tot = 0;
        int i = 0;
        while (!productFromStore.isEmpty()) {
            firstName = productFromStore.get(0)[2];
            storeName = productFromStore.get(i)[2];
            if (firstName.equals(storeName)) {
                tot++;
                if (i != 0) {
                    productFromStore.remove(i);
                } else i++;
            } else
                i++;

            if (i == productFromStore.size()) {
                System.out.println(firstName + " has sold " + tot + " products!");
                i = 0;
                tot = 0;
                for (int x = 0; x < store.size(); x++) {
                    if (firstName.equals(store.get(x))) {
                        store.remove(x);
                        break;
                    }
                }
                productFromStore.remove(0);
            }
        }
        bfr.close();

        for (String s : store) {
            System.out.println(s + " has sold 0 products!");
        }
    }

    //also part of the customer stats dashboard
    //Customers can see how many products they have purchased from each store
    public void howManyProductsFromEachStore() throws IOException {

        File input = new File("customerPurchaseHistory.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String[]> productFromStore = new ArrayList<String[]>();

        String line = bfr.readLine();

        while (line != null) {
            if (line.split(";")[0].equals(getEmail())) {
                productFromStore.add(line.split(";"));
            }
            line = bfr.readLine();
        }

        String firstName = "";
        String storeName = "";
        int tot = 0;
        int i = 0;
        while (!productFromStore.isEmpty()) {
            firstName = productFromStore.get(0)[2];
            storeName = productFromStore.get(i)[2];
            if (firstName.equals(storeName)) {
                tot++;
                if (i != 0) {
                    productFromStore.remove(i);
                } else i++;
            } else
                i++;

            if (i == productFromStore.size()) {
                System.out.println("You have purchased " + tot + " products from " + firstName);
                i = 0;
                tot = 0;
                productFromStore.remove(0);
            }
        }
        bfr.close();
    }
}
