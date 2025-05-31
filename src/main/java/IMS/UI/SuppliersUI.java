package IMS.UI;
import IMS.Interfaces.TableUIInterface;
import IMS.Interfaces.UserUIInterface;
import IMS.Inventory.UserManager;
import IMS.Users.Supplier;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class SuppliersUI extends GUI implements UserUIInterface, TableUIInterface {
    private final UserManager manager;
    private DefaultTableModel supplierTable;
    private final JTextField supplierIDField;
    private final JTextField supplierNameField;
    private final JTextField supplierAddressField;
    private final JTextField supplierEmailField;
    private final JTextField supplierDepartmentField;

    public SuppliersUI(UserManager manager) {
        this.manager = manager;

        supplierIDField = new JTextField(5);
        supplierNameField = new JTextField(10);
        supplierAddressField = new JTextField(5);
        supplierEmailField = new JTextField(5);
        supplierDepartmentField = new JTextField(5);

        JPanel northPanel = createNorthPanel("Suppliers");
        JScrollPane mainPanel = createTablePanel();
        JPanel southPanel = createSouthPanel();

        
        addPanels(northPanel, mainPanel, southPanel);
        refreshTable();
    }

    @Override
    public JScrollPane createTablePanel() {
        supplierTable = createNonEditTable(new String[]{"ID","Name", "Address", "Email", "Department"});
        JTable table = new JTable(supplierTable);
        return new JScrollPane(table);
    }

    @Override
    public JPanel createTopInput() {
        JPanel topInput = new JPanel(new GridLayout());
        addLabelField(topInput, "   ID :  ", supplierIDField);
        addLabelField(topInput, "   Name :  ", supplierNameField);
        addLabelField(topInput, "   Email :  ", supplierEmailField);
        return topInput;
    }
    @Override
    public JPanel createBottomInput() {
        JPanel bottomInput = new JPanel(new GridLayout());
        addLabelField(bottomInput, "  Address :  ", supplierAddressField);
        addLabelField(bottomInput, "   Department (Optional) :  ", supplierDepartmentField);
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

    @Override
    public void refreshTable(){
        supplierTable.setRowCount(0);
        for (Supplier user : manager.getSuppliers()) {
            supplierTable.addRow(new Object[]{user.getID(), user.getName(), user.getAddress(), user.getEmail(), user.getDepartment()});
        }
    }

}