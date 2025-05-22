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


            //Create table based on default above
            JTable table = new JTable(inventoryTable);
            JTable basket = new JTable(basketTable);

            JPanel stockPane = createTablePanel(table, "Inventory");
            JPanel basketPane = createTablePanel(basket, "Basket");
            basketPane.setMinimumSize(new Dimension(200,200));
            JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,stockPane,basketPane);
            mainPanel.setDividerLocation(450);
            mainPanel.setResizeWeight(1);

//            mainPanel.add(stockPane);
//            mainPanel.add(basketPane);

            //Add Table to window


            //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
            JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
            JPanel northPanel = new JPanel(new GridLayout());
            JPanel inputPanel = new JPanel(new GridLayout());
            JPanel buttonPanel = new JPanel(new GridLayout());
            JPanel errorPanel = new JPanel(new BorderLayout());

            //Button Creation
            JButton addButton = new JButton("Add To Basket");
            JButton removeOneButton = new JButton("Remove from Basket");
            JButton removeAllButton = new JButton("Remove All");
            JButton checkoutButton = new JButton("Checkout");


            //Field Creation
            JTextField productIDField = new JTextField(10);
            JTextField productNameField = new JTextField(10);
            JTextField productQuantityField = new JTextField(5);
            JTextField productPriceField = new JTextField(5);

            //Add fields to inputPanel
            addLabelField(inputPanel, "   ID :  ", productIDField);
            addLabelField(inputPanel, "   Product :  ", productNameField);
            addLabelField(inputPanel, "  Quantity :  ", productQuantityField);
            addLabelField(inputPanel, "   Price :  ", productPriceField);

            //Create blanks as easiest solution to create blank row, will {RETURN} to refacto

            //Add buttons to buttonPanel
            buttonPanel.add(addButton);
            buttonPanel.add(removeAllButton);
            buttonPanel.add(removeOneButton);
            buttonPanel.add(checkoutButton);

            //Added to southPanel
            southPanel.add(errorPanel);
            southPanel.add(inputPanel);
            southPanel.add(buttonPanel);

            //Added Title
            JLabel label = new JLabel("Orders");
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font("Verdana", Font.PLAIN, 18));
            Border border = BorderFactory.createLineBorder(Color.BLACK);
            label.setBorder(border);
            northPanel.add(label);

            //Add panels to window
            add(northPanel, BorderLayout.NORTH);
            add(southPanel, BorderLayout.SOUTH);
            add(mainPanel, BorderLayout.CENTER);
            refreshTable();

            //Listeners to do CRUD operations
            addButton.addActionListener(e -> {

            });

            checkoutButton.addActionListener(e -> {

            });

            removeOneButton.addActionListener(e -> {

            });

        }

    private void updatePanel(JPanel panel, String output) {
        panel.removeAll();
        panel.add(new JLabel(output));
        panel.updateUI();
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