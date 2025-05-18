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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UI().setVisible(true));
    }
}