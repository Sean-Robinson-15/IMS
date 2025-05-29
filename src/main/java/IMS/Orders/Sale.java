package IMS.Orders;

import IMS.Products.Product;

import java.util.ArrayList;

public class Sale extends Transaction{
    public Sale(String orderID, String userID, ArrayList<Product> products) {
        super(orderID, userID, products);
    }
}
