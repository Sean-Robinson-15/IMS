package IMS;
import IMS.UI.InventoryUI;

import javax.swing.*;

public class IMS {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryUI().setVisible(true));
    }
}

