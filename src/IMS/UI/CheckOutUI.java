package IMS.UI;

import IMS.Inventory.InventoryManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CheckOutUI extends GUI{
    private final InventoryManager manager;
    public CheckOutUI(InventoryManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;
        //Add Table to window


        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        JPanel northPanel = createNorthPanel("Suppliers");
        JPanel inputPanel = new JPanel(new GridLayout());
        JPanel buttonPanel = new JPanel(new GridLayout());
        JPanel errorPanel = new JPanel(new BorderLayout());

        //Button Creation
        JButton addButton = new JButton("Add");
        JButton checkoutButton = new JButton("Checkout");
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
        buttonPanel.add(checkoutButton);
        buttonPanel.add(removeButton);

        //Added to southPanel
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        //Listeners to do CRUD operations
        addButton.addActionListener(e -> {

        });

        checkoutButton.addActionListener(e -> {
//            manager.checkoutBasket();
            updatePanel(errorPanel, "Basket Checked out");
            refreshTable();
        });

        removeButton.addActionListener(e -> {

        });
    }


    public void refreshTable(){
        manager.getAllIDs();
        // PLaceholder for later
    }

}