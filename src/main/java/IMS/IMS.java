package IMS;
import IMS.Inventory.*;
import IMS.UI.UIManager;

import javax.swing.*;

public class IMS {

    public static void main(String[] args) {

        TransactionManager transactionManager = new TransactionManager();
        UserManager userManager = new UserManager();
        ProductManager productManager = new ProductManager();
        BasketManager basketManager = new BasketManager(productManager);
        InventoryManager manager = new InventoryManager(productManager, basketManager, userManager, transactionManager);

//        InventoryUI main = new InventoryUI(manager);
        UIManager main = new UIManager(manager, transactionManager, basketManager, userManager,productManager);
        manager.testItems();
        SwingUtilities.invokeLater(() -> main.setVisible(true));
    }
}

