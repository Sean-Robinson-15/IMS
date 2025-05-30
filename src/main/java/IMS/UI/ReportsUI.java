package IMS.UI;
import IMS.Inventory.TransactionManager;
import IMS.Orders.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
            double totalCost = transaction.getTotalCost();
            int quantity = transaction.getTotalProducts();
            //Set Color
            String color = "<html><font color=\"black\">";
            if (userID.charAt(0) == 'S') {
                color = "<html><font color=\"red\">";
            }

            String orderIDStr = color + orderID + "</font></html>";
            String userIDStr = color + userID + "</font></html>";
            String quantityStr = color + quantity + "</font></html>";
            String totalCostStr = color + String.format("£%.2f",totalCost) + "</font></html>";

            transactionTable.addRow(new Object[]{orderIDStr, userIDStr, quantityStr, totalCostStr});
        }
    }


    public void generateReport(JPanel mainPanel){
        TreeMap<String, Double> report = manager.generateReport();
        JLabel reportLabel = new JLabel();
        //maybe use get instead to guarantee order
        for (String item : report.keySet()) {
            Color color = Color.BLACK;
            double itemValue = report.get(item);
            if (item.equals("Profit/Loss") && itemValue <= 0) {
                color = Color.RED;
            } else if (item.equals("Profit/Loss") && itemValue > 0) {
                color = Color.GREEN;
            }

            String itemString = String.format("£%.2f",itemValue);
            JLabel label = new JLabel(reportLabel.getText() + item + " : " + itemString + "\n", JLabel.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 25));
            label.setForeground(color);
            mainPanel.add(label);
        }

    }


}