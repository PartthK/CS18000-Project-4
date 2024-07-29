import java.util.ArrayList;
import java.io.*;

/**
 * Project 04 -- Store
 * <p>
 * Creates a store object that includes a store name and an array list of items
 * a store can have multiple items
 *
 * @author Group 2- L23
 * @version November 13, 2023
 */
public class Store {

    //fields
    private ArrayList<Item> itemList;
    private String storeName;
    private String sellerEmail;
    private double productPrice;
    private double totalRevenue; //the total revenue a store has acquired
    private ArrayList<String[]> itemsSoldHistory; //all the items sold from one store

    //Constructor
    public Store(ArrayList<Item> itemList) {
        this.itemList = itemList;
        this.storeName = getItemList().get(0).getStoreName();
        this.totalRevenue = 0.0;
    }

    public Store(String storeName, double productPrice) {
        this.storeName = storeName;
        this.productPrice = productPrice;
    }

    //getters
    public void storeRevenue() throws IOException {
        sellerEmail = Seller.getEmailFromStoreName(storeName);
        FileReader fr = new FileReader("storeRevenue.txt");
        BufferedReader bfr = new BufferedReader(fr);


        String line = bfr.readLine();

        ArrayList<String[]> stores = new ArrayList<>();


        while (line != null) {
            String[] data = line.split(",", -1);
            stores.add(data);
            line = bfr.readLine();
        }

        bfr.close();

        FileOutputStream fos = new FileOutputStream("storeRevenue.txt");
        PrintWriter pw = new PrintWriter(fos);

        /*if (newFile) {
            pw.println(storeName + "," + sellerEmail + "," + productPrice);
        }*/

        boolean found = false;

        for (int i = 0; i < stores.size(); i++) {
            String[] data = stores.get(i);
            if (data[0].equals(storeName)) {
                found = true;
                double finalPrice = Double.parseDouble(data[2]) + productPrice;
                String str = finalPrice + "";
                String[] updateData = new String[3];
                updateData[0] = data[0];
                updateData[1] = data[1];
                updateData[2] = str;
                stores.set(i, updateData);
            }
        }

        if (!found) {
            String[] data = new String[3];
            data[0] = storeName;
            data[1] = sellerEmail;
            data[2] = productPrice + "";
            stores.add(data);
        }

        for (int i = 0; i < stores.size(); i++) {
            String[] data = stores.get(i);
            pw.println(data[0] + "," + data[1] + "," + data[2]);
        }

        pw.flush();
        pw.close();
    }


    public ArrayList<Item> getItemList() {
        return itemList;
    }

    //setters

    public void setItem(Item item, int index) {
        this.itemList.set(index, item);
    }

    //other methods

    public void addItem(Item product) {
        //adds items
        itemList.add(product);
    }

    public void deleteItem(Item product) {
        //delete items
        itemList.remove(product);
    }

    public String itemsListToString() {
        String itemsListToString = "";
        ArrayList<Item> itemsInStore = getItemList();
        for (int i = 0; i < itemsInStore.size(); i++) {
            itemsListToString += itemsInStore.get(i).displayToString();
        }
        return itemsListToString;
    }

    public String getStoreName() {
        return storeName;
    }

    /*@Override
    public String toString() {
        String itemsListToString = "";
        ArrayList<Item> itemsInStore = getItemList();
        for (int i = 0; i < itemsInStore.size(); i++) {
            itemsListToString += itemsInStore.get(i).displayToString();
        }
        return itemsListToString;
    }*/
}
