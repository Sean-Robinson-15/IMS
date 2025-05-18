import java.util.*;

public class InventoryManager {
    private Map<String, Product> inventory = new HashMap<>();

    public void addItem(String ID, String name, int quantity, double price) {
        inventory.put(ID, new Product(ID, name, quantity, price));
        System.out.println("Item added: " + ID + " " + name);
    }

    public void removeItem(String ID) {
        if (inventory.remove(ID) != null) {
            System.out.println("Item Removed : " + ID);
        } else {
            System.out.println("Error! Product Code (" + ID +") doesnt exist.");
        }
    }

    public List<Product> getAllItems() {
        return new ArrayList<>(inventory.values());
    }

}