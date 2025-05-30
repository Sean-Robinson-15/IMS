package IMS.UI;
import IMS.Inventory.InventoryManager;
import IMS.Inventory.UserManager;
import IMS.Users.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class CustomersUI extends GUI {
    private final UserManager manager;
    private final DefaultTableModel customerTable;

    public CustomersUI(UserManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;
        //Add Table

        customerTable = createNonEditTable(new String[]{"ID","Name", "Email", "Address"});

        JTable table = new JTable(customerTable);
        JScrollPane scrollPane = new JScrollPane(table);


        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        JPanel northPanel = createNorthPanel("Customers");
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        JPanel topInput = new JPanel(new GridLayout());
        JPanel bottomInput = new JPanel(new GridLayout());
        JPanel buttonPanel = new JPanel(new GridLayout());
        JPanel errorPanel = new JPanel(new BorderLayout());

        //Button Creation
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

        //Field Creation
        JTextField customerIDField = new JTextField(5);
        JTextField customerNameField = new JTextField(10);
        JTextField customerAddressField = new JTextField(5);
        JTextField customerEmailField = new JTextField(5);

        //Add fields to inputPanel
        addLabelField(topInput, "   ID :  ", customerIDField);
        addLabelField(topInput, "   Name :  ", customerNameField);
        addLabelField(bottomInput, "  Address :  ", customerAddressField);
        addLabelField(topInput, "   Email :  ", customerEmailField);

        inputPanel.add(topInput);
        inputPanel.add(bottomInput);

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
            String output = manager.addCustomer(customerIDField.getText(), customerNameField.getText(),
                    customerAddressField.getText(), customerEmailField.getText());
            updatePanel(errorPanel, output);
            refreshTable();
        });

        updateButton.addActionListener(e -> {
            String output = manager.updateUser(customerIDField.getText(), customerNameField.getText(),
                    customerAddressField.getText(), customerEmailField.getText());
            updatePanel(errorPanel, output);
            refreshTable();
        });

        removeButton.addActionListener(e -> {
            ArrayList<String> IDs = manager.getAllCustomerIDs();
            String ID = customerIDField.getText();
            String output = manager.removeUser(ID);
            updatePanel(errorPanel, output);
            refreshTable();
        });

    }

    @Override
    public void refreshTable(){
        customerTable.setRowCount(0);
        for (Customer user : manager.getCustomers()) {
            customerTable.addRow(new Object[]{user.getID(), user.getName(), user.getEmail(), user.getAddress()});
        }
    }

}