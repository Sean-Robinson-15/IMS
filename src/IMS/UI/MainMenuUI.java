package IMS.UI;
import IMS.Inventory.InventoryManager;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainMenuUI extends JFrame {

    private DefaultTableModel inventoryTable;

    public MainMenuUI(InventoryManager manager){
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(900, 500);


        JPanel menuPanel = new JPanel(new GridLayout());

        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        JButton ordersButton = new JButton("Orders");
        JButton suppliersButton = new JButton("Suppliers");

        menuPanel.add(homeButton);
        menuPanel.add(inventoryButton);
        menuPanel.add(ordersButton);
        menuPanel.add(suppliersButton);

        add(menuPanel, BorderLayout.NORTH);

        homeButton.addActionListener(e -> {
            this.getContentPane().removeAll();
            this.add(menuPanel, BorderLayout.NORTH);
            this.repaint();
            this.revalidate();
        });

        inventoryButton.addActionListener(e -> {
            InventoryUI inventoryUI = new InventoryUI(manager);
            if (!checkUI("InventoryUI")) {
                removeUI("InventoryUI");
                add(inventoryUI);
                this.repaint();
                this.revalidate();
            }
        });

        ordersButton.addActionListener(e -> {
            OrdersUI ordersUI = new OrdersUI(manager);
            if (!checkUI("OrdersUI")) {
                removeUI("OrdersUI");
                add(ordersUI);
                this.repaint();
                this.revalidate();
            }
        });
    }

    private boolean checkUI(String string) {
        for (Component frame : this.getContentPane().getComponents()) {
            System.out.println(frame);
            if (frame.getName() == string) {
                return true;
            }
        }
        return false;
    }
    private void removeUI(String string) {
        for (Component frame : this.getContentPane().getComponents()) {
            System.out.println(frame);
            if (frame.getName() != string && frame.getName() !=null) {
                this.remove(frame);
            }
        }
    }
}
