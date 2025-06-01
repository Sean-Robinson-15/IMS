package IMS.UI;
import IMS.Interfaces.TableUIInterface;
import IMS.Managers.TransactionManager;
import IMS.Orders.Transaction;
import IMS.Renderers.TransactionRowRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.TreeMap;

public class ReportsUI extends GUI implements TableUIInterface {
    private final TransactionManager transactionManager;
    private DefaultTableModel transactionTable;

    public ReportsUI(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;

        JPanel northPanel = createNorthPanel("Report");
        JScrollPane mainPanel = createTablePanel();
        JPanel southPanel = createSouthPanel();

        addPanels(northPanel, mainPanel, southPanel);
        refreshTable();

    }

    @Override
    public JScrollPane createTablePanel() {
        transactionTable = createNonEditTable(new String[]{"OrderID", "UserID" ,"Items", "Price"});
        JTable table = new JTable(transactionTable);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        table.setDefaultRenderer(Object.class, new TransactionRowRenderer());
        return new JScrollPane(table);
    }

    @Override
    public void refreshTable(){
        transactionTable.setRowCount(0);
        for (Transaction transaction : transactionManager.getAllTransactions()) {
            String userID = transaction.getUserID();
            String orderID = transaction.getOrderID();
            String totalCost = String.format("£%.2f",transaction.getTotalCost());
            int quantity = transaction.getTotalProducts();
            transactionTable.addRow(new Object[]{orderID, userID, quantity, totalCost});
        }
    }

    @Override
    protected JPanel createInputPanel() {
        return null;
    }

    @Override
    protected JPanel createButtonPanel() {
        return null;
    }

    @Override
    public JPanel createSouthPanel() {
        JPanel southPanel = new JPanel(new GridLayout( 1, 3, 5, 5));
        generateReport(southPanel);
        return southPanel;
    }


    public void generateReport(JPanel mainPanel){
        TreeMap<String, Double> report = transactionManager.generateReport();
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