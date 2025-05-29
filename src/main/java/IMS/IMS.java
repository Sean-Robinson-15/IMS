package IMS;
import IMS.Inventory.InventoryManager;
import IMS.UI.UIManager;

import javax.swing.*;

public class IMS {

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
//        InventoryUI main = new InventoryUI(manager);
        UIManager main = new UIManager(manager);
        manager.testItems();
        SwingUtilities.invokeLater(() -> main.setVisible(true));
    }
}

