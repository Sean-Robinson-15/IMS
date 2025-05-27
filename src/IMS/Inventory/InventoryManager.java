package IMS.Inventory;
import IMS.Products.*;

import java.util.*;

public class InventoryManager {
    private final Map<String, Product> inventory = new HashMap<>();
    private final Map<String, Product> basket = new HashMap<>();

    public void addItem(String ID, String name, int quantity, double price) {
        inventory.put(ID, new Product(ID, name, quantity, price));
        System.out.println("Item added: " + ID + " " + name);
    }
    public void addToBasket( String ID, String name, int quantity, double price) {
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
    }
    public void testItems() {
        randomize(inventory, 10);
        ArrayList<Product> inventoryArray = getAllItems();
        Product prod = inventoryArray.get((int)(Math.random()*inventoryArray.size()));
        addToBasket(prod.getID(), prod.getName(), (int)(Math.random()*prod.getQuantity()), prod.getPrice());

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
        Product product = inventory.get(ID);
        if (product != null) {
            product.setQuantity(quantity);
            System.out.println("Quantity updated to " + quantity);
        } else {
            System.out.println("Quantity not updated for " + ID + ". \n is the ID correct?");
        }
    }


    public void updateItem(String ID, double price) {
        Product product = inventory.get(ID);
        if (product != null) {
            product.setPrice(price);
            System.out.println("Price updated to " + price);
        } else {
            System.out.println("Price not updated for " + ID + ". \n is the ID correct?");
        }
    }

    public void updateItem(String ID, int quantity, double price) {
        Product product = inventory.get(ID);
        if (product != null) {
            product.setPrice(price);
            product.setQuantity(quantity);
            System.out.println("Price and Quantity updated to " + price + ", " + quantity);
        } else {
            System.out.println("Price and Quantity not updated for " + ID + ". \n is the ID correct?");
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

    public ArrayList<Product> getBasket() {
        return new ArrayList<>(basket.values());
    }

    public ArrayList<String> getAllIDs() {
        ArrayList<String> IDs = new ArrayList<String>();
        for (Product p : inventory.values()) {
            IDs.add(p.getID());
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
    public void removeItemFromBasket(String ID) {
        int basketQuantity = getBasketQuantity(ID);
        resetQuantity(ID, basketQuantity);
        basket.remove(ID);
    }
    public void removeItemFromBasket(String ID, int Quantity) {
        int basketQuantity = getBasketQuantity(ID);
        resetQuantity(ID, Quantity);
        basket.remove(ID);
    }

}