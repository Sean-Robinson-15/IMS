package IMS.Inventory;
import IMS.Product.*;

import java.util.*;

public class InventoryManager {
    private Map<String, Product> inventory = new HashMap<>();

    public void addItem(String ID, String name, int quantity, double price) {
        inventory.put(ID, new Product(ID, name, quantity, price));
        System.out.println("Item added: " + ID + " " + name);
    }
    public void testItems() {
        String ID = "69";
        String name = "test";
        int quantity = 1;
        double price = 5.5;

        String ID1 = "420";
        String name1 = "test";
        int quantity1 = 1;
        double price1 = 5.6;

        String ID2 = "71";
        String name2 = "test";
        int quantity2 = 1;
        double price2 = 5.6;

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