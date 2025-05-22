package IMS.UI;
import IMS.Inventory.InventoryManager;
import javax.swing.*;
import java.awt.*;


public class UIManager extends JFrame {
    private final InventoryManager manager;
    private final JPanel menuPanel;


    public UIManager(InventoryManager manager) {
        this.manager = manager;
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690, 420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(900, 500);
        menuPanel = createMainMenuUI();
        add(menuPanel, BorderLayout.NORTH);

    }

    private JPanel createMainMenuUI() {
        JPanel menuPanel = new JPanel(new GridLayout());
        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        JButton ordersButton = new JButton("Orders");
        JButton suppliersButton = new JButton("Suppliers");

        menuPanel.add(homeButton);
        menuPanel.add(inventoryButton);
        menuPanel.add(ordersButton);
        menuPanel.add(suppliersButton);

        homeButton.addActionListener(e -> {
            switchPanel();
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
        return menuPanel;
    }

    private void switchPanel(JPanel panel, JPanel menuPanel) {
        String panelName = panel.getClass().getSimpleName();
        if (!checkUI(panelName)) {
            removeUIExcept(panelName);
            add(panel);
            add(menuPanel, BorderLayout.NORTH);
            repaint();
            revalidate();
        }
    }
    private void switchPanel() {
        getContentPane().removeAll();
        add(menuPanel, BorderLayout.NORTH);
        repaint();
        revalidate();
    }

    private boolean checkUI(String string) {
        for (Component comp : getContentPane().getComponents()) {
            String name = comp.getClass().getSimpleName();
            if (!name.equals("JPanel")) {
                System.out.println("checking " + name);
            }
            if (name.equals(string)) {
                return true;
            }
        }
        return false;
    }
    private void removeUIExcept(String string) {
        for (Component comp : getContentPane().getComponents()) {
            if (!comp.getClass().getSimpleName().equals(string) && !comp.getClass().getSimpleName().equals("JPanel")) {
                System.out.println("removing "+comp.getClass().getSimpleName());
                this.remove(comp);
            }
        }
    }
}
