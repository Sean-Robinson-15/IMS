package IMS.UI;
import IMS.Inventory.InventoryManager;
import IMS.Inventory.UserManager;
import IMS.Users.Supplier;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class SuppliersUI extends GUI {
    private final UserManager manager;
    private final DefaultTableModel supplierTable;

    public SuppliersUI(UserManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;
        //Add Table to window

        supplierTable = createNonEditTable(new String[]{"ID","Name", "Address", "Email", "Department"});

        JTable table = new JTable(supplierTable);
        JScrollPane scrollPane = new JScrollPane(table);


        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way

        JPanel northPanel = createNorthPanel("Suppliers");
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        JPanel topInput = new JPanel(new GridLayout());
        JPanel bottomInput = new JPanel(new GridLayout());
        JPanel buttonPanel = new JPanel(new GridLayout());
        JPanel errorPanel = new JPanel(new BorderLayout());
        JPanel southPanel = createSouthPanel(inputPanel, buttonPanel);

        //Button Creation
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

        //Field Creation
        JTextField supplierIDField = new JTextField(5);
        JTextField supplierNameField = new JTextField(10);
        JTextField supplierAddressField = new JTextField(5);
        JTextField supplierEmailField = new JTextField(5);
        JTextField supplierDepartmentField = new JTextField(5);

        //Add fields to inputPanel
        addLabelField(topInput, "   ID :  ", supplierIDField);
        addLabelField(topInput, "   Name :  ", supplierNameField);
        addLabelField(bottomInput, "  Address :  ", supplierAddressField);
        addLabelField(topInput, "   Email :  ", supplierEmailField);
        addLabelField(bottomInput, "   Department (Optional) :  ", supplierDepartmentField);

        inputPanel.add(topInput);
        inputPanel.add(bottomInput);

        //Create blanks as easiest solution to create blank row, will {RETURN} to refacto

        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        //Added to southPanel


        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
        refreshTable();

        //Listeners to do CRUD operations
        addButton.addActionListener(e -> {
            String output = manager.addSupplier(supplierIDField.getText(), supplierNameField.getText(),
                    supplierAddressField.getText(), supplierEmailField.getText(),
                    supplierDepartmentField.getText());
            updatePanel(errorPanel, output);
            refreshTable();
        });

        updateButton.addActionListener(e -> {
            String output = manager.updateUser(supplierIDField.getText(), supplierNameField.getText(),
                    supplierAddressField.getText(), supplierEmailField.getText(),
                    supplierDepartmentField.getText());
            updatePanel(errorPanel, output);
            refreshTable();

        });

        removeButton.addActionListener(e -> {
            String ID = supplierIDField.getText();
            String output = manager.removeUser(ID);
            updatePanel(errorPanel,output);
            refreshTable();
        });

    }

    @Override
    public void refreshTable(){
        supplierTable.setRowCount(0);
        for (Supplier user : manager.getSuppliers()) {
            supplierTable.addRow(new Object[]{user.getID(), user.getName(), user.getAddress(), user.getEmail(), user.getDepartment()});
        }
    }

    @Override
    public JPanel createSouthPanel(JPanel inputPanel, JPanel buttonPanel) {
        JPanel southPanel = new JPanel(new GridLayout( 3, 1, 5, 5));
        southPanel.add(errorPanel);
        southPanel.add(inputPanel);
        southPanel.add(buttonPanel);
        return null;
    }

}