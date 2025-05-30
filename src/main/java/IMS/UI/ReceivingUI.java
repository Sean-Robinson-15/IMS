package IMS.UI;
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

        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        JPanel northPanel = createNorthPanel("Receiving (Products In Transit)");
        JPanel inputPanel = new JPanel(new GridLayout());
        JPanel buttonPanel = new JPanel(new GridLayout());
        JPanel errorPanel = new JPanel(new BorderLayout());

        //Button Creation
        JButton receiveButton = new JButton("Receive");

        //Field Creation
        JTextField productIDField = new JTextField(10);
        JTextField productQuantityField = new JTextField(5);

        //Add fields to inputPanel
        addLabelField(inputPanel, "   ID :  ", productIDField);
        addLabelField(inputPanel, "  Quantity :  ", productQuantityField);

        //Create blanks as easiest solution to create blank row, will {RETURN} to refacto

        //Add buttons to buttonPanel
        buttonPanel.add(receiveButton);

        //Added to southPanel
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable();

        receiveButton.addActionListener(e -> {
            String ID = productIDField.getText().trim();
            String quantityText = productQuantityField.getText().trim();
            String output = manager.receiveItem(ID, quantityText);
            updatePanel(errorPanel, output);
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