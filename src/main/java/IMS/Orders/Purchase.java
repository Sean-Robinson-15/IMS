package IMS.Orders;

import IMS.Products.Product;

import java.util.ArrayList;

public class Purchase extends Transaction{
    public Purchase(String orderID, String userID, ArrayList<Product> products) {
        super(orderID, userID, products);
    }
}
