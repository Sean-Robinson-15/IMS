package IMS;
import IMS.Inventory.InventoryManager;
import IMS.UI.InventoryUI;
import IMS.UI.MainMenuUI;

import javax.swing.*;

public class IMS {

    public static void main(String[] args) {
        InventoryManager manager = new InventoryManager();
//        InventoryUI main = new InventoryUI(manager);
        MainMenuUI main = new MainMenuUI(manager);
        SwingUtilities.invokeLater(() -> main.setVisible(true));
    }
}

