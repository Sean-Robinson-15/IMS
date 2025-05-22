package IMS.UI;
import IMS.Inventory.InventoryManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class SuppliersUI extends GUI {
    private final InventoryManager manager;
    public SuppliersUI(InventoryManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;
        //Add Table to window


        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        JPanel northPanel = new JPanel(new GridLayout());
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

        //Create blanks as easiest solution to create blank row, will {RETURN} to refacto

        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        //Added to southPanel
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        //Added title
        JLabel label = new JLabel("Suppliers");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Verdana", Font.PLAIN, 18));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        label.setBorder(border);
        northPanel.add(label);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

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
        });

        updateButton.addActionListener(e -> {
            ArrayList<String> IDs = manager.getAllIDs();
            String ID = productIDField.getText();
            int quantity = Integer.parseInt(productQuantityField.getText());
            if (IDs.contains(ID)) {
                manager.updateItem(ID, quantity);
                updatePanel(errorPanel,"ID "+ID+" has been updated.");
            } else {
                updatePanel(errorPanel, "ID "+ID+" doesnt Exist!");
            }
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
        });
    }

    private void updatePanel(JPanel panel, String output) {
        panel.removeAll();
        panel.add(new JLabel(output));
        panel.updateUI();
    }

    public void refreshTable(){
        manager.getAllIDs();
        // PLaceholder for later
    }

}