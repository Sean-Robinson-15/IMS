package IMS.UI;
import IMS.Inventory.InventoryManager;
import IMS.Products.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class OrdersUI extends GUI {
    private final InventoryManager manager;
    private final DefaultTableModel inventoryTable;
    private final DefaultTableModel basketTable;
        public OrdersUI(InventoryManager manager) {
            this.manager = manager;
            setLayout(new BorderLayout());

            //Create Default Table
            inventoryTable = createNonEditTable(new String[]{"ID","Product", "Quantity", "Price"});
            basketTable = createNonEditTable(new String[]{"Product", "Quantity", "Price"});

            JSplitPane mainPanel = createMainPanel();
            JPanel northPanel = createNorthPanel("Orders");
            JPanel southPanel = createSouthPanel();

            //Add panels to window
            add(northPanel, BorderLayout.NORTH);
            add(southPanel, BorderLayout.SOUTH);
            add(mainPanel, BorderLayout.CENTER);
            refreshTable();

        }

    private void updatePanel(JPanel panel, String output) {
        panel.removeAll();
        panel.add(new JLabel(output));
        panel.updateUI();
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
        JPanel errorPanel = new JPanel(new BorderLayout());
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();

        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        return southPanel;
    }

    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout());

        JTextField productIDField = new JTextField(10);
        JTextField productNameField = new JTextField(10);
        JTextField productQuantityField = new JTextField(5);
        JTextField productPriceField = new JTextField(5);

        addLabelField(inputPanel, "   ID :  ", productIDField);
        addLabelField(inputPanel, "   Product :  ", productNameField);
        addLabelField(inputPanel, "  Quantity :  ", productQuantityField);
        addLabelField(inputPanel, "   Price :  ", productPriceField);

        return inputPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());

        JButton addButton = new JButton("Add To Basket");
        JButton removeOneButton = new JButton("Remove from Basket");
        JButton removeAllButton = new JButton("Remove All");
        JButton checkoutButton = new JButton("Checkout");

        addButton.addActionListener(e -> {

        });

        checkoutButton.addActionListener(e -> {

        });

        removeOneButton.addActionListener(e -> {

        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeAllButton);
        buttonPanel.add(removeOneButton);
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



    public void refreshTable() {
        inventoryTable.setRowCount(0);
        for (Product item : manager.getAllItems()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity(), item.getPrice()});
        }
        basketTable.setRowCount(0);
        for (Product item : manager.getAllItems()) {
            basketTable.addRow(new Object[]{ item.getName(), item.getQuantity(), item.getPrice()});
        }
    }
}