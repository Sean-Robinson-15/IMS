package IMS.UI;
import IMS.Inventory.InventoryManager;
import IMS.Inventory.ProductManager;
import IMS.Products.Product;
import IMS.Alerts.Alerts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReceivingUI extends GUI {
    private final ProductManager manager;
    private final DefaultTableModel inventoryTable;

    public ReceivingUI(ProductManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;


        //Create Default Table
        inventoryTable = createNonEditTable(new String[]{"ID","Product", "Quantity"});
        //Create table based on default above
        JTable table = new JTable(inventoryTable);
        JScrollPane scrollPane = new JScrollPane(table);

        //Add Table to window


        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        JPanel northPanel = createNorthPanel("Receiving (Products In Transit)");
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
        addLabelField(inputPanel, "  Quantity :  ", productQuantityField);

        //Create blanks as easiest solution to create blank row, will {RETURN} to refacto

        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        //Added to southPanel
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable();

        //Listeners to do CRUD operations
        addButton.addActionListener(e -> {
            String ID = productIDField.getText();
            String name = productNameField.getText();
            String quan = productQuantityField.getText();
            String price = productPriceField.getText();
            String output = manager.addInventoryItem(ID, name, quan, price);
            updatePanel(errorPanel, output);
            refreshTable();
        });

        updateButton.addActionListener(e -> {
            String ID = productIDField.getText().trim();
            String name = productNameField.getText();
            String quantityText = productQuantityField.getText().trim();
            String priceText = productPriceField.getText().trim();
            String output = manager.updateItem(ID,name, quantityText, priceText);
            updatePanel(errorPanel, output);
            refreshTable();
        });


        removeButton.addActionListener(e -> {
            String ID = productIDField.getText();
            String output = manager.removeItem(ID);
            updatePanel(errorPanel,output);
            refreshTable();
        });
    }


    @Override
    public void refreshTable() {
        inventoryTable.setRowCount(0);
        ArrayList<Product> lowStock = new ArrayList<>();
        for (Product item : manager.getInTransit()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity()});
        }
        Alerts.sendStockAlert(lowStock);
    }
}