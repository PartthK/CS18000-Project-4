import java.io.*;
import java.util.ArrayList;

/**
 * Project 04 -- ShoppingCart
 * <p>
 * Class containing a Shopping Cart containing an ArrayList of Items.
 * Has methods allowing the user to add and remove items from the cart.
 * It also contains two methods for saving the cart data to a file and reading the data from a file.
 *
 * @author Group 2- Gavin Davis, L23
 * @version November 13, 2023
 */

public class ShoppingCart {

    //fields
    private ArrayList<Item> itemsInCart;

    //constructor
    public ShoppingCart(ArrayList<Item> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }


    //get items in cart returns the array list of all the items that are in the shopping cart
    public ArrayList<Item> getItemsInCart() {
        return itemsInCart;
    }

    //adds items to cart
    public void addToCart(Item itemToAdd) {
        itemsInCart.add(itemToAdd);
    }

    //removes items from a cart
    public void removeFromCart(Item itemToRemove) {
        itemsInCart.remove(itemToRemove);
    }

    //toStrings (different ways, depends on usage)
    public String displayToString() {
        String cartString = "";
        cartString += "Shopping Cart:\n";
        for (int i = 0; i < itemsInCart.size(); i++) {
            cartString += "Store: " + itemsInCart.get(i).getStoreName() + "\n";
            cartString += "Product: " + itemsInCart.get(i).getItemName() + "\n";
            cartString += "Description: " + itemsInCart.get(i).getItemDescription() + "\n";
            cartString += "Price: " + itemsInCart.get(i).getPrice() + "\n";
        }
        return cartString;
    }

    public String fileToString() {
        String cartString = "";
        cartString += "Shopping Cart";
        for (int i = 0; i < itemsInCart.size(); i++) {
            cartString += ";" + itemsInCart.get(i).fileToString();
        }
        return cartString;
    }

    /*Note about saveShoppingCart() and readShoppingCart() : PJ04 Handout didn't say whether to handle errors with try
catch statements or by throwing, so I am just throwing for now.*/
    public static void saveShoppingCart(ShoppingCart currentCart, String email) throws IOException {
        File f = new File("shoppingCartData.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);

        String line = bfr.readLine();
        if (line == null) {
            pw.println(email + ";" + currentCart.fileToString());
        }
        while (line != null) {
            if (line.substring(0, line.indexOf(";")).equals(email)) {
                pw.println(email + ";" + currentCart.fileToString());
            } else {
                pw.println(line);
            }
            line = bfr.readLine();
        }
        bfr.close();
        pw.close();
    }

    //reads the shopping cart file
    public static ShoppingCart readShoppingCart(File f, String email) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();
        ArrayList<Item> itemsInCart = new ArrayList<>();
        while (line != null) {
            if (line.substring(0, line.indexOf(";")).equals(email)) { //if this is the line with email
                line = line.substring(line.indexOf(";") + 1); //removes email
                line = line.substring(line.indexOf(";") + 1); //removes "Shopping Cart;"
                while (line.contains("Store")) { //while line has another item in it
                    line = line.substring(line.indexOf(";") + 1); //removes "Store;"
                    String currentItemStoreName = line.substring(0, line.indexOf(";")); //grabs store name
                    line = line.substring(line.indexOf(";") + 1); //removes store name
                    line = line.substring(line.indexOf(";") + 1); //removes "Product;
                    String currentItemName = line.substring(0, line.indexOf(";")); // grabs item name
                    line = line.substring(line.indexOf(";") + 1); //removes item name
                    line = line.substring(line.indexOf(";") + 1); //removes "Description;"
                    String currentItemDescription = line.substring(0, line.indexOf(";")); //grabs description
                    line = line.substring(line.indexOf(";") + 1); //removes description
                    line = line.substring(line.indexOf(";") + 1); //removes "Available;"
                    int currentItemAvailable = Integer.parseInt(line.substring(0, line.indexOf(";"))); //grabs available
                    line = line.substring(line.indexOf(";") + 1); // removes available
                    line = line.substring(line.indexOf(";") + 1); //removes "Price;"
                    if (line.contains("Store")) { // if there's another product in cart
                        //grabs price
                        double currentItemPrice = Double.parseDouble(line.substring(0, line.indexOf(";")));
                        line = line.substring(line.indexOf(";") + 1); //removes price
                        Item currentItem = new Item(currentItemStoreName,
                                currentItemName, currentItemDescription, currentItemAvailable, currentItemPrice);
                        //creates item object
                        itemsInCart.add(currentItem); //adds item to the shopping cart
                    } else {
                        double currentItemPrice = Double.parseDouble(line); //grabs price
                        Item currentItem = new Item(currentItemStoreName,
                                currentItemName, currentItemDescription, currentItemAvailable, currentItemPrice);
                        //creates item object
                        itemsInCart.add(currentItem); //adds item to the shopping cart
                    }
                }
            }
            line = bfr.readLine();
        }
        bfr.close();
        return new ShoppingCart(itemsInCart);
    }

    //deletes the information that is in a file related to a shooping cart
    public static void deleteShoppingCartData(String email) throws IOException {
        File f = new File("shoppingCartData.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);
        FileOutputStream fos = new FileOutputStream(f, false);
        PrintWriter pw = new PrintWriter(fos);

        String line = bfr.readLine();
        while (line != null) {
            if (!line.substring(0, line.indexOf(";")).equals(email)) {
                pw.println(line);
            }
            line = bfr.readLine();
        }
        pw.close();
    }

    //gets the shopping cart statistics
    public static void getShoppingCartStatistics(String storeName) throws IOException {
        File f = new File("shoppingCartData.txt");
        FileReader fr = new FileReader(f);
        BufferedReader bfr = new BufferedReader(fr);

        String line = bfr.readLine();
        while (line != null) {
            line = line.substring(0);
            line = bfr.readLine();
        }

    }
}
