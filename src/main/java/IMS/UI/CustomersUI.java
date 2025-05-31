package IMS.UI;
import IMS.Inventory.UserManager;
import IMS.Users.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class CustomersUI extends GUI {
    private final UserManager manager;
    private final DefaultTableModel customerTable;
    private JPanel errorPanel;

    public CustomersUI(UserManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;
        //Add Table

        customerTable = createNonEditTable(new String[]{"ID","Name", "Email", "Address"});

        JTable table = new JTable(customerTable);
        JScrollPane scrollPane = new JScrollPane(table);

        //Field Creation
        JTextField customerIDField = new JTextField(5);
        JTextField customerNameField = new JTextField(10);
        JTextField customerAddressField = new JTextField(5);
        JTextField customerEmailField = new JTextField(5);

        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way

        JPanel topInput = createTopInput(customerIDField, customerNameField, customerEmailField);
        JPanel bottomInput = createBottomInput(customerAddressField);
        JPanel inputPanel = createInputPanel(topInput, bottomInput);


        JPanel errorPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = createButtonPanel(customerIDField,customerNameField,
                customerAddressField, customerEmailField, errorPanel);
        JPanel southPanel = createSouthPanel( inputPanel, buttonPanel);
        JPanel northPanel = createNorthPanel("Customers");



        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable();



    }

    private JPanel createTopInput(JTextField customerIDField, JTextField customerNameField, JTextField customerEmailField) {
        JPanel topInput = new JPanel(new GridLayout());
        addLabelField(topInput, "   ID :  ", customerIDField);
        addLabelField(topInput, "   Name :  ", customerNameField);
        addLabelField(topInput, "   Email :  ", customerEmailField);
        return topInput;
    }
    private JPanel createBottomInput(JTextField customerAddressField) {
        JPanel bottomInput = new JPanel(new GridLayout());
        addLabelField(bottomInput, "  Address :  ", customerAddressField);
        return bottomInput;
    }

    private JPanel createInputPanel(JPanel topInput, JPanel bottomInput) {
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        inputPanel.add(topInput);
        inputPanel.add(bottomInput);
        return inputPanel;
    }

    private JPanel createButtonPanel(JTextField customerIDField, JTextField customerNameField,
                             JTextField customerAddressField, JTextField customerEmailField,
                             JPanel errorPanel) {
        JPanel buttonPanel = new JPanel(new GridLayout());
        //Button Creation
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

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

        //Create blanks as easiest solution to create blank row, will {RETURN} to refacto
        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);
        return buttonPanel;
    }

    @Override
    public JPanel createSouthPanel(JPanel inputPanel, JPanel buttonPanel) {
        errorPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);
        return southPanel;
    }


    @Override
    public void refreshTable(){
        customerTable.setRowCount(0);
        for (Customer user : manager.getCustomers()) {
            customerTable.addRow(new Object[]{user.getID(), user.getName(), user.getEmail(), user.getAddress()});
        }
    }

}