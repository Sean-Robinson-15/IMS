package IMS.UI;
import IMS.Inventory.InventoryManager;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainMenuUI extends JFrame {
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
            getContentPane().removeAll();
            add(menuPanel, BorderLayout.NORTH);
            repaint();
            revalidate();
        });

        inventoryButton.addActionListener(e -> {
            InventoryUI inventoryUI = new InventoryUI(manager);
            switchPanel(inventoryUI, menuPanel);
        });

        ordersButton.addActionListener(e -> {
            OrdersUI ordersUI = new OrdersUI(manager);
            switchPanel(ordersUI, menuPanel);
        });

        suppliersButton.addActionListener(e -> {
            SuppliersUI suppliersUI = new SuppliersUI(manager);
            switchPanel(suppliersUI, menuPanel);
        });
    }

    private void switchPanel(JPanel panel, JPanel menuPanel) {
        String panelName = panel.getClass().getSimpleName();
        panel.setName(panelName);
        if (!checkUI(panelName)) {
            removeUIExcept(panelName);
            add(panel);
            add(menuPanel, BorderLayout.NORTH);
            repaint();
            revalidate();
        }
    }

    private boolean checkUI(String string) {
        for (Component comp : getContentPane().getComponents()) {
            System.out.println(comp.getClass().getSimpleName());
            if (comp.getName().equals(string)) {
                return true;
            }
        }
        return false;
    }
    private void removeUIExcept(String string) {
        for (Component comp : getContentPane().getComponents()) {
            System.out.println(comp.getClass().getSimpleName());
            if (!Objects.equals(comp.getName(), string) && !Objects.equals(comp.getName(), "JPanel")) {
                this.remove(comp);
            }
        }
    }
}
