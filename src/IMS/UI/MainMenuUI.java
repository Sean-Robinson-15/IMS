package IMS.UI;
import IMS.Inventory.InventoryManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainMenuUI extends JFrame {

    private DefaultTableModel inventoryTable;

    public MainMenuUI(InventoryManager manager){
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(-900, 500);


        JPanel menuPanel = new JPanel(new GridLayout());

        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        JButton ordersButton = new JButton("Orders");
        JButton suppliersButton = new JButton("Suppliers");

        menuPanel.add(homeButton);
        menuPanel.add(inventoryButton);
        menuPanel.add(ordersButton);
        menuPanel.add(suppliersButton);

        add(menuPanel, BorderLayout.SOUTH);

        homeButton.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(menuPanel, BorderLayout.SOUTH);
            this.repaint();
            this.revalidate();
        });

        inventoryButton.addActionListener(e -> {
            InventoryUI inventoryUI = new InventoryUI(manager);
            if (!checkUI("InventoryUI")) {
                add(inventoryUI);
                this.repaint();
                this.revalidate();
            }
        });
    }

    private boolean checkUI(String string) {
        for (Component frame : this.getContentPane().getComponents()) {
            System.out.println(frame.getName());
            if (frame.getName() == string) {
                return true;
            }
        }
        return false;
    }
}
