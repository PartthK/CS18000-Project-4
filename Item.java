/**
 * Project 04 -- Item
 * <p>
 * Creates an item object that includes the name, description, quantity available, and price
 *
 * @author Group 2- L23
 * @version November 13, 2023
 */
public class Item {

    //fields
    public String storeName; //name of the store
    private String itemName; //name of the product
    private String itemDescription; //the description of the product
    private int available; // the quantity available for purchase
    private double price; // the price of the product


    //Constructor
    public Item(String storeName, String itemName, String itemDescription, int available, double price) {
        this.storeName = storeName;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.available = available;
        this.price = price;
    }

    //getters
    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getAvailable() {
        return available;
    }

    public double getPrice() {
        return price;
    }

    public String getStoreName() {
        return storeName;
    }

    //setters
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //to String methods (different depending on if it is displayed, to go in file, etc.)
    public String displayToString() {
        return String.format("Store: %s\nProduct: %s\nPrice: $%.2f \n", getStoreName(), getItemName(), getPrice());
    }

    public String displayDetails() {
        return String.format("Description: %s\nQuantity Available: %d", getItemDescription(), getAvailable());
    }

    public String fileToString() {
        return String.format("Store;%s;Product;%s;Description;%s;Available;%d;Price;%.2f", getStoreName(), 
                getItemName(), getItemDescription(), getAvailable(), getPrice());
        /*I used ";" as a separator so that there are no issues with substring() when reading from a file even if
        a product name or description includes a space*/
    }

    public String commafileToString() {
        return String.format(";%s,%s,%s,%d,%.2f;", getStoreName(), getItemName(),
                getItemDescription(), getAvailable(), getPrice());
        /*I used ";" as a separator so that there are no issues with substring() when reading from a file even if
        a product name or description includes a space*/
    }

    public String semiColonfileToString() {
        return String.format("%s;%s;%s;%d;%.2f", getStoreName(), getItemName(),
                getItemDescription(), getAvailable(), getPrice());
        /*I used ";" as a separator so that there are no issues with substring() when reading from a file even if
        a product name or description includes a space*/
    }
}
