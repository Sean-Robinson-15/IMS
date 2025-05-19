import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class UI extends JFrame {
    private InventoryManager manager = new InventoryManager();
    private DefaultTableModel inventoryTable;

    public UI() {
        manager.testItems();


        //Create Window
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(-900, 500);

        //Create Default Table
        inventoryTable = new DefaultTableModel(new Object[]{"ID", "Product", "Quantity", "Price"}, 1);

        //Create table based on default above
        JTable table = new JTable(inventoryTable);
        JScrollPane scrollPane = new JScrollPane(table);

        //Add Table to window
        add(scrollPane, BorderLayout.CENTER);

        //Initial UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel northPanel = new JPanel(new GridLayout( 2, 1, 10, -10));
        JPanel southPanel = new JPanel(new GridLayout( 2, 1));
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

        //Create blanks as easiest solution to create blank row, will {RETURN} to refactor
        northPanel.add(inputPanel);
        northPanel.add(new JPanel());

        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(removeButton);

        //Added to southPanel
        southPanel.add(errorPanel);
        southPanel.add(buttonPanel);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        refreshTable();

        //Listeners to do CRUD operations
        addButton.addActionListener(e -> {
            ArrayList<String> IDs = manager.getAllIDs();
            String ID = productIDField.getText();
            String name = productNameField.getText();
            int quan = Integer.parseInt(productQuantityField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            if (!IDs.contains(ID)) {
                manager.addItem(ID, name, quan, price);
                errorPanel.removeAll(); // {RETURN} refactor into function later
                errorPanel.add(new JLabel("ID "+ID+" has been added"));
                errorPanel.updateUI();
            } else {
                errorPanel.removeAll();
                errorPanel.add(new JLabel("ID "+ID+" Already Exists!"));
                errorPanel.updateUI();
            }
            refreshTable();
        });

        removeButton.addActionListener(e -> {
            ArrayList<String> IDs = manager.getAllIDs();
            String ID = productIDField.getText();
            if (IDs.contains(ID)) {
                manager.removeItem(ID);
                errorPanel.removeAll(); // {RETURN} refactor into function later
                errorPanel.add(new JLabel("ID "+ID+" has been removed"));
                errorPanel.updateUI();
            } else {
                errorPanel.removeAll();
                errorPanel.add(new JLabel("ID "+ID+" doesnt Exist!"));
                errorPanel.updateUI();
            }
            refreshTable();
        });
    }

    private void addLabelField(JPanel parent, String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        parent.add(panel);
    }

    private void refreshTable() {
        inventoryTable.setRowCount(0);
        for (Product item : manager.getAllItems()) {
            inventoryTable.addRow(new Object[]{item.getID(), item.getName(), item.getQuantity(), item.getPrice()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI().setVisible(true));
    }
}