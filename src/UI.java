import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UI extends JFrame {
    private InventoryManager manager = new InventoryManager();
    private DefaultTableModel inventoryTable;

    public UI() {
        //Create Window
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Create Default Table
        inventoryTable = new DefaultTableModel(new Object[]{"ID", "Product", "Quantity", "Price"}, 1);

        //Create table based on default above
        JTable table = new JTable(inventoryTable);
        JScrollPane scrollPane = new JScrollPane(table);

        //Add Table to window
        add(scrollPane, BorderLayout.CENTER);

        //Panel Creation
        JPanel inputPanel = new JPanel(new GridLayout(2, 3, 10, -10));
        JPanel productPanel = new JPanel(new BorderLayout());
        JPanel quantityPanel = new JPanel(new BorderLayout());
        JPanel pricePanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout( 0, 3));

        //Button Creation
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

        //Field Creation
        JLabel productNameLabel = new JLabel("   Product :  ");
        JTextField productNameField = new JTextField(10);

        JLabel productQuantityLabel = new JLabel("  Quantity :  ");
        JTextField productQuantityField = new JTextField(5);

        JLabel productPriceLabel = new JLabel("   Price :  ");
        JTextField productPriceField = new JTextField(5);

        //Add fields to inputPanel
        productPanel.add(productNameLabel, BorderLayout.WEST);
        productPanel.add(productNameField);
        inputPanel.add(productPanel);

        quantityPanel.add(productQuantityLabel, BorderLayout.WEST);
        quantityPanel.add(productQuantityField);
        inputPanel.add(quantityPanel);

        pricePanel.add(productPriceLabel, BorderLayout.WEST);
        pricePanel.add(productPriceField);
        inputPanel.add(pricePanel);

        //Create blanks as easiest solution to create blank row, will {RETURN} to refactor
        inputPanel.add(new JLabel());
        inputPanel.add(new JLabel());

        //Add buttons to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);

        //Add panels to window
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    private void refreshTable() {
        inventoryTable.setRowCount(0);
        for (Product item : manager.getAllItems()) {
            inventoryTable.addRow(new Object[]{item.getName(), item.getQuantity(), item.getPrice()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI().setVisible(true));
    }
}