package IMS.UI;
import IMS.Inventory.*;

import javax.swing.*;
import java.awt.*;


public class UIManager extends JFrame {
    private final InventoryManager manager;
    private final TransactionManager transactionManager;
    private final BasketManager basketManager;
    private final UserManager userManager;
    private final ProductManager productManager;
    private final JPanel menuPanel;


    public UIManager(InventoryManager manager, TransactionManager transactionManager, BasketManager basketManager, UserManager userManager, ProductManager productManager) {
        this.manager = manager;
        this.transactionManager = transactionManager;
        this.basketManager = basketManager;
        this.userManager = userManager;
        this.productManager = productManager;
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(1000,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(900, 500);
        menuPanel = createMainMenuUI();
        add(menuPanel, BorderLayout.NORTH);

    }

    private JPanel createMainMenuUI() {
        JPanel menuPanel = new JPanel(new GridLayout(0,7));
        JButton homeButton = new JButton("Home");
        JButton inventoryButton = new JButton("Inventory");
        JButton ordersButton = new JButton("Orders");
        JButton suppliersButton = new JButton("Suppliers");
        JButton customersButton = new JButton("Customers");
        JButton reportsButton = new JButton("Report");
        JButton ReceivingButton = new JButton("Receiving");

        menuPanel.add(homeButton);
        menuPanel.add(inventoryButton);
        menuPanel.add(ordersButton);
        menuPanel.add(suppliersButton);
        menuPanel.add(customersButton);
        menuPanel.add(reportsButton);
        menuPanel.add(ReceivingButton);

        homeButton.addActionListener(e -> {
            switchPanel();
        });

        inventoryButton.addActionListener(e -> {
            InventoryUI inventoryUI = new InventoryUI(productManager);
            switchPanel(inventoryUI, menuPanel);
        });

        ordersButton.addActionListener(e -> {
            OrdersUI ordersUI = new OrdersUI(manager, productManager, basketManager, userManager, transactionManager);
            switchPanel(ordersUI, menuPanel);
        });

        suppliersButton.addActionListener(e -> {
            SuppliersUI suppliersUI = new SuppliersUI(userManager);
            switchPanel(suppliersUI, menuPanel);
        });

        customersButton.addActionListener(e -> {
            CustomersUI customersUI = new CustomersUI(userManager);
            switchPanel(customersUI, menuPanel);
        });

        reportsButton.addActionListener(e -> {
            ReportsUI reportsUI = new ReportsUI(transactionManager);
            switchPanel(reportsUI, menuPanel);
        });

        ReceivingButton.addActionListener(e -> {
            ReceivingUI receivingUI = new ReceivingUI(productManager);
            switchPanel(receivingUI, menuPanel);
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
