package IMS;
import IMS.Inventory.InventoryManager;
import IMS.Inventory.ProductManager;
import IMS.Inventory.TransactionManager;
import IMS.Inventory.UserManager;
import IMS.UI.UIManager;

import javax.swing.*;

public class IMS {

    public static void main(String[] args) {

        TransactionManager transactionManager = new TransactionManager();
        UserManager userManager = new UserManager();
        ProductManager productManager = new ProductManager();
        InventoryManager manager = new InventoryManager(productManager,userManager,transactionManager);

//        InventoryUI main = new InventoryUI(manager);
        UIManager main = new UIManager(manager, transactionManager, userManager,productManager);
        manager.testItems();
        SwingUtilities.invokeLater(() -> main.setVisible(true));
    }
}

