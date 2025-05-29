package IMS.Orders;

import IMS.Products.Product;

import java.util.ArrayList;

public class Transaction {
    private final String orderID;
    private final String userID;
    private final ArrayList<Product> products;

    public Transaction(String orderID, String userID, ArrayList<Product> products) {
        this.orderID = orderID;
        this.userID = userID;
        this.products = products;
    }

    public double getTotalCost() {
        double totalCost = 0;
        for (Product item : products) {
            double cost = item.getPrice() * item.getQuantity();
            if (cost < 0) {
                return -1;
            }
            totalCost += cost;
        }
        return totalCost;
    }

    public int getTotalProducts() {
        return products.size();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
