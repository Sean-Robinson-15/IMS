import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UI extends JFrame {

    public UI() {
        //Create Window
        setTitle("IMS by BNU Industry Solutions LTD");
        setSize(690,420);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Create Default Table
        DefaultTableModel inventoryTable = new DefaultTableModel(new Object[]{"ID", "Product", "Quantity", "Price"}, 1);

        //Create table based on default above
        JTable table = new JTable(inventoryTable);
        JScrollPane scrollPane = new JScrollPane(table);

        //Add Table to window
        add(scrollPane, BorderLayout.CENTER);

        //Create Button
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

        //Input Field Creation
        JPanel inputPanel = new JPanel();
        JLabel productNameLabel = new JLabel("Product:");
        JTextField productNameField = new JTextField(10);

        //Add field to inputPanel
        inputPanel.add(productNameLabel);
        inputPanel.add(productNameField);

        //Add button to buttonPanel
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);


        //Add panel to window
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI().setVisible(true));
    }
}