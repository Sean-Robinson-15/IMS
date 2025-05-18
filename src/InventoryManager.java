import java.util.*;

public class InventoryManager {
    private Map<String, Product> inventory = new HashMap<>();
        

    public void addItem(String ID, String name, int quantity, double price) {
        inventory.put(ID, new Product(ID, name, quantity, price));
        System.out.println("Item added: " + ID + " " + name);
    }



}