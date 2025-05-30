package IMS.Inventory;

import IMS.Products.Product;
import IMS.Orders.Sale;
import IMS.Orders.Transaction;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class BasketManager {
    private final Map<String, Product> basket = new HashMap<>();
    private final ProductManager productManager;

    public BasketManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public String addToBasket( String ID, String name, String quantityStr, double price) {
        ID=ID.toUpperCase();
        Product prod = null;
        if ((ID.isEmpty())) {
            return "No Product Entered. Please try again.";
        }
        if (quantityStr.isEmpty()) {
            return "Invalid Quantity Entered. Please try again.";
        }
        if (name.isEmpty()) {
            return "Invalid Name Entered. Please try again.";
        }
        int quantity = Integer.parseInt(quantityStr);
        if (quantity > productManager.getQuantity(ID)) {
            return "quantity greater than available. Please try again. \n";
        }

        for (Product p : productManager.getAllItems()) {
            if (p.getID().equals(ID)) {
                prod = p;
            }
        }
        //change to use map/key combo on inventory
        assert prod != null;
        if (prod.getID().equals(ID)) {
            if (basket.containsKey(ID)) {
                int currentBasketQuantity = getBasketQuantity(ID);
                basket.put(ID, new Product(ID, name, currentBasketQuantity + quantity, price));
            } else {
                basket.put(ID, new Product(ID, name, quantity, price));
            }
            productManager.updateItem(ID, prod.getQuantity() - quantity);
        }
        return ID + " added to basket. \n";
    }

    public String removeItemFromBasket(String ID) {
        ID=ID.toUpperCase();
        try {
            int basketQuantity = getBasketQuantity(ID);
            resetQuantity(ID, basketQuantity);
            basket.remove(ID);
            return "Item "+ID+" Removed";
        } catch (NullPointerException e) {
            return "Item "+ID+" Doesnt Exist in basket";
        }
    }

    public void clearBasket() {
        for (Product item : getBasket()) {
            String ID = item.getID();
            resetQuantity(ID, item.getQuantity());
        }
        basket.clear();
    }

    public Integer getBasketQuantity(String ID){
        ID=ID.toUpperCase();
        return basket.get(ID).getQuantity();
    }

    public void resetQuantity(String ID, int baskQuantity){
        ID=ID.toUpperCase();
        int currentQuan = productManager.getQuantity(ID);
        productManager.updateItem(ID, currentQuan + baskQuantity);
    }

    public ArrayList<Product> getBasket() {
        return new ArrayList<>(basket.values());
    }

    public boolean isBasketEmpty() {
        return basket.isEmpty();
    }

    public Transaction createSale(String orderID, String userID) {
        return new Sale(orderID, userID, getBasket());
    }



}
