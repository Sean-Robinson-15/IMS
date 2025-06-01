package IMS.Inventory;
import IMS.Orders.Transaction;
import IMS.Orders.Sale;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;

public class TransactionManager {
    private final Map<String, Transaction> transactions = new TreeMap<>();

    public String addTransaction(String orderID, Transaction transaction) {
        if (transactions.containsKey(orderID)) {
            System.out.println("Order ID " + orderID + " already exists.");
            return "Error: Order ID " + orderID + " already exists.";
        }
        transactions.put(orderID, transaction);
        return "Order ID " + orderID + " added.";
    }

    public TreeMap<String,Double> generateReport() {
        TreeMap<String,Double> report = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        double sales = 0;
        double purchases = 0;

        for (Transaction transaction : transactions.values()) {
            if (transaction instanceof Sale) {
                sales += transaction.getTotalCost();

            } else {
                purchases += transaction.getTotalCost();
            }
        }

        double profit = sales - purchases;
        System.out.println("Sales: " + sales);
        report.put("Revenue", sales);
        report.put("Purchases", purchases);
        report.put("Profit/Loss", profit);
        return report;
    }

    public ArrayList<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

}
