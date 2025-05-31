package IMS;
import IMS.UI.UIManager;

import javax.swing.*;

public class IMS {

    public static void main(String[] args) {


        UIManager main = new UIManager();
        SwingUtilities.invokeLater(() -> main.setVisible(true));
    }
}

