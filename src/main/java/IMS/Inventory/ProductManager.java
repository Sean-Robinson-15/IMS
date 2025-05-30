package IMS.Inventory;

import IMS.UI.InputValidator;
import IMS.Products.Product;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class ProductManager {
    private final Map<String, Product> inventory = new HashMap<>();
    private final Map<String, Product> inTransit = new HashMap<>();
    private final InputValidator validator = new InputValidator();
    private final int LOWSTOCKTHRESHOLD = InventoryManager.DEFAULT_LOW_STOCK_THRESHOLD;

    public String addInventoryItem(String ID, String name, String quantityStr, String priceStr) {
        ID=ID.toUpperCase();
        HashMap<String, String> inputList = new HashMap<>(Map.of("ID", ID, "name", name, "quantityStr", quantityStr, "priceStr", priceStr));
        String validationResult = validator.confirmInputs(inputList);
        if (validationResult != "") {
            return "Error: "+validationResult;
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

    public String updateItem(String ID, String name, String quantityStr, String priceStr) {
        ID=ID.toUpperCase();
        Product product = inventory.get(ID);
        if (product != null) {
            String validationResult = validator.validateInt(quantityStr, "Quantity");
            if (validationResult != "") return validationResult;
            int quantity = Integer.parseInt(quantityStr);
            product.setQuantity(quantity);

            validationResult = validator.validateDouble(priceStr, "Price");
            if (validationResult != "") return validationResult;
            double price = Double.parseDouble(priceStr);
            product.setPrice(price);

            if (!name.isEmpty()) {
                product.setName(name);
            }
            return ("Item Updated: " + ID + " " + name);
        }
        return ("Error: Product Code [" + ID +"] doesnt exist.");
    }

    public void updateItem(String ID, int quantity) {
        updateItem(ID, "", Integer.toString(quantity), "");
    }


    public String removeItem(String ID) {
        ID=ID.toUpperCase();
        if (ID.isEmpty()) return ("Error: Product Code is empty");
        if (inventory.remove(ID) != null) {
            return ("Item Removed : " + ID);
        } else {
            return ("Error: Product Code [" + ID +"] doesnt exist.");
        }
    }

    public boolean productExists(String ID) {
        ID=ID.toUpperCase();
        return inventory.containsKey(ID);
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

    public double getPrice(String ID){
        ID=ID.toUpperCase();
        return inventory.get(ID).getPrice();
    }
    public String getName(String ID){
        ID=ID.toUpperCase();
        return inventory.get(ID).getName();
    }
    public Integer getQuantity(String ID){
        ID=ID.toUpperCase();
        Product prod = inventory.get(ID);
        return prod.getQuantity();
    }

    public ArrayList<Product> getInTransit() {
        return new ArrayList<>(inTransit.values());
    }

    public void addInTransitItem(Product product) {
        inTransit.put(product.getID(), product);
    }

    public void randomize(int num)  {
        String[] randomStr = {"rD9av", "R11Xt", "E6qa2", "BOnbh", "2oFxY", "Xt31Q", "eMPF2", "bJJKZ", "R870x", "m3cwL"};
        for (int i = 0; i < num; i++) {
            String ID = String.format("BNU%03d", (int)(Math.random()*1000));
            String name = randomStr[(int)(Math.random()*randomStr.length)];
            int quantity = (int)(Math.random()*50) + 1;
            double price = (double) Math.round(Math.random()*1000)/100;
            inventory.put(ID, new Product(ID, name, quantity, price));
        }
    }

    public int getLowStockThreshold() {
        return LOWSTOCKTHRESHOLD;
    }


}
