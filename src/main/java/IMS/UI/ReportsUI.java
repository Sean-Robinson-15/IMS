package IMS.UI;
import IMS.Inventory.InventoryManager;
import IMS.Inventory.TransactionManager;

import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

public class ReportsUI extends GUI {
    private final TransactionManager manager;

    public ReportsUI(TransactionManager manager) {
        setLayout(new BorderLayout());
        this.manager = manager;

        //Initial IMS.UI.UI/Panel Creation. Will {RETURN} to see if there is a better way
        JPanel mainPanel = new JPanel(new GridLayout( 1, 3, 5, 5));
        JPanel northPanel = createNorthPanel("Report");
        generateReport(mainPanel);


        //Added to southPanel

        //Add panels to window
        add(northPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

    }

    @Override
    public void refreshTable(){

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