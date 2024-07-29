import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Project 04 -- Tester
 * <p>
 * this tests all the objects created
 * all methods/ideas that relate to files are tested manually
 *
 * @author Group 2- Lauren Ellis, L23
 * @version November 12, 2023
 */
public class Tester {
    /*
    Alternatively, you can choose to create a main method to accomplish a similar goal.
    Simply call each method being tested and save the result, then compare it to an expected value.
    You should pair each class created in your solution with a corresponding testing class.

    Your test cases should do the following:

    Each requirement must have a test verifying it functions properly.
    Each requirement should have an error test to verify it does not crash when receiving invalid input.
     */
    public static void main(String[] args) {
        //create an item
        Item pen = new Item("Staples", "Pen", "Writes in blue ink",
                10, 2.50);

        //test item methods

        //tests toStrings
        System.out.println("Testing item.displayToString() . . . ");
        boolean displayToWorks = false;
        System.out.println(pen.displayToString());
        if (pen.displayToString().equals("Store: Staples\nProduct: Pen\nPrice: $2.50 \n")) {
            displayToWorks = true;
        }
        System.out.println("True or false, item displayToString works? " + displayToWorks);
        System.out.println();

        System.out.println("Testing item.displayDetails() . . . ");
        boolean displayDetailsWorks = false;
        System.out.println(pen.displayDetails());
        if (pen.displayDetails().equals("Description: Writes in blue ink\nQuantity Available: 10")) {
            displayDetailsWorks = true;
        }
        System.out.println("True or false, item displayDetails works? " + displayDetailsWorks);
        System.out.println();

        System.out.println("Testing item.fileToString() . . . ");
        boolean fileToWorks = false;
        System.out.println(pen.fileToString());
        if (pen.fileToString().equals(
                "Store;Staples;Product;Pen;Description;Writes in blue ink;Available;10;Price;2.50")) {
            fileToWorks = true;
        }
        System.out.println("True or false, item fileToString works? " + fileToWorks);
        System.out.println();


        //tests getters
        System.out.println("Testing item.getStoreName() . . . ");
        boolean storeNameWorks = false;
        System.out.println("Item Store Name: " + pen.getStoreName());
        if (pen.getStoreName().equals("Staples")) {
            storeNameWorks = true;
        }
        System.out.println("True or false, item storeName getter works? " + storeNameWorks);
        System.out.println();

        System.out.println("Testing item.getItemName() . . . ");
        boolean nameWorks = false;
        System.out.println("Item Name: " + pen.getItemName());
        if (pen.getItemName().equals("Pen")) {
            nameWorks = true;
        }
        System.out.println("True or false, itemName getter works? " + nameWorks);
        System.out.println();

        System.out.println("Testing item.getItemDescription() . . . ");
        boolean descriptionWorks = false;
        System.out.println("Item Description: " + pen.getItemDescription());
        if (pen.getItemDescription().equals("Writes in blue ink")) {
            descriptionWorks = true;
        }
        System.out.println("True or false, itemDescription getter works? " + descriptionWorks);
        System.out.println();

        System.out.println("Testing item.getAvailable() . . . ");
        boolean availableWorks = false;
        System.out.println("Item Quantity Available: " + pen.getAvailable());
        if (pen.getAvailable() == 10) {
            availableWorks = true;
        }
        System.out.println("True or false, itemAvailable getter works? " + availableWorks);
        System.out.println();

        System.out.println("Testing item.getItemPrice() . . . ");
        boolean priceWorks = false;
        System.out.println("Item Price: " + pen.getPrice());
        if (pen.getPrice() == 2.50) {
            priceWorks = true;
        }
        System.out.println("True or false, itemPrice getter works? " + priceWorks);
        System.out.println();

        //tests setters
        System.out.println("Testing item.setStoreName() . . . ");
        boolean changeStoreNameWorks = false;
        System.out.println("Item Store Name: " + pen.getStoreName());
        pen.setStoreName("Office Depot");
        System.out.println(" *UPDATED* Item Store Name: " + pen.getStoreName());
        if (pen.getStoreName().equals("Office Depot")) {
            storeNameWorks = true;
        }
        System.out.println("True or false, item storeName setter works? " + storeNameWorks);
        System.out.println();

        System.out.println("Testing item.setItemName() . . . ");
        boolean changeName = false;
        System.out.println("Item Name: " + pen.getItemName());
        pen.setItemName("Blue Pen");
        System.out.println("*CHANGED* Item Name: " + pen.getItemName());
        if (pen.getItemName().equals("Blue Pen")) {
            changeName = true;
        }
        System.out.println("True or false, itemName setter works? " + changeName);
        System.out.println();

        System.out.println("Testing item.setItemDescription() . . . ");
        boolean changeDescription = false;
        System.out.println("Item Description: " + pen.getItemDescription());
        pen.setItemDescription("Writes in blue ink");
        System.out.println("*CHANGED* Item Description: " + pen.getItemDescription());
        if (pen.getItemDescription().equals("Writes in blue ink")) {
            changeDescription = true;
        }
        System.out.println("True or false, itemDescription setter works? " + changeDescription);
        System.out.println();

        System.out.println("Testing item.setIAvailable() . . . ");
        boolean changeAvailable = false;
        System.out.println("Item Quantity Available: " + pen.getAvailable());
        pen.setAvailable(15);
        System.out.println("*CHANGED* Item Quantity Available: " + pen.getAvailable());
        if (pen.getAvailable() == 15) {
            changeAvailable = true;
        }
        System.out.println("True or false, itemAvailable setter works? " + changeAvailable);
        System.out.println();

        System.out.println("Testing item.setPrice() . . . ");
        boolean changePrice = false;
        System.out.println("Item Price: " + pen.getPrice());
        pen.setPrice(1.25);
        System.out.println("*CHANGED* Item Price: " + pen.getPrice());
        if (pen.getPrice() == 1.25) {
            changePrice = true;
        }
        System.out.println("True or false, itemPrice setter works? " + changePrice);
        System.out.println();

        //fyi new pen is Pen(Office Depot, Blue Pen, Writes in blue ink, 15, 1.25)

        //create another item
        Item paper = new Item("Office Depot", "Paper", "8x11 printer paper" ,
                11, 25.00);


        //create an array list with items
        ArrayList<Item> itemList = new ArrayList<>();
        itemList.add(pen);
        itemList.add(paper);

        //create a store
        Store officeDepot = new Store(itemList);

        //test store methods
        // item list to string
        System.out.println("Testing store.toString() . . . ");
        boolean storeToWorks = false;
        System.out.println(officeDepot);
        if (officeDepot.toString().equals(pen.displayToString() + paper.displayToString())) {
            storeToWorks = true;
        }
        System.out.println("True or false, store ToString works? " + storeToWorks);
        System.out.println();

        //set item
        System.out.println("Testing store.setItem() . . . ");
        boolean setItemToStoreWorks = false;
        System.out.println(officeDepot);
        Item linedPaper = new Item("Office Depot", "Lined Paper",
                "College ruled lined paper with 3 hole punch", 30, 2.75 );
        officeDepot.setItem(linedPaper, 1);
        if (officeDepot.getItemList().get(1).getItemName().equals("Lined Paper")) {
            setItemToStoreWorks = true;
        }
        System.out.println("*UPDATED*\n" + officeDepot);
        for (int i = 0; i < officeDepot.getItemList().size(); i++) {

        }
        System.out.println("True or false, setting/editing an item in store works? " + setItemToStoreWorks);
        System.out.println();

        //add item
        System.out.println("Testing store.addItem() . . . ");
        boolean addToStoreWorks = false;
        System.out.println(officeDepot);
        Item tape = new Item("Office Depot", "Painters Tape",
                "Tape to make sharp lines and protect walls", 5, 5.00 );
        officeDepot.addItem(tape);
        System.out.println("*UPDATED*\n" + officeDepot);
        for (int i = 0; i < officeDepot.getItemList().size(); i++) {
            if (officeDepot.getItemList().get(i).getItemName().equals("Painters Tape")) {
                addToStoreWorks = true;
            }
        }
        System.out.println("True or false, adding an item to store works? " + addToStoreWorks);
        System.out.println();

        //delete item
        System.out.println("Testing item.deleteItem() . . . ");
        boolean deleteItemFromStoreWorks = true;
        System.out.println(officeDepot);
        officeDepot.deleteItem(tape);
        System.out.println("*UPDATED*\n" + officeDepot);
        for (int i = 0; i < officeDepot.getItemList().size(); i++) {
            if (officeDepot.getItemList().get(i).getItemName().equals("Painters Tape")) {
                deleteItemFromStoreWorks = false;
            }
        }
        System.out.println("True or false, deleting an item from store works? " + deleteItemFromStoreWorks);

        // get store name

        System.out.println();

        System.out.println("Testing store.getStoreName() . . . ");
        boolean getStoreNameWorks = false;
        System.out.println("Store Name: " + officeDepot.getStoreName());
        if (pen.getStoreName().equals("Office Depot")) {
            getStoreNameWorks = true;
        }
        System.out.println("True or false, store name getter works? " + getStoreNameWorks);
        System.out.println();

        //create a shopping cart object
        ArrayList<Item> inCart = new ArrayList<>();
        inCart.add(pen);
        inCart.add(paper);
        ShoppingCart cart = new ShoppingCart(inCart);

        //test shopping cart methods
        System.out.println("Testing shoppingCart.getItemsInCart() . . .");
        boolean itemsInCart = true;
        for (int i  = 0; i < cart.getItemsInCart().size(); i++ ) {
            if (!cart.getItemsInCart().get(i).getItemName().equals(inCart.get(i).getItemName())) {
                itemsInCart = false;
            }
        }
        System.out.println("True or false, shopping cart get items works? " + itemsInCart);
        System.out.println();


        //add to cart
        System.out.println("Testing shoppingCart.addToCart() . . .");
        boolean addToCartWorks = false;
        for (int i  = 0; i < cart.getItemsInCart().size(); i++ ) {
            System.out.println(cart.getItemsInCart().get(i).getItemName());
        }
        cart.addToCart(tape);
        if (cart.getItemsInCart().get(2).getItemName().equals("Painters Tape")) {
            addToCartWorks = true;
        }
        for (int i  = 0; i < cart.getItemsInCart().size(); i++ ) {
            System.out.println(cart.getItemsInCart().get(i).getItemName());
        }
        System.out.println("True or false, adding an item to the cart works? " + addToCartWorks);
        System.out.println();

        //remove from cart
        System.out.println("Testing shoppingCart.removeFromCart() . . .");
        boolean removeFromCartWorks = true;
        for (int i  = 0; i < cart.getItemsInCart().size(); i++ ) {
            System.out.println(cart.getItemsInCart().get(i).getItemName());
        }
        cart.removeFromCart(tape);
        for (int i = 0; i < cart.getItemsInCart().size(); i++) {
            if (cart.getItemsInCart().get(i).getItemName().equals("Painters Tape")) {
                removeFromCartWorks = false;
            }
        }
        for (int i  = 0; i < cart.getItemsInCart().size(); i++ ) {
            System.out.println(cart.getItemsInCart().get(i).getItemName());
        }
        System.out.println("True or false, removing an item from the cart works? " + removeFromCartWorks);
        System.out.println();

        //displayToString
        System.out.println("Testing shoppingCart.displayToString() . . .");
        boolean cartDisplayToWorks = false;
        System.out.println(cart.displayToString());
        if (cart.displayToString().equals("Shopping Cart:\nStore: Office Depot\nProduct: Lined Paper" +
                "\nDescription: College ruled lined paper with 3 hole punch\nPrice: 2.75" +
                "\nStore: Office Depot\nProduct: Painters Tape" +
                "\nDescription: Tape to make sharp lines and protect walls\nPrice: 5.0\n")) {
            cartDisplayToWorks = true;
        }
        System.out.println("True or false, displayToString for shopping cart works? " + cartDisplayToWorks);

        //fileToString
        System.out.println("Testing shoppingCart.fileToString() . . .");
        boolean cartFileToWorks = false;
        System.out.println(cart.fileToString());
        if (cart.fileToString().equals("Shopping Cart;" + linedPaper.fileToString() + ";" + tape.fileToString())) {
            cartFileToWorks = true;
        }
        System.out.println("True or false, fileToString for shopping cart works? " + cartFileToWorks);
        System.out.println();

        //create account object
        try {
            Account myAccount = new Account("smith12@purdue.edu", "Password123!", "customer");
            //test the account methods (like getters and setters)
            //get email
            System.out.println("Testing account.getEmail() . . .");
            boolean getEmailWorks = false;
            System.out.println("Account email: " + myAccount.getEmail());
            if (myAccount.getEmail().equals("smith12@purdue.edu")) {
                getEmailWorks = true;
            }
            System.out.println("True or false, the account email getter works? " + getEmailWorks);
            System.out.println();

            //get password
            System.out.println("Testing account.getPassword() . . .");
            boolean getPassWorks = false;
            System.out.println("Account password: " + myAccount.getPassword());
            if (myAccount.getPassword().equals("Password123!")) {
                getPassWorks = true;
            }
            System.out.println("True or false, the account password getter works? " + getPassWorks);
            System.out.println();

            //get role
            System.out.println("Testing account.getRole() . . .");
            boolean getRoleWorks = false;
            System.out.println("Account role: " + myAccount.getRole());
            if (myAccount.getRole().equals("customer")) {
                getRoleWorks = true;
            }
            System.out.println("True or false, the account role getter works? " + getRoleWorks);
            System.out.println();

            //set email
            System.out.println("Testing account.setEmail() . . . ");
            boolean setEmailWorks = false;
            System.out.println("Account email: " + myAccount.getEmail());
            myAccount.setEmail("bob123@purdue.edu");
            System.out.println("*CHANGED* Account email: " + myAccount.getEmail());
            if (myAccount.getEmail().equals("bob123@purdue.edu")) {
                setEmailWorks = true;
            }
            System.out.println("True or false, account email setter works? " + setEmailWorks);
            System.out.println();

            //set password
            System.out.println("Testing account.setPassword() . . . ");
            boolean setPassWorks = false;
            System.out.println("Account password: " + myAccount.getPassword());
            myAccount.setPassword("123!Password");
            System.out.println("*CHANGED* Account password: " + myAccount.getPassword());
            if (myAccount.getPassword().equals("123!Password")) {
                setPassWorks = true;
            }
            System.out.println("True or false, account email setter works? " + setPassWorks);
            System.out.println();

            //set role
            System.out.println("Testing account.setRole() . . . ");
            boolean setRoleWorks = false;
            System.out.println("Account role: " + myAccount.getRole());
            myAccount.setRole("seller");
            System.out.println("Account role: " + myAccount.getRole());
            if (myAccount.getRole().equals("seller")) {
                setRoleWorks = true;
            }
            System.out.println("True or false, account role setter works? " + setRoleWorks);
            System.out.println();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //create customer object
        Customer customer = new Customer("white127@purdue.edu");
        //test customer methods (like getters and setters)
        //get email
        System.out.println("Testing customer.getEmail() . . .");
        boolean customerGetEmailWorks = false;
        System.out.println("Customer email: " + customer.getEmail());
        if (customer.getEmail().equals("white127@purdue.edu")) {
            customerGetEmailWorks = true;
        }
        System.out.println("True or false, the customer email getter works? " + customerGetEmailWorks);
        System.out.println();

        //set email
        System.out.println("Testing customer.setEmail() . . . ");
        boolean customerSetEmailWorks = false;
        System.out.println("Customer email: " + customer.getEmail());
        customer.setEmail("brown396@purdue.edu");
        System.out.println("*CHANGED* Customer email: " + customer.getEmail());
        if (customer.getEmail().equals("brown396@purdue.edu")) {
            customerSetEmailWorks = true;
        }
        System.out.println("True or false, customer email setter works? " + customerSetEmailWorks);
        System.out.println();

        //create seller object
        Seller seller = new Seller("purple456@purdue.edu");
        //test seller methods that don't have to do with files
        //getEmail
        System.out.println("Testing seller.getEmail() . . .");
        boolean sellerGetEmailWorks = false;
        System.out.println("Seller email: " + seller.getEmail());
        if (seller.getEmail().equals("purple456@purdue.edu")) {
            sellerGetEmailWorks = true;
        }
        System.out.println("True or false, the seller email getter works? " + sellerGetEmailWorks);
        System.out.println();

        //productsString
        System.out.println("Testing seller.productsString() . . .");
        try {
            System.out.println(seller.productsString());
            boolean sellerProdToWorks = false;
            if (seller.productsString().equals("purple456@purdue.edu-")) {
                sellerProdToWorks = true;
            }
            System.out.println("True or false, seller productsString works? " + sellerProdToWorks);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
