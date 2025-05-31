package IMS.UI;
import IMS.Inventory.TransactionManager;
import IMS.Orders.Transaction;
import IMS.Renderers.TransactionRowRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.TreeMap;

public class ReportsUI extends GUI {
    private final TransactionManager manager;
    private final DefaultTableModel transactionTable;

    public ReportsUI(TransactionManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;

        //Create Default Table
        transactionTable = createNonEditTable(new String[]{"OrderID", "UserID" ,"Items", "Price"});

//        inventoryTable.setFont(new Font("Serif", Font.BOLD, 20));
        //Create table based on default above
        JTable table = new JTable(transactionTable);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));

        table.setDefaultRenderer(Object.class, new TransactionRowRenderer());
        JScrollPane mainPanel = new JScrollPane(table);

        JPanel northPanel = createNorthPanel("Report");
        JPanel southPanel = new JPanel(new GridLayout( 1, 3, 5, 5));
        generateReport(southPanel);

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        refreshTable();

    }

    @Override
    public void refreshTable(){
        transactionTable.setRowCount(0);
        for (Transaction transaction : manager.getAllTransactions()) {
            String userID = transaction.getUserID();
            String orderID = transaction.getOrderID();
            String totalCost = String.format("£%.2f",transaction.getTotalCost());
            int quantity = transaction.getTotalProducts();
            transactionTable.addRow(new Object[]{orderID, userID, quantity, totalCost});
        }
    }

    @Override
    public JPanel createSouthPanel(JPanel inputPanel, JPanel buttonPanel) {
        return null;
    }


    public void generateReport(JPanel mainPanel){
        TreeMap<String, Double> report = manager.generateReport();
        final Color DARK_GREEN = new Color(0x21692d);
        final Color DARK_RED = new Color(0xb50b0b);
        JLabel reportLabel = new JLabel();
        //maybe use get instead to guarantee order
        for (String item : report.keySet()) {
            Color color = Color.BLACK;
            double itemValue = report.get(item);
            if (item.equals("Profit/Loss") && itemValue <= 0) {
                color = DARK_RED;
            } else if (item.equals("Profit/Loss") && itemValue > 0) {
                color = DARK_GREEN;
            }

            String itemString = String.format("£%.2f",itemValue);
            JLabel label = new JLabel(reportLabel.getText() + item + " : " + itemString + "\n", JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 25));
            label.setForeground(color);
            mainPanel.add(label);
        }

    }


}