package IMS.Inventory;
import IMS.Product.*;

import java.util.*;

public class InventoryManager {
    private final Map<String, Product> inventory = new HashMap<>();

    public void addItem(String ID, String name, int quantity, double price) {
        inventory.put(ID, new Product(ID, name, quantity, price));
        System.out.println("Item added: " + ID + " " + name);
    }
    public void testItems() {
        String ID = "BNU69";
        String name = "BIG Machine";
        int quantity = 7;
        double price = 3.14;

        String ID1 = "BNU420";
        String name1 = "Even Bigger Machine";
        int quantity1 = 14;
        double price1 = 32;

        String ID2 = "BNU71";
        String name2 = "Machiney McMachineFace";
        int quantity2 = 21;
        double price2 = 100.54;

        inventory.put(ID, new Product(ID, name, quantity, price));
        inventory.put(ID1, new Product(ID1, name1, quantity1, price1));
        inventory.put(ID2, new Product(ID2, name2, quantity2, price2));
        System.out.println("Item added: " + ID + " " + name);
    }

    public void updateItem(String ID, int quantity) {
        Product product = inventory.get(ID);
        if (product != null) {
            product.setQuantity(quantity);
            System.out.println("Quantity updated to " + quantity);
        } else {
            System.out.println("Quantity Not updated for " + ID + ". \n is the ID correct?");
        }
    }

    public void removeItem(String ID) {
        if (inventory.remove(ID) != null) {
            System.out.println("Item Removed : " + ID);
        } else {
            System.out.println("Error! IMS.Product.Product Code (" + ID +") doesnt exist.");
        }
    }

    public ArrayList<Product> getAllItems() {
        return new ArrayList<>(inventory.values());
    }

    public ArrayList<String> getAllIDs() {
        ArrayList<String> IDs = new ArrayList<String>();
        for (Product p : inventory.values()) {
            IDs.add(p.getID());
        }
        return IDs;
    }

}