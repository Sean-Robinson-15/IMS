package IMS.UI;
import IMS.Interfaces.TableUIInterface;
import IMS.Inventory.ProductManager;
import IMS.Products.Product;
import IMS.Alerts.Alerts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ReceivingUI extends GUI implements TableUIInterface {
    private final ProductManager productManager;
    private DefaultTableModel inventoryTable;
    private final JTextField productIDField;
    private final JTextField productQuantityField;

    public ReceivingUI(ProductManager productManager) {
        this.productManager = productManager;

        productIDField = new JTextField(10);
        productQuantityField = new JTextField(5);

        JPanel northPanel = createNorthPanel("Receiving (Products In Transit)");
        JScrollPane mainPanel = createTablePanel();
        JPanel southPanel = createSouthPanel();

        addPanels(northPanel, mainPanel, southPanel);
        refreshTable();
    }
    @Override
    public JScrollPane createTablePanel() {
        inventoryTable = createNonEditTable(new String[]{"ID","Product", "Quantity"});
        JTable table = new JTable(inventoryTable);
        return new JScrollPane(table);
    }

    @Override
    public JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        addLabelField(inputPanel, "   ID :  ", productIDField);
        addLabelField(inputPanel, "  Quantity :  ", productQuantityField);
        return inputPanel;
    }

    @Override
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());
        JButton receiveButton = new JButton("Receive");
        buttonPanel.add(receiveButton);

        receiveButton.addActionListener(e -> {
            String ID = productIDField.getText().trim();
            String quantityText = productQuantityField.getText().trim();
            String output = productManager.receiveItem(ID, quantityText);
            updatePanel(errorPanel, output);
            refreshTable();
        });

        return buttonPanel;
    }

    @Override
    public JPanel createSouthPanel() {
        JPanel inputPanel = createInputPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);
        return southPanel;
    }

    @Override
    public void refreshTable() {
        inventoryTable.setRowCount(0);
        ArrayList<Product> lowStock = new ArrayList<>();
        for (Product item : productManager.getInTransit()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity()});
        }
        Alerts.sendStockAlert(lowStock);
    }
}