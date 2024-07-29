# CS180-Project-4
Code repository for CS180 Project 4.

# Furniture Marketplace

This is a furniture marketplace that allows users to sell or buy different pieces of furniture.

## Project Compilation and Execution

Instructions on how to compile and run this project:

- It is vital that all of the text files are downloaded before running the main method. To run the project, simply run the main method and follow the directions that it provides on the terminal depending on the type of user you are and what you are trying to accomplish. 

## Submission Information

**Submitted Report on Brightspace:**
- Lauren Ellis

**Submitted Vocareum Workspace:**
- Lauren Ellis

## Report of all Classes

### Main Class

**Functionality/Relationship to other classes:**
- The main class is the source of all the code and calls all of the methods in all of the other classes that were created. The method starts off by prompting the user and asking if they would like to login or create an account. After the account is created, the user will see a different dashboard with separate things that they are able to do depending on whether the user is a customer or seller. The menu will continue to prompt the user depending on what they would like to do, and call the specific method from each class for each question/situation.

**Testing:**
- This class was primarily tested manually. Because the class was very long and called a variety of methods and other classes, we made sure to test the other classes/methods separately instead and do a final check by performing a variety of possible scenarios.

### Customer Class

**Functionality/Relationship to other classes:**
- The customer class has a variety of methods that allow the customer to perform different actions. The class includes private fields such as email, which refers to the customer’s email and the corresponding getter/setter methods, their shopping cart, and purchase history stores a list of all the customer’s purchases, something that will be used in the main as well as other classes. 
- The getPurchaseHistory method reads the purchase history from a corresponding text file and then writes it based on the customer’s email. In addition, the getPurchaseHistoryStream method allows this information to be outputted to the terminal. 
- The purchased item method simply acts as if the customer is purchasing the item and does a variety of things that would occur when the customer completes this act. This includes decreasing the availability of the item (quantity) and recording the purchase in an all customer purchases file. The reviewPurchaseHistory method allows this to be inputted to the terminal when the customer asks for it.  The customer class also contains two of the methods related to the customer side of the statistics dashboard.
- Overall, this class works with the store and item classes and the main classes to work effectively. 


**Testing:**
- The customer class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already existed and contained the information necessary for that specific method. 

### Seller Class

**Functionality/Relationship to other classes:**
- The seller class contains a variety of methods that perform different tasks. This class works with almost all of the other classes, and most of the methods involved are called in main. 
- The seller class also contains two of the methods related to the seller side of the statistics dashboard.


**Testing:**
- The seller class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already existed and contained the information necessary for that specific method. 

### ShoppingCart Class

**Functionality/Relationship to other classes:**
- This class contains an ArrayList of items and allows the user to add and remove items from the cart. This works with the customer class and contains methods that save this cart data to a file and read the data from a file for further use. In addition, a productsString method is created and used in methods when printing out to a file, etc. The class also creates methods to create and add a store as well as add, edit, and delete a product in the store.
- Other than the primary methods that the seller requires, it contains a variety of methods that are all called in main and other methods to perform a specific function. 

**Testing:**
- The shoppingCart class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already existed and contained the information necessary for that specific method. 

### Store Class

**Functionality/Relationship to other classes:**
- This class mainly creates a store object that includes a store name and an array list of items.
- One of the methods, storeRevenue calculates the revenue of the store every time an item is bought from the store and updates this information in a text file called storeRevenue.txt.
- It also contains a variety of smaller methods such as deleteItem, addItem, etc that are called on in the Seller class. 

**Testing:**
- The store class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already existed and contained the information necessary for that specific method.

### Item Class

**Functionality/Relationship to other classes:**
- The Item class mainly creates an item object that includes all the specific parameters it requires such as the store name, the item name, the item description, the item availability (quantity), and the item price, as well as the caressing getter and setter methods. The item class can also be thought of as the product. 
- It also includes methods that create a formatted version of the strong to file, with specific separators that allow us to split the line in later classes for added ease. 
- This class is used in a variety of other classes such as store, seller, customer, etc. 

**Testing:**
- The item class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already existed and contained the information necessary for that specific method. 


### Account Class

**Functionality/Relationship to other classes:**
- Account.java focuses on the creation of the actual account when the customer/seller login and works closely with the AccountsFile.java class. It not only contains methods that allow the user to login, but allows for methods that create, delete, and edit accounts. These methods usually iterate through the list of accounts. 
- In the createAccount method, it makes sure that the email is not already in use and adds the appropriate email and password to the list to store the account. In the deleteAccount method, it looks for the email, and deletes the whole account, making sure that if a seller is deleting their account it also deletes their corresponding stores and items in the store. The editAccount is similar in the sense where it checks if the new values are the same and then replaces the old account with the new one. 


**Testing:**
- The account class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already existed and contained the information necessary for that specific method. 

### AccountsFile Class

**Functionality/Relationship to other classes:**
- AccountsFile.java works with the Account.java class and contains the readAccounts, writeAccounts, and add Account methods. The first reads all of the logins from the login CSVfile (not user inputed, the file is defined here), in the String Array, while the writeAccount writes all logins to the file. The addAccount then appends a login to the end of the login file. 

**Testing:**
- The accountFile class has some methods that were checked in Tester.java, while the others that related to files, were checked manually and through the code in Main.java. The file check methods were checked with separate main methods depending on the situation and were done as if the file already exists and contains the information necessary for that specific method. 
