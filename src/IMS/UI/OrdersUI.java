package IMS.UI;
import IMS.Inventory.InventoryManager;
import IMS.Products.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class OrdersUI extends GUI {
    private final InventoryManager manager;
    private final DefaultTableModel inventoryTable;
    private final DefaultTableModel basketTable;
    private JTextField productIDField;
    private JTextField productQuantityField;
    private JPanel errorPanel;

        public OrdersUI(InventoryManager manager) {
            this.manager = manager;
            setLayout(new BorderLayout());

            //Create Default Table
            inventoryTable = createNonEditTable(new String[]{"ID","Product", "Quantity", "Price"});
            basketTable = createNonEditTable(new String[]{"ID", "Product", "Quantity", "Price"});

            JSplitPane mainPanel = createMainPanel();
            JPanel northPanel = createNorthPanel("Orders");
            JPanel southPanel = createSouthPanel();

            //Add panels to window
            add(northPanel, BorderLayout.NORTH);
            add(southPanel, BorderLayout.SOUTH);
            add(mainPanel, BorderLayout.CENTER);
            refreshTable();

        }

    private JSplitPane createMainPanel() {
        JTable table = new JTable(inventoryTable);
        JTable basket = new JTable(basketTable);
        JPanel stockPane = createTablePanel(table, "Inventory");
        JPanel basketPane = createTablePanel(basket, "Basket");
        basketPane.setMinimumSize(new Dimension(200,200));
        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,stockPane,basketPane);
        mainPanel.setDividerLocation(450);
        mainPanel.setResizeWeight(1);
        return mainPanel;
    }



    private JPanel createSouthPanel() {
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        errorPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        return southPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout());

        productIDField = new JTextField(10);
        productQuantityField = new JTextField(5);

        addLabelField(inputPanel, "   ID :  ", productIDField);
        addLabelField(inputPanel, "  Quantity :  ", productQuantityField);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());

        JButton addButton = new JButton("Add To Basket/Update");
        JButton removeOneButton = new JButton("Remove from Basket");
        JButton removeAllButton = new JButton("Remove All");
        JButton checkoutButton = new JButton("Checkout");

        addButton.addActionListener(e -> {
            updatePanel(errorPanel, "");
            String ID = productIDField.getText();
            // {RETURN} to refactor
            if (Objects.equals(productQuantityField.getText(), "")) {
                updatePanel(errorPanel, "No Quantity Entered. Please try again.");
                refreshTable();
                return;
            }
            if (Objects.equals(ID, "")) {
                updatePanel(errorPanel, "No Product Entered. Please try again.");
                refreshTable();
                return;
            }
            int inputQuan = Integer.parseInt(productQuantityField.getText());
            int stockQuan = manager.getQuantity(ID);
            if (inputQuan <= stockQuan) {

                String name = manager.getName(ID);
                double price = manager.getPrice(ID);

                manager.addToBasket(ID, name, inputQuan, price);
            }else {
                updatePanel(errorPanel, "Requested Quantity too high");

            }
            refreshTable();

        });

        checkoutButton.addActionListener(e -> {

        });

        removeOneButton.addActionListener(e -> {
            String ID = productIDField.getText();
            try {
                manager.removeItemFromBasket(ID);
                updatePanel(errorPanel, "Item "+ID+" Removed");
            } catch (NullPointerException ignored) {
                updatePanel(errorPanel, "Item Already Removed");
            }
            refreshTable();
        });

        removeAllButton.addActionListener(e -> {
            manager.clearBasket();
            refreshTable();

        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeOneButton);
        buttonPanel.add(removeAllButton);
        buttonPanel.add(checkoutButton);

        return buttonPanel;
    }

    private JPanel createTablePanel(JTable table, String header) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(new JScrollPane(table), BorderLayout.CENTER);
            JLabel headerLabel = createHeader(header);
            panel.add(headerLabel, BorderLayout.NORTH);
            return panel;
    }


    @Override
    public void refreshTable() {
        inventoryTable.setRowCount(0);
        basketTable.setRowCount(0);
        System.out.println("Refreshing");
        for (Product item : manager.getAllItems()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity(), item.getPrice()});
        }
        for (Product item : manager.getBasket()) {
            basketTable.addRow(new Object[]{ item.getID(), item.getName(), item.getQuantity(), item.getPrice()});
        }
    }
}