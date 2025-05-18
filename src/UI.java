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
        JPanel inputPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");

        //Add button to panel
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(updateButton);


        //Add panel to window
        add(inputPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI().setVisible(true));
    }
}