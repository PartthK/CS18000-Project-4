import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 04 -- Main
 * <p>
 * All the main processing and user display for the Furniture Marketplace program.
 *
 * @author Group 2 - L23
 * @version November 13, 2023
 */

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean loginStatus = false; //false until successfully logged in
        String email = "";
        String password = "";
        String accountType = ""; //used to determine whether a user is a customer or a seller
        Customer customer = null;
        Seller seller = null;
        Account account = null;
        boolean inputInvalid = true;
        int menuChoice = 0; //updated with scanner at every menu decision
        boolean repromptMainMenu = true; //used to determine if the main menu should be redisplayed
        boolean isSortedbyPrice = false; //determines if the marketplace has been sorted by price
        boolean isSortedbyQuantity = false; //determines if marketplace is sorted by quantity
        File shoppingCartData = new File("shoppingcartdata.txt");
        ArrayList<Item> allProducts = Seller.getAllProducts();
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getAvailable() <= 0) {
                allProducts.remove(allProducts.get(i));
            }
        }
        //login process
        System.out.println("Welcome to the Furniture Marketplace! " +
                "Would you like to\n1. Login\n2. Create a new account");
        while (inputInvalid) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input type. Please try again.");
                scanner.nextLine();
            } else {
                menuChoice = scanner.nextInt();
                scanner.nextLine();
                inputInvalid = false;
            }
        }
        inputInvalid = true;

        while (!loginStatus) {
            if (menuChoice == 1) {
                System.out.println("Enter your Email: ");
                email = scanner.nextLine();
                System.out.println("Enter your Password: ");
                password = scanner.nextLine();
                account = Account.login(email, password);
                if (account != null) {
                    loginStatus = true;
                    accountType = account.getRole();
                } else {
                    System.out.println("Error: Login error for reason stated above.");
                }
            } else if (menuChoice == 2) {
                System.out.println("Enter the Email you would like: ");
                email = scanner.nextLine();
                System.out.println("Enter the Password you would like: ");
                password = scanner.nextLine();
                System.out.println("Would you like this to be a customer account or a seller account?");
                accountType = scanner.nextLine();
                if (accountType.equals("customer")) {
                    account = new Account(email, password, accountType);
                    if (Account.checkForInvalidCharacters(email) && Account.checkForInvalidCharacters(password) &&
                            account.createAccount()) {
                        loginStatus = true;
                    } else {
                        System.out.println("Please try again.");
                    }
                } else if (accountType.equals("seller")) {
                    account = new Account(email, password, accountType);
                    if (Account.checkForInvalidCharacters(email) && Account.checkForInvalidCharacters(password) &&
                            account.createAccount()) {
                        loginStatus = true;
                    } else {
                        System.out.println("Please try again.");
                    }
                } else {
                    System.out.println("Error: Please enter an account type of either \"customer\" or \"seller\".");
                }
            } else {
                System.out.println("Error: Please enter a valid choice from the option numbers listed!");
            }
        }

        //after fully logged in
        ShoppingCart shoppingCart = ShoppingCart.readShoppingCart(shoppingCartData, email);
        while (repromptMainMenu) {
            //used to determine if the customer menu should be displayed
            boolean repromptCustomerMenu;
            //used to determine if the seller menu should be displayed
            boolean repromptSellerMenu;
            //used to determine if the customer statistics dashboard should be displayed
            boolean repromptCustomerDashboard = true;
            //used to determine if the seller statistics dashboard should be displayed
            boolean repromptSellerDashboard = true;
            boolean repromptAccountMenu = true;
            System.out.println("What would you like to do?" +
                    "\n1. Open Marketplace" +
                    "\n2. Open Statistics Dashboard" +
                    "\n3. Manage Your Account" +
                    "\n4. Log Out");
            while (inputInvalid) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input type. Please try again.");
                    scanner.nextLine();
                } else {
                    menuChoice = scanner.nextInt();
                    scanner.nextLine();
                    inputInvalid = false;
                }
            }
            inputInvalid = true;
            //makes sure only the correct type of menu will appear
            if (accountType.equals("customer")) {
                repromptCustomerMenu = true;
                repromptSellerMenu = false;
                customer = new Customer(email);
            } else {
                repromptCustomerMenu = false;
                repromptSellerMenu = true;
                seller = new Seller(email);
                seller.readStores("allStores.txt");
            }
            if (menuChoice == 1) {
                if (accountType.equals("customer")) {
                    //customer menu processing
                    while (repromptCustomerMenu) {
                        boolean repromptPurchaseHistoryMenu = true;
                        boolean repromptProductSelection = true;
                        boolean repromptSortMenu = true;
                        boolean repromptCartMenu = true;
                        System.out.println("What would you like to do?" +
                                "\n1. View all Marketplace Listings" +
                                "\n2. Search for a Listed Product" +
                                "\n3. Sort Marketplace Listings" +
                                "\n4. Shopping Cart (View and/or Purchase)" +
                                "\n5. View Purchase History" +
                                "\n6. Return to Main Menu.");
                        while (inputInvalid) {
                            if (!scanner.hasNextInt()) {
                                System.out.println("Invalid input type. Please try again.");
                                scanner.nextLine();
                            } else {
                                menuChoice = scanner.nextInt();
                                scanner.nextLine();
                                inputInvalid = false;
                            }
                        }
                        inputInvalid = true;
                        switch (menuChoice) {
                            case 1: //marketplace listings
                                while (repromptProductSelection) {
                                    allProducts = Seller.getAllProducts();
                                    ArrayList<Item> allProductsSorted = new ArrayList<>();
                                    if (isSortedbyPrice) {
                                        Item highestPricedProduct = null;
                                        int highestPricedIndex = 0;
                                        while (!allProducts.isEmpty()) {
                                            for (int i = 0; i < allProducts.size(); i++) {
                                                if (highestPricedProduct == null) {
                                                    highestPricedProduct = allProducts.get(i);
                                                    highestPricedIndex = i;
                                                }
                                                if (allProducts.get(i).getPrice() > highestPricedProduct.getPrice()) {
                                                    highestPricedProduct = allProducts.get(i);
                                                    highestPricedIndex = i;
                                                }
                                            }
                                            allProductsSorted.add(highestPricedProduct);
                                            allProducts.remove(highestPricedIndex);
                                            highestPricedProduct = null;
                                        }
                                        allProducts = allProductsSorted;
                                    } else if (isSortedbyQuantity) {
                                        Item highestQuantityProduct = null;
                                        int highestQuantityIndex = 0;
                                        while (!allProducts.isEmpty()) {
                                            for (int i = 0; i < allProducts.size(); i++) {
                                                if (highestQuantityProduct == null) {
                                                    highestQuantityProduct = allProducts.get(i);
                                                    highestQuantityIndex = i;
                                                }
                                                if (allProducts.get(i).getAvailable() >
                                                        highestQuantityProduct.getAvailable()) {
                                                    highestQuantityProduct = allProducts.get(i);
                                                    highestQuantityIndex = i;
                                                }
                                            }
                                            allProductsSorted.add(highestQuantityProduct);
                                            allProducts.remove(highestQuantityIndex);
                                            highestQuantityProduct = null;
                                        }
                                        allProducts = allProductsSorted;
                                    }
                                    boolean repromptSpecificProductOptions = true;
                                    for (int i = 0; i < allProducts.size(); i++) {
                                        System.out.println(i + ". " + allProducts.get(i).displayToString());
                                    }
                                    System.out.println("Enter the number of the product you would like to view, " +
                                            "or enter -1 to return.");
                                    while (inputInvalid) {
                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Invalid input type. Please try again.");
                                            scanner.nextLine();
                                        } else {
                                            menuChoice = scanner.nextInt();
                                            scanner.nextLine();
                                            inputInvalid = false;
                                        }
                                    }
                                    inputInvalid = true;
                                    if (menuChoice >= 0 && menuChoice < allProducts.size()) {
                                        int productIndex = menuChoice;
                                        while (repromptSpecificProductOptions) {
                                            System.out.println(allProducts.get(productIndex).displayDetails());
                                            System.out.println("What would you like to do for this product?: "
                                                    + allProducts.get(productIndex).getItemName() + "\n" +
                                                    "1. Purchase Product\n" +
                                                    "2. Add Product to Shopping Cart\n" +
                                                    "3. Remove Product from Shopping Cart\n" +
                                                    "4. Contact the Seller of this Product\n" +
                                                    "5. Return to Market Listings");
                                            while (inputInvalid) {
                                                if (!scanner.hasNextInt()) {
                                                    System.out.println("Invalid input type. Please try again.");
                                                    scanner.nextLine();
                                                } else {
                                                    menuChoice = scanner.nextInt();
                                                    scanner.nextLine();
                                                    inputInvalid = false;
                                                }
                                            }
                                            inputInvalid = true;
                                            switch (menuChoice) {
                                                case 1:
                                                    customer.purchaseItem(allProducts
                                                                    .get(productIndex).getStoreName(),
                                                            allProducts.get(productIndex), email);
                                                    System.out.println("Purchase successful!");
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                case 2:
                                                    boolean alreadyInCart = false;
                                                    for (int i = 0; i < shoppingCart.getItemsInCart().size(); i++) {
                                                        if (shoppingCart.getItemsInCart().get(i).getStoreName()
                                                                .equals(allProducts.
                                                                        get(productIndex).getStoreName())
                                                                && shoppingCart.getItemsInCart().get(i)
                                                                .getItemName().equals(allProducts.
                                                                        get(productIndex).getItemName())) {
                                                            System.out.println("Item already in cart.");
                                                            alreadyInCart = true;
                                                        }
                                                    }
                                                    if (!alreadyInCart) {
                                                        shoppingCart.addToCart(allProducts.get(productIndex));
                                                        System.out.println("Added to cart!");
                                                    }
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                case 3:
                                                    for (int i = 0; i < shoppingCart.getItemsInCart().size(); i++) {
                                                        if (shoppingCart.getItemsInCart()
                                                                .get(i).getStoreName().equals(
                                                                allProducts.get(i).getStoreName()) &&
                                                                shoppingCart.getItemsInCart().get(i).getItemName().
                                                                        equals(
                                                                                allProducts.get(productIndex)
                                                                                        .getItemName())) {
                                                            shoppingCart.removeFromCart(shoppingCart
                                                                    .getItemsInCart().get(i));
                                                        }
                                                    }
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                case 4:
                                                    System.out.println("Seller's email address:" +
                                                            Seller.getEmailFromStoreName
                                                                    (allProducts.get(productIndex).getStoreName()));
                                                case 5:
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                default:
                                                    System.out.println("Error: Please enter a valid choice" +
                                                            " from the option numbers listed!");
                                            }
                                        }
                                    } else if (menuChoice == -1) {
                                        repromptProductSelection = false;
                                    } else {
                                        System.out.println("Error: Please enter a valid choice " +
                                                "from the option numbers listed!");
                                    }
                                }
                                break;
                            case 2: //search for a listed product
                                System.out.println("Enter the term you would like to search for:");
                                String searchTerm = scanner.nextLine();
                                ArrayList<Item> relatedProducts = new ArrayList<>();
                                for (int i = 0; i < allProducts.size(); i++) {
                                    if (allProducts.get(i).getItemName().contains(searchTerm) ||
                                            allProducts.get(i).getItemDescription().contains(searchTerm)) {
                                        relatedProducts.add(allProducts.get(i));
                                    }
                                }
                                while (repromptProductSelection) {
                                    allProducts = Seller.getAllProducts();
                                    ArrayList<Item> allProductsSorted = new ArrayList<>();
                                    if (isSortedbyPrice) {
                                        Item highestPricedProduct = null;
                                        int highestPricedIndex = 0;
                                        while (!allProducts.isEmpty()) {
                                            for (int i = 0; i < allProducts.size(); i++) {
                                                if (highestPricedProduct == null) {
                                                    highestPricedProduct = allProducts.get(i);
                                                    highestPricedIndex = i;
                                                }
                                                if (allProducts.get(i).getPrice() > highestPricedProduct.getPrice()) {
                                                    highestPricedProduct = allProducts.get(i);
                                                    highestPricedIndex = i;
                                                }
                                            }
                                            allProductsSorted.add(highestPricedProduct);
                                            allProducts.remove(highestPricedIndex);
                                            highestPricedProduct = null;
                                        }
                                        allProducts = allProductsSorted;
                                    } else if (isSortedbyQuantity) {
                                        Item highestQuantityProduct = null;
                                        int highestQuantityIndex = 0;
                                        while (!allProducts.isEmpty()) {
                                            for (int i = 0; i < allProducts.size(); i++) {
                                                if (highestQuantityProduct == null) {
                                                    highestQuantityProduct = allProducts.get(i);
                                                    highestQuantityIndex = i;
                                                }
                                                if (allProducts.get(i).getAvailable() >
                                                        highestQuantityProduct.getAvailable()) {
                                                    highestQuantityProduct = allProducts.get(i);
                                                    highestQuantityIndex = i;
                                                }
                                            }
                                            allProductsSorted.add(highestQuantityProduct);
                                            allProducts.remove(highestQuantityIndex);
                                            highestQuantityProduct = null;
                                        }
                                        allProducts = allProductsSorted;
                                    }
                                    boolean repromptSpecificProductOptions = true;
                                    for (int i = 0; i < relatedProducts.size(); i++) {
                                        System.out.println(i + ". " + relatedProducts.get(i).displayToString());
                                    }
                                    System.out.println("Enter the number of the product you would like to view, " +
                                            "or enter -1 to return.");
                                    while (inputInvalid) {
                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Invalid input type. Please try again.");
                                            scanner.nextLine();
                                        } else {
                                            menuChoice = scanner.nextInt();
                                            scanner.nextLine();
                                            inputInvalid = false;
                                        }
                                    }
                                    inputInvalid = true;
                                    if (menuChoice >= 0 && menuChoice < relatedProducts.size()) {
                                        int productIndex = menuChoice;
                                        while (repromptSpecificProductOptions) {
                                            System.out.println(relatedProducts.get(productIndex).displayDetails());
                                            System.out.println("What would you like to do for this product?: "
                                                    + relatedProducts.get(productIndex).getItemName() + "\n" +
                                                    "1. Purchase Product\n" +
                                                    "2. Add Product to Shopping Cart\n" +
                                                    "3. Remove Product from Shopping Cart\n" +
                                                    "4. Contact the Seller of this Product\n" +
                                                    "5. Return to Market Listings");
                                            while (inputInvalid) {
                                                if (!scanner.hasNextInt()) {
                                                    System.out.println("Invalid input type. Please try again.");
                                                    scanner.nextLine();
                                                } else {
                                                    menuChoice = scanner.nextInt();
                                                    scanner.nextLine();
                                                    inputInvalid = false;
                                                }
                                            }
                                            inputInvalid = true;
                                            switch (menuChoice) {
                                                case 1:
                                                    customer.purchaseItem
                                                            (relatedProducts.get(productIndex).getStoreName(),
                                                                    relatedProducts.get(productIndex), email);
                                                    System.out.println("Purchase successful!");
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                case 2:
                                                    shoppingCart.addToCart(relatedProducts.get(productIndex));
                                                    System.out.println("Added to cart!");
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                case 3:
                                                    if (shoppingCart.getItemsInCart().contains(relatedProducts.
                                                            get(productIndex))) {
                                                        shoppingCart.removeFromCart(relatedProducts.
                                                                get(productIndex));
                                                        System.out.println("Removed from cart!");
                                                    } else {
                                                        System.out.println("This item is not in your cart.");
                                                    }
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                case 4:
                                                    System.out.println("Seller's email address:" +
                                                            relatedProducts.get(productIndex).getStoreName());
                                                case 5:
                                                    repromptSpecificProductOptions = false;
                                                    break;
                                                default:
                                                    System.out.println("Error: Please enter a valid choice" +
                                                            " from the option numbers listed!");
                                            }
                                        }
                                    } else if (menuChoice == -1) {
                                        repromptProductSelection = false;
                                    } else {
                                        System.out.println("Error: Please enter a valid choice " +
                                                "from the option numbers listed!");
                                    }
                                }
                                break;
                            case 3: //sort marketplace listings
                                while (repromptSortMenu) {
                                    System.out.println("What would you like to do?" +
                                            "\n1. Sort by Price" +
                                            "\n2. Sort by Quantity Available" +
                                            "\n3. Return to Main Menu");
                                    while (inputInvalid) {
                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Invalid input type. Please try again.");
                                            scanner.nextLine();
                                        } else {
                                            menuChoice = scanner.nextInt();
                                            scanner.nextLine();
                                            inputInvalid = false;
                                        }
                                    }
                                    inputInvalid = true;
                                    if (menuChoice == 1) {
                                        isSortedbyPrice = true;
                                        if (isSortedbyQuantity) {
                                            isSortedbyQuantity = false;
                                        }
                                        repromptSortMenu = false;
                                    } else if (menuChoice == 2) {
                                        isSortedbyQuantity = true;
                                        if (isSortedbyPrice) {
                                            isSortedbyPrice = false;
                                        }
                                        repromptSortMenu = false;
                                    } else if (menuChoice == 3) {
                                        repromptSortMenu = false;
                                    } else {
                                        System.out.println("Error:" +
                                                "Please enter a valid choice from the option numbers listed!");
                                    }
                                }
                                break;
                            case 4: //view shopping cart
                                while (repromptCartMenu) {
                                    System.out.println("Here is your current cart:");
                                    if (!shoppingCart.getItemsInCart().isEmpty()) {
                                        System.out.println(shoppingCart.displayToString());
                                        System.out.println("Would you like to purchase all the items in your cart?" +
                                                "\n1. Yes" +
                                                "\n2. No");
                                        while (inputInvalid) {
                                            if (!scanner.hasNextInt()) {
                                                System.out.println("Invalid input type. Please try again.");
                                                scanner.nextLine();
                                            } else {
                                                menuChoice = scanner.nextInt();
                                                scanner.nextLine();
                                                inputInvalid = false;
                                            }
                                        }
                                        inputInvalid = true;
                                        if (menuChoice == 1) {
                                            System.out.println("Purchasing cart...");
                                            for (int i = 0; i < shoppingCart.getItemsInCart().size(); i++) {
                                                customer.purchaseItem
                                                        (shoppingCart.getItemsInCart().get(i).getStoreName(),
                                                                shoppingCart.getItemsInCart().get(i), email);
                                                System.out.println(shoppingCart.getItemsInCart()
                                                        .get(i).getItemName() + "... purchased");
                                            }
                                            System.out.println("\nAll items purchased! Congrats on your order :)");
                                            repromptCartMenu = false;
                                        } else if (menuChoice == 2) {
                                            repromptCartMenu = false;
                                        } else {
                                            System.out.println("Error: " +
                                                    "Please enter a valid choice from the option numbers listed!");
                                        }
                                    } else {
                                        System.out.println("Your shopping cart is currently empty!");
                                        repromptCartMenu = false;
                                    }
                                }
                                break;
                            case 5: // view purchase history
                                Customer.getPurchaseHistoryStream(email);
                                while (repromptPurchaseHistoryMenu) {
                                    System.out.println("What would you like to do?" +
                                            "\n1. Export Purchase History to File" +
                                            "\n2. Return to Previous Menu");
                                    while (inputInvalid) {
                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Invalid input type. Please try again.");
                                            scanner.nextLine();
                                        } else {
                                            menuChoice = scanner.nextInt();
                                            scanner.nextLine();
                                            inputInvalid = false;
                                        }
                                    }
                                    inputInvalid = true;
                                    if (menuChoice == 1) {
                                        customer.getPurchaseHistory(email);
                                        repromptPurchaseHistoryMenu = false;
                                    } else if (menuChoice == 2) {
                                        repromptPurchaseHistoryMenu = false;
                                    } else {
                                        System.out.println("Error: " +
                                                "Please enter a valid choice from the option numbers listed!");
                                    }
                                }
                                break;
                            case 6: //return to main menu
                                repromptCustomerMenu = false;
                                break;
                            default:
                                System.out.println("Error:" +
                                        " Please enter a valid choice from the option numbers listed!");
                                break;
                        }
                    }
                } else {
                    while (repromptSellerMenu) {
                        boolean repromptStoreSelection = true;
                        boolean repromptDeleteStoreWarning = true;
                        boolean repromptStoreProductCreation = true;
                        System.out.println("What would you like to do?" +
                                "\n1. Manage your Stores" +
                                "\n2. Open a New Store" +
                                "\n3. Delete a Store" +
                                "\n4. Export Your Products to CSV" +
                                "\n5. Return to Main Menu");
                        while (inputInvalid) {
                            if (!scanner.hasNextInt()) {
                                System.out.println("Invalid input type. Please try again.");
                                scanner.nextLine();
                            } else {
                                menuChoice = scanner.nextInt();
                                scanner.nextLine();
                                inputInvalid = false;
                            }
                        }
                        inputInvalid = true;
                        if (menuChoice == 1) {
                            while (repromptStoreSelection) {
                                boolean repromptStoreManagement = true;
                                ArrayList<Store> myStores = seller.getStores();
                                for (int i = 0; i < myStores.size(); i++) {
                                    System.out.println(i + ". " + myStores.get(i).getStoreName());
                                }
                                System.out.println("Enter the number of the store you would like to manage," +
                                        " or enter -1 to return.");
                                while (inputInvalid) {
                                    if (!scanner.hasNextInt()) {
                                        System.out.println("Invalid input type. Please try again.");
                                        scanner.nextLine();
                                    } else {
                                        menuChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        inputInvalid = false;
                                    }
                                }
                                inputInvalid = true;
                                if (menuChoice >= 0 && menuChoice < myStores.size()) {
                                    int storeIndex = menuChoice;
                                    while (repromptStoreManagement) {
                                        System.out.println("What would you like to do for this store?: "
                                                + myStores.get(storeIndex).getStoreName() + "\n" +
                                                "1. Add Product to Store\n" +
                                                "2. Remove Product from Store\n" +
                                                "3. Edit Product in Store\n" +
                                                "4. Return to store selection");
                                        while (inputInvalid) {
                                            if (!scanner.hasNextInt()) {
                                                System.out.println("Invalid input type. Please try again.");
                                                scanner.nextLine();
                                            } else {
                                                menuChoice = scanner.nextInt();
                                                scanner.nextLine();
                                                inputInvalid = false;
                                            }
                                        }
                                        inputInvalid = true;
                                        switch (menuChoice) {
                                            case 1:
                                                System.out.println("Enter the name of the product to add: ");
                                                String itemName = scanner.nextLine();
                                                System.out.println("Enter the description of the product: ");
                                                String itemDescription = scanner.nextLine();
                                                System.out.println("Enter the quantity available (as an integer): ");
                                                int itemQuantity = 0;
                                                while (inputInvalid) {
                                                    if (!scanner.hasNextInt()) {
                                                        System.out.println("Invalid input type. Please try again.");
                                                        scanner.nextLine();
                                                    } else {
                                                        itemQuantity = scanner.nextInt();
                                                        scanner.nextLine();
                                                        inputInvalid = false;
                                                    }
                                                }
                                                inputInvalid = true;
                                                System.out.println("Enter the price of the product " +
                                                        "(in a format like 15.35): ");
                                                double itemPrice = 0.0;
                                                while (inputInvalid) {
                                                    if (!scanner.hasNextDouble()) {
                                                        System.out.println("Invalid input type. Please try again.");
                                                        scanner.nextLine();
                                                    } else {
                                                        itemPrice = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        inputInvalid = false;
                                                    }
                                                }
                                                inputInvalid = true;
                                                System.out.println("Creating Product....");
                                                if (Account.checkForInvalidCharacters(itemName) &&
                                                        Account.checkForInvalidCharacters(itemDescription)) {
                                                    Item itemToAdd = new Item(myStores.get(storeIndex).getStoreName(),
                                                            itemName, itemDescription, itemQuantity, itemPrice);
                                                    seller.addProductInTheStore
                                                            (myStores.get(storeIndex).getStoreName(), itemToAdd);
                                                    seller.readStores("allStores.txt"); //might need this
                                                    System.out.println("Item successfully added to store!");
                                                }
                                                break;
                                            case 2:
                                                System.out.println("Enter the name of the product to remove: ");
                                                String itemToRemove = scanner.nextLine();
                                                seller.deleteProductInTheStore(myStores.get(storeIndex)
                                                                .getStoreName(), itemToRemove);
                                                System.out.println("Item successfully removed from store!");
                                                break;
                                            case 3:
                                                System.out.println("Enter the name of the product to edit: ");
                                                String itemToEdit = scanner.nextLine();
                                                System.out.println("Enter the updated name of the product: ");
                                                String editedItemName = scanner.nextLine();
                                                System.out.println("Enter the updated description of the product: ");
                                                String editedItemDescription = scanner.nextLine();
                                                System.out.println("Enter the updated quantity available " +
                                                        "(as an integer): ");
                                                int editedItemQuantity = 0;
                                                while (inputInvalid) {
                                                    if (!scanner.hasNextInt()) {
                                                        System.out.println("Invalid input type. Please try again.");
                                                        scanner.nextLine();
                                                    } else {
                                                        editedItemQuantity = scanner.nextInt();
                                                        scanner.nextLine();
                                                        inputInvalid = false;
                                                    }
                                                }
                                                inputInvalid = true;
                                                System.out.println("Enter the updated price of the product " +
                                                        "(in a format like 15.35): ");
                                                double editedItemPrice = 0.0;
                                                while (inputInvalid) {
                                                    if (!scanner.hasNextDouble()) {
                                                        System.out.println("Invalid input type. Please try again.");
                                                        scanner.nextLine();
                                                    } else {
                                                        editedItemPrice = scanner.nextDouble();
                                                        scanner.nextLine();
                                                        inputInvalid = false;
                                                    }
                                                }
                                                inputInvalid = true;
                                                if (Account.checkForInvalidCharacters(editedItemName) &&
                                                        Account.checkForInvalidCharacters(editedItemDescription)) {
                                                    Item editedItem = new Item(myStores.get(storeIndex).
                                                            getStoreName(), editedItemName,
                                                            editedItemDescription, editedItemQuantity,
                                                            editedItemPrice);
                                                    seller.editProductInTheStore
                                                            (myStores.get(storeIndex).getStoreName(),
                                                                    itemToEdit, editedItem);
                                                }
                                                break;
                                            case 4:
                                                repromptStoreManagement = false;
                                                break;
                                            default:
                                                System.out.println("Error: Please enter a valid choice " +
                                                        "from the option numbers listed!");
                                                break;
                                        }
                                    }
                                } else if (menuChoice == -1) {
                                    repromptStoreSelection = false;
                                } else {
                                    System.out.println("Error: Please enter a valid choice " +
                                            "from the option numbers listed!");
                                }
                            }
                        } else if (menuChoice == 2) {
                            //open a new store
                            System.out.println("There are a few steps to opening a store!" +
                                    "Please follow the instruction below!");
                            System.out.println("Enter the name of the store you would like to open: ");
                            String storeName = scanner.nextLine();
                            if (!Account.checkForInvalidCharacters(storeName)) {
                                repromptStoreProductCreation = false;
                            }
                            while (repromptStoreProductCreation) {
                                System.out.println("Next we need to create products for the store!");
                                System.out.println("Would you like to..." +
                                        "\n1. Enter the Product Information" +
                                        "\n2. Import it from a CSV file" +
                                        "\n3. Return to Previous Menu");
                                while (inputInvalid) {
                                    if (!scanner.hasNextInt()) {
                                        System.out.println("Invalid input type. Please try again.");
                                        scanner.nextLine();
                                    } else {
                                        menuChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        inputInvalid = false;
                                    }
                                }
                                inputInvalid = true;
                                ArrayList<Item> itemList = new ArrayList<>();
                                if (menuChoice == 1) {
                                    System.out.println("How many products would you like to create " +
                                            "(please enter an integer):");
                                    int numOfProducts = 0;
                                    while (inputInvalid) {
                                        if (!scanner.hasNextInt()) {
                                            System.out.println("Invalid input type. Please try again.");
                                            scanner.nextLine();
                                        } else {
                                            numOfProducts = scanner.nextInt();
                                            scanner.nextLine();
                                            inputInvalid = false;
                                        }
                                    }
                                    inputInvalid = true;
                                    int created = 0;
                                    //creates a new products, goes until the amount of products the seller wanted
                                    while (created < numOfProducts) {
                                        System.out.println("Enter the name of the product: ");
                                        String itemName = scanner.nextLine();
                                        System.out.println("Enter the description of the product: ");
                                        String itemDescription = scanner.nextLine();
                                        System.out.println("Enter the quantity available (as an integer): ");
                                        int itemQuantity = 0;
                                        while (inputInvalid) {
                                            if (!scanner.hasNextInt()) {
                                                System.out.println("Invalid input type. Please try again.");
                                                scanner.nextLine();
                                            } else {
                                                itemQuantity = scanner.nextInt();
                                                scanner.nextLine();
                                                inputInvalid = false;
                                            }
                                        }
                                        inputInvalid = true;
                                        System.out.println("Enter the price of the product (in a format like 15.35): ");
                                        double itemPrice = 0.0;
                                        while (inputInvalid) {
                                            if (!scanner.hasNextDouble()) {
                                                System.out.println("Invalid input type. Please try again.");
                                                scanner.nextLine();
                                            } else {
                                                itemPrice = scanner.nextDouble();
                                                scanner.nextLine();
                                                inputInvalid = false;
                                            }
                                        }
                                        inputInvalid = true;
                                        System.out.println("Creating Product....");
                                        if (Account.checkForInvalidCharacters(itemName) &&
                                                Account.checkForInvalidCharacters(itemDescription)) {
                                            Item myItem = new Item(storeName, itemName, itemDescription,
                                                    itemQuantity, itemPrice);
                                            itemList.add(myItem);
                                            System.out.println("Product Created!\n");
                                        }
                                        created++;
                                    }
                                    if (!itemList.isEmpty()) {
                                        Store newStore = new Store(itemList);
                                        seller.createStore(newStore);
                                    }
                                    repromptStoreProductCreation = false;
                                } else if (menuChoice == 2) {
                                    System.out.println("Enter the filename to import the products from:");
                                    String filename = scanner.nextLine();
                                    itemList = Seller.importItem(filename);
                                    Store newStore = new Store(itemList);
                                    seller.createStore(newStore);
                                    repromptStoreProductCreation = false;
                                } else if (menuChoice == 3) {
                                    repromptStoreProductCreation = false;
                                } else {
                                    System.out.println("Error: Please enter a valid choice " +
                                            "from the option numbers listed!");
                                }
                            }
                        } else if (menuChoice == 3) {
                            //delete a store
                            System.out.println("What is the name of the store you would like to delete?");
                            String storeName = scanner.nextLine();
                            while (repromptDeleteStoreWarning) {
                                System.out.println("Are you sure you would like to delete " + storeName + "?" +
                                        "\n1. Yes" +
                                        "\n2. No");
                                while (inputInvalid) {
                                    if (!scanner.hasNextInt()) {
                                        System.out.println("Invalid input type. Please try again.");
                                        scanner.nextLine();
                                    } else {
                                        menuChoice = scanner.nextInt();
                                        scanner.nextLine();
                                        inputInvalid = false;
                                    }
                                }
                                inputInvalid = true;
                                if (menuChoice == 1) {
                                    seller.deleteStore(storeName);
                                    System.out.println("Store Deleted!");
                                    repromptDeleteStoreWarning = false;
                                } else if (menuChoice == 2) {
                                    repromptDeleteStoreWarning = false;
                                } else {
                                    System.out.println("Error: Please enter a valid choice " +
                                            "from the option numbers listed!");
                                }
                            }
                        } else if (menuChoice == 4) {
                            seller.exportItem();
                            System.out.println("Products exported to " + email + "-products.csv!");
                        } else if (menuChoice == 5) {
                            repromptSellerMenu = false;
                        } else {
                            System.out.println("Error: Please enter a valid choice from the option numbers listed!");
                        }
                    }
                }
            } else if (menuChoice == 2) {
                if (accountType.equals("customer")) {
                    while (repromptCustomerDashboard) {
                        System.out.println("What would you like to do?" +
                                "\n1. View Store Data Sorted by Products Sold" +
                                "\n2. View Store Data Sorted by Purchase History" +
                                "\n3. Return to Main Menu");
                        while (inputInvalid) {
                            if (!scanner.hasNextInt()) {
                                System.out.println("Invalid input type. Please try again.");
                                scanner.nextLine();
                            } else {
                                menuChoice = scanner.nextInt();
                                scanner.nextLine();
                                inputInvalid = false;
                            }
                        }
                        inputInvalid = true;
                        if (menuChoice == 1) {
                            customer.seeAllStoresAndSales();
                        } else if (menuChoice == 2) {
                            customer.howManyProductsFromEachStore();
                        } else if (menuChoice == 3) {
                            repromptCustomerDashboard = false;
                        } else {
                            System.out.println("Error: Please enter a valid choice from the option numbers listed!");
                        }
                    }
                } else {
                    while (repromptSellerDashboard) {
                        System.out.println("What would you like to do?" +
                                "\n1. View Your Sales Data Sorted by Customer" +
                                "\n2. View Your Sales Data Sorted by Product" +
                                "\n3. View Your Shopping Cart Data" +
                                "\n4. View Your List of Sales by Store" +
                                "\n5. View Your Total Revenue" +
                                "\n6. View Your Seller History" +
                                "\n7. Return back");
                        while (inputInvalid) {
                            if (!scanner.hasNextInt()) {
                                System.out.println("Invalid input type. Please try again.");
                                scanner.nextLine();
                            } else {
                                menuChoice = scanner.nextInt();
                                scanner.nextLine();
                                inputInvalid = false;
                            }
                        }
                        inputInvalid = true;
                        switch (menuChoice) {
                            case 1:
                                seller.salesOfSpecificCustomer();
                                break;
                            case 2:
                                seller.salesOfSpecificProduct();
                                break;
                            case 3:
                                seller.getCustomerShoppingcartData();
                                break;
                            case 4:
                                seller.viewListOfSalesByStore();
                                break;
                            case 5:
                                seller.viewTotalRevenue();
                                break;
                            case 6:
                                seller.sellerCustomerHistory();
                                break;
                            case 7:
                                repromptSellerDashboard = false;
                                break;
                            default:
                                System.out.println("Error: Please enter a valid choice " +
                                        "from the option numbers listed!");
                                break;
                        }
                    }
                }
            } else if (menuChoice == 3) {
                while (repromptAccountMenu) {
                    boolean repromptDeletionWarning = true;
                    System.out.println("What would you like to do?" +
                            "\n1. Edit Account Details" +
                            "\n2. Delete Account" +
                            "\n3. Return to Main Menu");
                    while (inputInvalid) {
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input type. Please try again.");
                            scanner.nextLine();
                        } else {
                            menuChoice = scanner.nextInt();
                            scanner.nextLine();
                            inputInvalid = false;
                        }
                    }
                    inputInvalid = true;
                    if (menuChoice == 1) {
                        System.out.println("Enter the updated email for your account: ");
                        String newEmail = scanner.nextLine();
                        System.out.println("Enter the updated password for your account: ");
                        String newPassword = scanner.nextLine();
                        System.out.println("Enter the updated role for your account (customer or seller): ");
                        String newAccountType = scanner.nextLine();
                        if (Account.checkForInvalidCharacters(newEmail)
                                && Account.checkForInvalidCharacters(newPassword)) {
                            account.editAccount(newEmail, newPassword, newAccountType);
                            email = newEmail; //does not reassign password variable because it is only used on login
                            accountType = newAccountType;
                            repromptAccountMenu = false;
                        }
                    } else if (menuChoice == 2) {
                        while (repromptDeletionWarning) {
                            System.out.println("Warning: this will permanently delete this account" +
                                    " and any associated data! Are you sure you want to proceed?" +
                                    "\n1. Yes" +
                                    "\n2. No");
                            while (inputInvalid) {
                                if (!scanner.hasNextInt()) {
                                    System.out.println("Invalid input type. Please try again.");
                                    scanner.nextLine();
                                } else {
                                    menuChoice = scanner.nextInt();
                                    scanner.nextLine();
                                    inputInvalid = false;
                                }
                            }
                            inputInvalid = true;
                            if (menuChoice == 1) {
                                if (account.removeAccount()) {
                                    System.out.println("Account deletion successful! Goodbye!");
                                    repromptDeletionWarning = false;
                                    repromptAccountMenu = false;
                                    repromptMainMenu = false;
                                }
                                if (accountType.equals("customer")) {
                                    ShoppingCart.deleteShoppingCartData(email);
                                } else {
                                    seller.removeSeller(email);
                                }
                            } else if (menuChoice == 2) {
                                repromptDeletionWarning = false;
                            } else {
                                System.out.println("Error: Please enter a valid choice " +
                                        "from the option numbers listed!");
                            }
                        }
                    } else if (menuChoice == 3) {
                        repromptAccountMenu = false;
                    } else {
                        System.out.println("Error: Please enter a valid choice from the option numbers listed!");
                    }
                }
            } else if (menuChoice == 4) {
                repromptMainMenu = false;
                System.out.println("Successfully logged out. Goodbye!");
                ShoppingCart.saveShoppingCart(shoppingCart, email);
            } else {
                System.out.println("Error: Please enter a valid choice from the option numbers listed!");
            }
        }
    }
}
