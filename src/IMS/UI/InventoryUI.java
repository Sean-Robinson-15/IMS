package IMS.UI;
import IMS.UI.GUI;
import IMS.Inventory.InventoryManager;
import IMS.Product.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InventoryUI extends GUI {

    private DefaultTableModel inventoryTable;

    public InventoryUI(InventoryManager manager) {
        manager.testItems();
        setName("InventoryUI");
        setLayout(new BorderLayout());
        //Create Window
//        setTitle("IMS by BNU Industry Solutions LTD");
//        setSize(690,420);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLocation(-900, 500);

        //Create Default Table
        inventoryTable = new DefaultTableModel(new Object[]{"ID", "Product", "Quantity", "Price"}, 1);

        //Create table based on default above
        JTable table = new JTable(inventoryTable);
        JScrollPane scrollPane = new JScrollPane(table);

        //Add Table to window


        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel northPanel = new JPanel(new GridLayout( 2, 1, 10, -10));
        JPanel southPanel = new JPanel(new GridLayout( 2, 1));
        JPanel inputPanel = new JPanel(new GridLayout());
        JPanel buttonPanel = new JPanel(new GridLayout());
        JPanel errorPanel = new JPanel(new BorderLayout());

        //Button Creation
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

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

        //Create blanks as easiest solution to create blank row, will {RETURN} to refactor
        northPanel.add(inputPanel);
        northPanel.add(new JPanel());

        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        //Added to southPanel
        southPanel.add(errorPanel);
        southPanel.add(buttonPanel);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable(manager);

        //Listeners to do CRUD operations
        addButton.addActionListener(e -> {
            ArrayList<String> IDs = manager.getAllIDs();
            String ID = productIDField.getText();
            String name = productNameField.getText();
            int quan = Integer.parseInt(productQuantityField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            if (!IDs.contains(ID)) {
                manager.addItem(ID, name, quan, price);
                updatePanel(errorPanel, "ID "+ID+" has been added.");
            } else {
                updatePanel(errorPanel, "ID "+ID+" already Exists!");
            }
            refreshTable(manager);
        });

        removeButton.addActionListener(e -> {
            ArrayList<String> IDs = manager.getAllIDs();
            String ID = productIDField.getText();
            if (IDs.contains(ID)) {
                manager.removeItem(ID);
                updatePanel(errorPanel,"ID "+ID+" has been removed.");
            } else {
                updatePanel(errorPanel, "ID "+ID+" doesnt Exist!");
            }
            refreshTable(manager);
        });
    }

    private void updatePanel(JPanel panel, String output) {
        panel.removeAll();
        panel.add(new JLabel(output));
        panel.updateUI();
    }



    private void refreshTable(InventoryManager manager) {
        inventoryTable.setRowCount(0);
        for (Product item : manager.getAllItems()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity(), item.getPrice()});
        }
    }
}