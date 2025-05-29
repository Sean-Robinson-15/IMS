package IMS.Inventory;
import IMS.Orders.Transaction;
import IMS.Users.Customer;
import IMS.Users.Supplier;
import IMS.Users.User;
import IMS.Products.*;

import java.util.*;

public class InventoryManager {
    private final Map<String, Product> inventory = new HashMap<>();
    private final Map<String, Product> basket = new HashMap<>();
    private final Map<String, Transaction> transactions = new HashMap<>();
    private final Map<String, User> users = new HashMap<>();


    public String addItem(String ID, String name, String quantityStr, String priceStr) {
        HashMap<String, String> inputList = new HashMap<>(Map.of("ID",ID, "name",name, "quantityStr",quantityStr, "priceStr",priceStr));
        if (!confirmInputs(inputList).isEmpty()) {
            return confirmInputs(inputList);
        }

        ArrayList<String> IDs = getAllIDs();

        int quantity = Integer.parseInt(quantityStr);
        double price = Double.parseDouble(priceStr);
        if (!IDs.contains(ID)) {
            inventory.put(ID, new Product(ID, name, quantity, price));
            return("Item added: " + ID + " " + name);
        }

        return "ID "+ID+" arleady exists";
    }

    public String confirmInputs(HashMap<String, String> inputs) {
        ArrayList<String> emptyInputs = new ArrayList<>();
        for (String key : inputs.keySet()) {
            if (inputs.get(key).isEmpty()) {
                emptyInputs.add(key);
            }
        }
            if (!emptyInputs.isEmpty()) {
                return "Please fill in all fields. Empty fields: " + emptyInputs;
            }
        return "";
    }



    public String addToBasket( String ID, String name, String quantityStr, double price) {
        if ((ID.isEmpty())) {
            return "No Product Entered. Please try again.";
        }
        if (quantityStr.isEmpty()) {
            return "Invalid Quantity Entered. Please try again.";
        }
        int quantity = Integer.parseInt(quantityStr);
        if (quantity > getQuantity(ID)) {
            return "quantity greater than available. Please try again. \n";
        }

        //change to use map/key combo on inventory
        for (Product prod : getAllItems()) {
            if (prod.getID().equals(ID)) {
                if (basket.containsKey(ID)) {
                    int currentBasketQuantity = getBasketQuantity(ID);
                    basket.put(ID, new Product(ID, name, currentBasketQuantity + quantity, price));
                } else {
                    basket.put(ID, new Product(ID, name, quantity, price));
                }
                System.out.println("Item added to basket: " + ID + " " + name);
                updateItem(ID, prod.getQuantity() - quantity);
            }
        }
        return name + " added to basket. \n";
    }
    public void testItems() {
        randomize(inventory, 10);
        ArrayList<Product> inventoryArray = getAllItems();
        Product prod = inventoryArray.get((int)(Math.random()*inventoryArray.size()));
        int quantity = (int)(Math.random()*prod.getQuantity());
        addToBasket(prod.getID(), prod.getName(), String.valueOf(quantity), prod.getPrice());
        users.put("C001", new Customer("C001", "Customer 1", "101 Made Up Lane" ,"Customer1@gmail.com" ));
        users.put("S001", new Supplier("S001", "Supplier 1", "202 Not Real Road" , "Supplier1@gmail.com",  "Sales" ));

    }
    public void randomize(Map<String, Product> map, Integer num) {
        String[] randomStr = {"rD9av","R11Xt","E6qa2","BOnbh","2oFxY","Xt31Q","eMPF2","bJJKZ","R870x","m3cwL","ZXlpU","m4hen","bhj7N","JNP3J","MtZ5P","EQOWO","uo9Vb","VeUIF","JTakW","SKRrl","s6NFt","FUvy7","Ponqd","poid1","Y1V","5","n7MgC","O2OfQ","DSgzo","wmoUb","Y64Fb"};
        for (int i = 0; i < num; i++)
        {
            String ID = String.format("BNU%03d", (int)(Math.random()*1000));
            String name = randomStr[(int)(Math.random()*randomStr.length)];
            int quantity = (int)(Math.random()*50) +1;
            double price = (double) Math.round(Math.random()*1000)/100;
            map.put(ID, new Product(ID, name, quantity, price));
        }
    }


    public void updateItem(String ID, int quantity) {
        updateItem(ID, "", Integer.toString(quantity), "");
    }


    public String updateItem(String ID, String name, String quantityStr, String priceStr) {
        Product product = inventory.get(ID);
        if (product != null) {
            if (!quantityStr.isEmpty()) {
                int quantity = Integer.parseInt(quantityStr);
                product.setQuantity(quantity);
            }
            if (!priceStr.isEmpty()) {
                double price = Double.parseDouble(priceStr);
                product.setPrice(price);
            }
            if (!name.isEmpty()) {
                product.setName(name);
            }
            return ("Item Updated: " + ID + " " + name);
        }
        return ("Error: Product Code (" + ID +") doesnt exist.");
    }

    public String updateUser(String ID, String name, String address, String email) {
        User user = users.get(ID);
        if (user != null) {
            if (!address.isEmpty()) {
                user.setAddress(address);
            }
            if (!email.isEmpty()) {
                user.setEmail(email);
            }
            if (!name.isEmpty()) {
                user.setName(name);
            }
            return ("Item Updated: " + ID + " " + name);
        }
        return ("Error: Product Code (" + ID +") doesnt exist.");
    }
    public String updateUser(String ID, String name, String address, String email, String department) {
        updateUser(ID, name, address, email);
        Supplier user = (Supplier)users.get(ID);
        if ( user != null && !department.isEmpty()) {
            user.setDepartment(department);
        }
        return ("Error: Product Code (" + ID +") doesnt exist.");
    }


    public String removeItem(String ID) {
        if (inventory.remove(ID) != null) {
            return ("Item Removed : " + ID);
        } else {
            return ("Error: Product Code (" + ID +") doesnt exist.");
        }
    }
    public String removeUser(String ID) {
        if (users.remove(ID) != null) {
            return "Item Removed : " + ID;
        } else {
            return "Error! User Code(" + ID +") doesnt exist.";
        }
    }

    public ArrayList<Product> getAllItems() {
        return new ArrayList<>(inventory.values());
    }

    public ArrayList<Product> getBasket() {
        return new ArrayList<>(basket.values());
    }
    public boolean isBasketEmpty() {
        return basket.isEmpty();
    }

    public ArrayList<String> getAllIDs() {
        ArrayList<String> IDs = new ArrayList<String>();
        for (Product p : inventory.values()) {
            IDs.add(p.getID());
        }
        return IDs;
    }

    public ArrayList<String> getAllCustomerIDs() {
        ArrayList<String> IDs = new ArrayList<String>();
        for (User p : users.values()) {
            if (p instanceof Customer) {
                IDs.add(p.getID());
            }
        }
        return IDs;
    }
    public ArrayList<String> getAllSupplierIDs() {
        ArrayList<String> IDs = new ArrayList<String>();
        for (User p : users.values()) {
            if (p instanceof Supplier) {
                IDs.add(p.getID());
            }
        }
        return IDs;
    }

    public double getPrice(String ID){
        for (Product item : getAllItems()) {
            if (item.getID().equals(ID)) {
                return item.getPrice();
            }
        }
        return -1;
    }
    public String getName(String ID){
        for (Product item : getAllItems()) {
            if (item.getID().equals(ID)) {
                return item.getName();
            }
        }
        return null;
    }
    public Integer getQuantity(String ID){
        for (Product item : getAllItems()) {
            if (item.getID().equals(ID)) {
                return item.getQuantity();
            }
        }
        return null;
    }
    public Integer getBasketQuantity(String ID){
        for (Product item : getBasket()) {
            if (item.getID().equals(ID)) {
                return item.getQuantity();
            }
        }
        return null;
    }
    public void resetQuantity(String ID, int baskQuan){
            int currentQuan = getQuantity(ID);
            updateItem(ID, currentQuan + baskQuan);
    }

    public void clearBasket() {
        for (Product item : getBasket()) {
            String ID = item.getID();
            resetQuantity(ID, item.getQuantity());
        }
        basket.clear();
    }

    public String checkoutBasket(String userID) {
        String orderID = "ORDER1";// {RETURN} to change to order number creator
        double totalPrice = 0.0;

        if (isBasketEmpty() || userID.isEmpty()) {
            return "Basket or Customer ID is empty. Please try again.";
        }

        // {RETURN} to write a function to contain these
        Transaction transaction = new Transaction(orderID, userID, getBasket());
        transactions.put(orderID, transaction);

        basket.clear();
        System.out.println("Order Completed!");
        System.out.println("Order ID: " + orderID);
        System.out.println("Stakeholder ID: " + userID);
        System.out.println("Total Price: " + totalPrice);
        System.out.println("Items in Order: ");
        for (Product item : transaction.getProducts()) {
            System.out.println(item.getID() + " " + item.getName() + " " + item.getQuantity() + " " + item.getPrice());
            totalPrice += item.getPrice() * item.getQuantity();
        }

        return orderID+" Checked out for £"+totalPrice;
    }

    public String removeItemFromBasket(String ID) {
        try {
            int basketQuantity = getBasketQuantity(ID);
            resetQuantity(ID, basketQuantity);
            basket.remove(ID);
            return "Item "+ID+" Removed";
        } catch (NullPointerException e) {
            return "Item "+ID+" Doesnt Exist in basket";
        }
    }

    public void removeItemFromBasket(String ID, int Quantity) {
        int basketQuantity = getBasketQuantity(ID);
        resetQuantity(ID, Quantity);
        basket.remove(ID);
    }

    public ArrayList<Supplier> getSuppliers() {
        ArrayList<User> usersList = new ArrayList<>(users.values());
        ArrayList<Supplier> suppliersList = new ArrayList<>();
         for (User i : usersList) {
             if (i instanceof Supplier) {
                 System.out.println(i.getID());
                 suppliersList.add((Supplier) i);
             }
         };
         System.out.println(suppliersList.size());
        return suppliersList;
    }
    public ArrayList<Customer> getCustomers() {
        ArrayList<User> usersList = new ArrayList<>(users.values());
        ArrayList<Customer> CustomersList = new ArrayList<>();
         for (User i : usersList) {
             if (i instanceof Customer) {
                 System.out.println(i.getID());
                 CustomersList.add((Customer) i);
             }
         };
         System.out.println(CustomersList.size());
        return CustomersList;
    }

    public String addSupplier(String ID, String name, String address, String email, String department) {
        ID=ID.toUpperCase();
        if (users.containsKey(ID)) {
            return "Supplier with ID " + ID + " already exists.";
        }
        if (ID.isEmpty() || name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            return "Please enter all necessary fields.";
        }
        users.put(ID, new Supplier(ID, name, address, email, department));
        return "Supplier " + name + " added with ID " + ID;
    }

    public String addCustomer(String ID, String name, String address, String email) {
        ID=ID.toUpperCase();
        if (users.containsKey(ID)) {
            return "Customer with ID " + ID + " already exists.";
        }
        if (name.isEmpty() || address.isEmpty() || email.isEmpty()) {
            return "Please enter all necessary fields.";
        }
        users.put(ID, new Customer(ID, name, address, email));
        return "Customer " + name + " added with ID " + ID;
    }

}