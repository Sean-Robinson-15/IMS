package IMS.UI;
import IMS.Interfaces.TableUIInterface;
import IMS.Inventory.ProductManager;
import IMS.Products.Product;
import IMS.Alerts.Alerts;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InventoryUI extends GUI implements TableUIInterface {
    private final ProductManager manager;
    private DefaultTableModel inventoryTable;
    private final JTextField productIDField;
    private final JTextField productNameField;
    private final JTextField productQuantityField;
    private final JTextField productPriceField;
    private ArrayList<Product> lowStock;
    private ArrayList<Product>  addingTolowStock;

    public InventoryUI(ProductManager manager) {
        this.manager = manager;

        productIDField = new JTextField(10);
        productNameField = new JTextField(10);
        productQuantityField = new JTextField(5);
        productPriceField = new JTextField(5);
        
        JPanel northPanel = createNorthPanel("Inventory");
        JScrollPane mainPanel = createTablePanel();
        JPanel southPanel = createSouthPanel();
        
        addPanels(northPanel, mainPanel, southPanel);
        refreshTable();

    }

    @Override
    public JScrollPane createTablePanel() {
        inventoryTable = createNonEditTable(new String[]{"ID","Product", "Quantity", "Price"});
        JTable table = new JTable(inventoryTable);
        return new JScrollPane(table);
    }

    @Override
    public void refreshTable() {
        inventoryTable.setRowCount(0);
        boolean sendAlert = false;
        addingTolowStock = new ArrayList<>();
        for (Product item : manager.getAllItems()) {
            String quantity = Integer.toString(item.getQuantity());
            if (item.getQuantity() <= manager.getLowStockThreshold()) {
                quantity = "<html><font color=\"red\">" + item.getQuantity() + "</font></html>";
                sendAlert = addToLowStock(item);
            } else if (lowStock != null) {
                lowStock.remove(item);
            }
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), quantity, item.getPrice()});
        }
        if (sendAlert) {Alerts.sendStockAlert(addingTolowStock);}
    }

    public boolean addToLowStock(Product item) {
        if (lowStock == null) {
            this.lowStock = new ArrayList<Product>();
        }
        if (!lowStock.contains(item)) {
            addingTolowStock.add(item);
            lowStock.add(item);
            return true;
        }
        return false;
    }

    @Override
    public JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        addLabelField(inputPanel, "   ID :  ", productIDField);
        addLabelField(inputPanel, "   Product :  ", productNameField);
        addLabelField(inputPanel, "  Quantity :  ", productQuantityField);
        addLabelField(inputPanel, "   Price :  ", productPriceField);
        return inputPanel;
    }

    @Override
    public JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout());
        //Button Creation
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

        
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

        //Create blanks as easiest solution to create blank row, will {RETURN} to refacto
        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
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
}