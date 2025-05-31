package IMS.UI;
import IMS.Interfaces.TableUIInterface;
import IMS.Interfaces.UserUIInterface;
import IMS.Inventory.UserManager;
import IMS.Users.Customer;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class CustomersUI extends GUI implements UserUIInterface, TableUIInterface {
    private final UserManager userManager;
    private DefaultTableModel customerTable;
    private final JTextField customerNameField;
    private final JTextField customerAddressField;
    private final JTextField customerEmailField;
    private final JTextField customerIDField;

    public CustomersUI(UserManager userManager) {
        this.userManager = userManager;

        customerIDField = new JTextField(5);
        customerNameField = new JTextField(10);
        customerAddressField = new JTextField(5);
        customerEmailField = new JTextField(5);

        JPanel northPanel = createNorthPanel("Customers");
        JScrollPane mainPanel = createTablePanel();
        JPanel southPanel = createSouthPanel();
        
        addPanels(northPanel, mainPanel, southPanel);
        refreshTable();
    }

    @Override
    public JScrollPane createTablePanel() {
        customerTable = createNonEditTable(new String[]{"ID","Name", "Email", "Address"});
        JTable table = new JTable(customerTable);
        return new JScrollPane(table);
    }

    @Override
    public JPanel createTopInput() {
        JPanel topInput = new JPanel(new GridLayout());
        addLabelField(topInput, "   ID :  ", customerIDField);
        addLabelField(topInput, "   Name :  ", customerNameField);
        addLabelField(topInput, "   Email :  ", customerEmailField);
        return topInput;
    }

    @Override
    public JPanel createBottomInput() {
        JPanel bottomInput = new JPanel(new GridLayout());
        addLabelField(bottomInput, "  Address :  ", customerAddressField);
        return bottomInput;
    }

    @Override
    public JPanel createInputPanel() {
        JPanel topInput = createTopInput();
        JPanel bottomInput = createBottomInput();
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        inputPanel.add(topInput);
        inputPanel.add(bottomInput);
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
            String output = userManager.addCustomer(customerIDField.getText(), customerNameField.getText(),
                    customerAddressField.getText(), customerEmailField.getText());
            updatePanel(errorPanel, output);
            refreshTable();
        });

        updateButton.addActionListener(e -> {
            String output = userManager.updateUser(customerIDField.getText(), customerNameField.getText(),
                    customerAddressField.getText(), customerEmailField.getText());
            updatePanel(errorPanel, output);
            refreshTable();
        });

        removeButton.addActionListener(e -> {
            String ID = customerIDField.getText();
            String output = userManager.removeUser(ID);
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
    public void refreshTable(){
        customerTable.setRowCount(0);
        for (Customer user : userManager.getCustomers()) {
            customerTable.addRow(new Object[]{user.getID(), user.getName(), user.getEmail(), user.getAddress()});
        }
    }

}