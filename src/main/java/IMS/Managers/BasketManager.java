package IMS.Managers;

import IMS.Orders.Purchase;
import IMS.Products.Product;
import IMS.Orders.Sale;
import IMS.Orders.Transaction;

import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class BasketManager {
    private final Map<String, Product> basket = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    private final ProductManager productManager;
    public String currentUser = "";

    public BasketManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public String addToBasket( String userID, String ID, String name, String quantityStr, double price) {
        Product prod = null;
        if (!currentUser.equals(userID) && !currentUser.isEmpty()) {
            return "Error: User has changed";
        }


        if (userID.isEmpty()) {
            return "User ID is empty. Please try again.";
        }
        if (userID.charAt(0) != 'C' && userID.charAt(0) != 'c' && userID.charAt(0) != 'S' && userID.charAt(0) != 's') {
            return "User ID must start with C (Customer) or S (Supplier). Please try again.";
        }
        currentUser = userID;
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

        if (isCustomer(currentUser)) {
            quantity = quantity*-1;
        }

        if (quantity > productManager.getQuantity(ID) && isCustomer(currentUser)) {
            return "quantity greater than available. Please try again. \n";
        }

        for (Product p : productManager.getAllItems()) {
            if (p.getID().equals(ID)) {
                prod = p;
            }
        }
        //change to use map/key combo on inventory
        if (prod == null) {
            return "Product does not exist. Please try again. \n";
        }

        if (prod.getID().equals(ID)) {
            if (basket.containsKey(ID)) {
                int currentBasketQuantity = getBasketQuantity(ID);
                basket.put(ID, new Product(ID, name, currentBasketQuantity + quantity, price));
            } else {

                basket.put(ID, new Product(ID, name, quantity, price));
            }
            productManager.updateItem(ID, prod.getQuantity() + (quantity));
        }

        return ID + " added to basket. \n";
    }

    public boolean isCustomer(String userID) {
        return userID.toUpperCase().contains("C");

    }

    public String removeItemFromBasket(String ID) {
        try {
            int basketQuantity = getBasketQuantity(ID);
            resetQuantity(ID, basketQuantity);
            basket.remove(ID);
            if (basket.isEmpty()) {
                currentUser = "";
            }
            return "Item "+ID+" Removed";
        } catch (NullPointerException e) {
            return "Item "+ID+" Doesnt Exist in basket";
        }
    }

    public String removeItemFromBasket(String ID, String quantity) {
        try {
            Product basketProduct = basket.get(ID);
            int removeQuantity;

            try {
                removeQuantity = Integer.parseInt(quantity)*-1;
                if (removeQuantity >= 0) { //yes its the wrong way round, because its a negative number
                    return "Quantity must be positive. Please try again.";
                }

            } catch (NumberFormatException e) {
                return "Quantity must be an integer. Please try again.";
            }

            if (basketProduct.getQuantity() > removeQuantity) {
                System.out.println("Basket quantity: " + basketProduct.getQuantity());
                System.out.println("Remove quantity: " + removeQuantity);
                return "Removal quantity too high.";
            }

            resetQuantity(ID, removeQuantity);

            if (basket.isEmpty()) {
                currentUser = "";
            }

            if (basketProduct.getQuantity() == removeQuantity) {
                basket.remove(ID);
                return "Item "+ID+" Removed \n";
            } else {
                basketProduct.setQuantity(basketProduct.getQuantity() - removeQuantity);
                return "Item "+ID+" Quantity reduced by "+quantity+" \n";
            }



        } catch (NullPointerException e) {
            return "Item "+ID+" Doesnt Exist in basket";
        }
    }

    public void emptyBasket() {
        for (Product item : getBasket()) {
            int basketQuantity = item.getQuantity();
            String ID = item.getID();
            resetQuantity(ID, basketQuantity);
        }
        currentUser = "";
        basket.clear();
    }

    public void checkoutBasket() {
        if (!isCustomer(currentUser)) {
            emptyBasket();
            return;
        }
        basket.clear();
        currentUser = "";
    }

    public Integer getBasketQuantity(String ID){
        return basket.get(ID).getQuantity();
    }

    public void resetQuantity(String ID, int basketQuantity){
        int currentQuan = productManager.getQuantity(ID);
        productManager.updateItem(ID, currentQuan - basketQuantity);
    }

    public ArrayList<Product> getBasket() {
        return new ArrayList<>(basket.values());
    }

    public boolean isBasketEmpty() {
        return basket.isEmpty();
    }

    public Transaction createTransaction(String orderID) {
        if (isCustomer(currentUser)) {
            for (Product item : getBasket()) {
                int basketQuantity = item.getQuantity();
                basketQuantity *= -1;
                item.setQuantity(basketQuantity);
            }
            return new Sale(orderID, currentUser, getBasket());
        }
        return new Purchase(orderID, currentUser, getBasket());
    }

}
