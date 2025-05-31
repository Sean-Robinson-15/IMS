package IMS.Alerts;

import IMS.Products.Product;

import javax.swing.*;
import java.util.ArrayList;

public class Alerts {
    public static void sendStockAlert(ArrayList<Product> items) {
        String message;
        if (items.isEmpty()) {
            return;
        }

        if (items.size() == 1) {
             message = "Stock Alert: " + items.getFirst().getID() + " is running low. \n";

        } else {
            ArrayList <String> itemIDs = new ArrayList<>();
            for (Product item : items) {
                itemIDs.add(item.getID());
            }
            message = "Stock Alert: " + itemIDs + " are running low. \n";
        }

        JFrame jframe = new JFrame();
        JOptionPane.showMessageDialog(jframe,
                message,
                "Low Stock Warning",
                JOptionPane.WARNING_MESSAGE);
        //Code to Send email
    }
}
