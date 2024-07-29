import java.io.*;
import java.util.ArrayList;

/**
 * Project 04 -- Seller
 * <p>
 * Creates a seller object
 * this allows the seller to do required actions
 * (i.e. getters, setters, reading and writing important information to files)
 *
 * @author Group 2- L23
 * @version November 13, 2023
 */
public class Seller {

    // Fields
    private String email;

    //private double totalRevenue;
    private ArrayList<Store> stores;

    //seller has acesss to different stores; be allowed to edit this
    // Constructor
    public Seller(String email) {
        this.email = email;
        stores = new ArrayList<>();
    }

    // Getter/setters for username
    public String getEmail() {
        return email;
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    //allows to read Stores
    public void readStores(String filename) throws IOException {
        File f = new File(filename);
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        ArrayList<Store> storesList = new ArrayList<>();
        while (line != null) {
            String[] data = line.split("-");
            if (email.equals(data[0])) {
                for (int i = 1; i < data.length; i++) {
                    String[] itemData = data[i].split(";");
                    ArrayList<Item> items = new ArrayList<>();
                    for (int j = 0; j < itemData.length; j++) {
                        String[] eachItem = itemData[j].split(",");
                        Item item = new Item(eachItem[0], eachItem[1], eachItem[2],
                                Integer.parseInt(eachItem[3]), Double.parseDouble(eachItem[4]));
                        items.add(item);
                    }
                    Store store = new Store(items);
                    storesList.add(store);
                }
            }
            line = bfr.readLine();
        }
        bfr.close();
        stores = storesList;
    }

    public ArrayList<String> importAllProducts() throws IOException {
        File input = new File("storeList.csv");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String> allProducts = new ArrayList<>();

        String line = bfr.readLine();

        while (line != null) {
            //StoreName,ProductName,Price
            String[] data = line.split(",", -1);
            String[] itemData = data[2].split(";", -1);
            System.out.print(data[1] + ":");
            for (int i = 0; i < itemData.length; i++) {
                if (i % 5 == 1) allProducts.add(itemData[i] + "," + itemData[i + 3]);
            }
            line = bfr.readLine();
        }

        bfr.close();
        return allProducts;
    }

    public ArrayList<String[]> allProductKeyword() throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();

        ArrayList<String[]> items = new ArrayList<>();

        while (line != null) {
            //StoreName,ProductName,Price
            String[] data = line.split("-", -1);
            //String[] itemData = data[2].split(";",-1);
            for (int i = 1; i < data.length - 1; i++) {
                String[] itemData = data[i].split(";", -1);
                String[] itemFinal = new String[itemData.length - 1];
                for (int j = 0; j < itemData.length; j++) {
                    itemFinal[i] = itemData[i];
                }
                items.add(itemFinal);
            }
            line = bfr.readLine();
        }
        bfr.close();
        return items;
    }

    //String to return format of each product
    public String productsString() throws FileNotFoundException {
        String products = "";

        products += this.email + "-";
        for (Store store : stores) {
            for (Item item : store.getItemList()) {
                products += item.getStoreName() + "," + item.getItemName() +
                        "," + item.getItemDescription()
                        + "," + item.getAvailable()
                        + "," + item.getPrice() + ";";
            }
            products += "-";
        }
        return products;
    }

    //start of what seller class actually needs to do
    //creating a store
    public void createStore(Store storeNew) throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        String stringStore = "";

        String line = bfr.readLine();
        if (line == null) {
            stores.add(storeNew);
            stringStore += productsString() + "\n";
        }
        while (line != null) {
            String[] data = line.split("-", -1);

            if (data[0].equals(email)) {
                boolean flag = true;
                for (Store store : stores) {
                    if (store.getStoreName().equals(storeNew.getStoreName())) {
                        System.out.println("Store already exists");
                        stringStore += line + "\n";
                        flag = false;
                    }
                }
                if (flag) {
                    System.out.println("Store created!");
                    stores.add(storeNew);
                    stringStore += productsString() + "\n";
                }
            } else
                stringStore += line + "\n";
            line = bfr.readLine();
        }
        if (stores.isEmpty()) {
            System.out.println("Store created!");
            stores.add(storeNew);
            stringStore += productsString() + "\n";
        }
        bfr.close();

        FileOutputStream fos = new FileOutputStream("allStores.txt", false);
        PrintWriter pw = new PrintWriter(fos);

        stringStore = stringStore.substring(0, stringStore.length() - 1);
        pw.print(stringStore);
        pw.flush();
        pw.close();
    }

    //deleting a store
    public void deleteStore(String storeName) throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();

        String stringStore = "";

        while (line != null) {
            String[] data = line.split("-", -1);

            if (data[0].equals(email)) {
                boolean found = false;
                Store toBeDeleted = null;
                for (Store store : stores) {
                    if (store.getStoreName().equals(storeName)) {
                        toBeDeleted = store;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    System.out.println("Store deleted!");
                    stores.remove(toBeDeleted);
                    stringStore += productsString() + "\n";
                } else {
                    System.out.println("Store is not there, cannot be deleted");
                    stringStore += line + "\n";
                }
            } else
                stringStore += line + "\n";
            line = bfr.readLine();
        }
        bfr.close();

        FileOutputStream fos = new FileOutputStream("allStores.txt", false);
        PrintWriter pw = new PrintWriter(fos);

        stringStore = stringStore.substring(0, stringStore.length() - 1);
        pw.print(stringStore);
        pw.flush();
        pw.close();
    }

    // Method to add a product from a specific store
    public boolean addProductInTheStore(String storeName, Item item) throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        boolean flag = true;

        String line = bfr.readLine();

        String stringStore = "";

        while (line != null) {
            String[] data = line.split("-", -1);

            if (data[0].equals(email)) {
                boolean found1 = false;
                boolean found2 = true;
                for (Store store : stores) {
                    if (store.getStoreName().equals(storeName)) {
                        found1 = true;
                        for (Item product : store.getItemList()) {
                            if (product.getItemName().equals(item.getItemName())) {
                                found2 = false;
                            }
                        }
                        if (found2) {
                            store.addItem(item);
                            stringStore += productsString() + "\n";
                        }
                    }
                }
                if (!found1) {
                    System.out.println("Store is not found, so can't add product");
                    flag = false;
                    stringStore += line + "\n";
                } else if (!found2) {
                    System.out.println("Product already exists, so can't add product");
                    flag = false;
                    stringStore += line + "\n";
                }
            } else
                stringStore += line + "\n";
            line = bfr.readLine();
        }
        bfr.close();

        FileOutputStream fos = new FileOutputStream("allStores.txt", false);
        PrintWriter pw = new PrintWriter(fos);

        stringStore = stringStore.substring(0, stringStore.length() - 2);
        pw.print(stringStore);
        pw.flush();
        pw.close();

        return flag;
    }


    // Method to delete a product from a specific store
    public boolean deleteProductInTheStore(String storeName, String productName) throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        boolean flag = true;

        String line = bfr.readLine();

        String stringStore = "";

        while (line != null) {
            String[] data = line.split("-", -1);

            if (data[0].equals(email)) {
                boolean found1 = false;
                boolean found2 = false;
                for (Store store : stores) {
                    if (store.getStoreName().equals(storeName)) {
                        found1 = true;
                        for (Item product : store.getItemList()) {
                            if (product.getItemName().equals(productName)) {
                                store.deleteItem(product);
                                stringStore += productsString() + "\n";
                                found2 = true;
                                break;
                            }
                        }
                    }
                }
                if (!found1) {
                    System.out.println("Store is not found, so can't delete product");
                    flag = false;
                    stringStore += line + "\n";
                } else if (!found2) {
                    System.out.println("Product is not found, so can't delete product");
                    flag = false;
                    stringStore += line + "\n";
                }
            } else
                stringStore += line + "\n";
            line = bfr.readLine();
        }
        bfr.close();

        FileOutputStream fos = new FileOutputStream("allStores.txt", false);
        PrintWriter pw = new PrintWriter(fos);

        stringStore = stringStore.substring(0, stringStore.length() - 1);
        pw.print(stringStore);
        pw.flush();
        pw.close();

        return flag;
    }

    // Method to edit a product from a specific store
    public void editProductInTheStore(String storeName, String productName, Item item) throws IOException {

        if (deleteProductInTheStore(storeName, productName) &&
                addProductInTheStore(storeName, item)) {
            System.out.println("Item edited successfully!");
        }
        //in main --> prints out the item and its description, name, etc so they can check and then do it
    }

    //get the seller email depending on the store name
    //this is used in another method
    public static String getEmailFromStoreName(String storeName) throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();

        while (line != null) {
            String[] data = line.split("-", -1);
            for (int i = 1; i < data.length; i++) {
                if (data[i].split(",")[0].equals(storeName)) {
                    bfr.close();
                    return data[0];
                }
            }
            line = bfr.readLine();
        }
        bfr.close();
        return null;
    }

    //get all of the products from the allStores.txt
    public static ArrayList<Item> getAllProducts() throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader("allStores.txt"));

        String line = bfr.readLine();
        ArrayList<Item> itemsList = new ArrayList<>();

        while (line != null) {
            String[] data = line.split("-", -1);
            for (int i = 1; i < data.length; i++) {
                String[] items = data[i].split(";", -1);
                for (String item : items) {
                    if (!item.isEmpty()) {
                        String[] itemD = item.split(",", -1);
                        if (!(Integer.parseInt(itemD[3]) <= 0)) {
                            itemsList.add(new Item(itemD[0], itemD[1], itemD[2],
                                    Integer.parseInt(itemD[3]), Double.parseDouble(itemD[4])));
                        }
                    }
                }
            }
            line = bfr.readLine();
        }
        bfr.close();
        return itemsList;
    }

    //sellers can view a list of their sales by store, including customer information and revenues from the sale
    public void viewListOfSalesByStore() throws IOException {
        FileReader fr = new FileReader("storeRevenue.txt");
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        while (line != null) {
            String[] sales = line.split(",");
            if (sales[1].equals(email)) {
                System.out.printf(sales[0] + " Sales: $%.2f\n", Double.parseDouble(sales[2]));
            }
            line = bfr.readLine();
        }
        bfr.close();
    }

    //see the total revenue that each seller is making --> from the storeRevenue file
    public void viewTotalRevenue() throws IOException {
        FileReader fr = new FileReader("storeRevenue.txt");
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();
        double tot = 0;
        while (line != null) {
            String[] sales = line.split(",");
            if (sales[1].equals(email)) {
                tot += Double.parseDouble(sales[2]);
            }
            line = bfr.readLine();
        }
        System.out.printf("Total Revenue: $%.2f\n", tot);
        bfr.close();
    }


    public void sellerCustomerHistory() throws IOException {
        FileReader fr = new FileReader("allCustomerPurchases.txt");
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();
        while (line != null) {
            String[] cust = line.split(";");
            for (Store store : stores) {
                if (store.getStoreName().equals(cust[1])) {
                    System.out.println(cust[0] + cust[1]);
                    break;
                }
            }
            line = bfr.readLine();
        }

        bfr.close();
    }

    //the following 2 methods are related to file io
    //these two methods import/export the item
    public static ArrayList<Item> importItem(String filename) throws IOException {
        File input = new File(filename);
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();

        ArrayList<Item> items = new ArrayList<>();

        if (line == null) {
            bfr.close();
            return null;
        }
        while (line != null) {
            String[] data = line.split(",", -1);
            Item item = new Item(data[0], data[1], data[2], Integer.parseInt(data[3]), Double.parseDouble(data[4]));
            items.add(item);
            line = bfr.readLine();
        }

        bfr.close();
        return items;
    }

    public void exportItem() throws IOException {
        File input = new File("allStores.txt");
        FileReader fr = new FileReader(input);
        BufferedReader bfr = new BufferedReader(fr);

        FileOutputStream fos = new FileOutputStream(email + "-products.csv", false);
        PrintWriter pw = new PrintWriter(fos);

        String line = bfr.readLine();

        while (line != null) {
            String[] data = line.split("-", -1);

            if (data[0].equals(email)) {
                for (int i = 1; i < data.length - 1; i++) {
                    String[] itemData = data[i].split(";", -1);
                    for (int j = 0; j < itemData.length - 1; j++) {
                        pw.println(itemData[0]);
                    }
                }
            }
            line = bfr.readLine();
        }

        bfr.close();
        pw.flush();
        pw.close();
    }

    //allows the product to be edited in the store
    public void editProductInTheStoreCustomer(String storeName, String productName, Item item) throws IOException {
        deleteProductInTheStore(storeName, productName);
        addProductInTheStore(storeName, item);
    }

    //the following two methods are related to the statistics dashboard for the seller
    //Sellers can see how many sales each of their specific products has made 
    public void salesOfSpecificProduct() throws IOException {
        FileReader fr = new FileReader("allCustomerPurchases.txt");
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String[]> dataLine = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] datas = line.split(";", -1);
            dataLine.add(datas);
            line = bfr.readLine();
        }

        bfr.close();

        for (int i = 0; i < stores.size(); i++) {
            Store store = stores.get(i);
            ArrayList<Item> items = store.getItemList();
            String storeName = items.get(0).getStoreName();
            for (int j = 0; j < items.size(); j++) {
                String itemName = items.get(j).getItemName();
                int counter = 0;
                for (int k = 0; k < dataLine.size(); k++) {
                    String[] data = dataLine.get(k);
                    if (data[1].equals(storeName) && data[2].equals(itemName)) counter++;
                }
                System.out.println("Item " + itemName + " from store " + storeName + " sold " + counter + " quantity");
            }
        }
    }

    //sellers can see how many products each customer has purchased from their stores
    public void salesOfSpecificCustomer() throws IOException {
        FileReader fr = new FileReader("allCustomerPurchases.txt");
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String[]> dataLine = new ArrayList<>();
        String line = bfr.readLine();
        while (line != null) {
            String[] datas = line.split(";", -1);
            dataLine.add(datas);
            line = bfr.readLine();
        }

        bfr.close();

        ArrayList<String> customerNames = new ArrayList<>();
        for (int i = 0; i < dataLine.size(); i++) {
            if (!customerNames.contains(dataLine.get(i)[0])) {
                customerNames.add(dataLine.get(i)[0]);
            }
        }

        for (int l = 0; l < customerNames.size(); l++) {
            String custName = customerNames.get(l);
            for (int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                ArrayList<Item> items = store.getItemList();
                String storeName = items.get(0).getStoreName();
                for (int j = 0; j < items.size(); j++) {
                    String itemName = items.get(j).getItemName();
                    int counter = 0;
                    for (int k = 0; k < dataLine.size(); k++) {
                        String[] data = dataLine.get(k);
                        if (data[0].equals(custName) && data[1].equals(storeName) && data[2].equals(itemName))
                            counter++;
                    }
                    if (counter != 0) {
                        System.out.print(custName + " has purchased " + counter + " " + itemName);
                        if (counter > 1) System.out.print("s");
                        System.out.println(" from " + storeName);
                    }
                }
            }
        }

    }

    //one product is 10
    public void getCustomerShoppingcartData() throws IOException {
        FileReader fr = new FileReader("shoppingcartdata.txt");
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String[]> products = new ArrayList<String[]>();

        String line = bfr.readLine();
        while (line != null) {
            String[] data = line.split("Store;", -1);
            for (int i = 1; i < data.length; i++) {
                products.add(data[i].split(";", -1));
            }
            line = bfr.readLine();
        }

        ArrayList<String[]> productsNew = new ArrayList<String[]>();
        for (String[] p : products) {
            for (Store store : stores) {
                if (store.getStoreName().equals(p[0])) {
                    productsNew.add(p);
                }
            }
        }

        String firstName = "";
        String storeName = "";
        int tot = 0;
        int i = 0;
        while (!productsNew.isEmpty()) {
            firstName = productsNew.get(0)[2];
            storeName = productsNew.get(i)[2];
            if (firstName.equals(storeName)) {
                tot++;
                if (i != 0) {
                    productsNew.remove(i);
                } else i++;
            } else
                i++;

            if (i == productsNew.size()) {
                System.out.println("There are " + tot + " " + productsNew.get(0)[2] +
                        " (" + productsNew.get(0)[4] + ") from "
                        + productsNew.get(0)[0] + " in customer shopping carts.");
                i = 0;
                tot = 0;
                productsNew.remove(0);
            }
        }
    }

    //removes the seller --> logout related
    public void removeSeller(String sellerEmail) throws IOException {
        FileReader fr = new FileReader("allStores.txt");
        BufferedReader bfr = new BufferedReader(fr);

        ArrayList<String> sellerData = new ArrayList<>();

        String line = bfr.readLine();
        while (line != null) {
            sellerData.add(line);
            line = bfr.readLine();
        }

        bfr.close();

        File file = new File("allStores.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(fos);

        for (int i = 0; i < sellerData.size(); i++) {
            String[] data = sellerData.get(i).split("-");
            if (!data[0].equals(sellerEmail)) pw.println(sellerData.get(i));
        }

        pw.flush();
        pw.close();
    }

}
