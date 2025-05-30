package IMS.Inventory;

import IMS.Orders.Transaction;
import IMS.Orders.Sale;
import IMS.Orders.Purchase;
import IMS.Products.Product;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class TransactionManager {
    private final Map<String, Transaction> transactions = new HashMap<>();

    public String addTransaction(String orderID, Transaction transaction) {
        if (transactions.containsKey(orderID)) {
            return "Order ID " + orderID + " already exists.";
        }
        transactions.put(orderID, transaction);
        return "Order ID " + orderID + " added.";
    }

    public Transaction getTransaction(String orderID) {
        return transactions.get(orderID);
    }
    public ArrayList<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions.values());
    }

    public HashMap<String,Double> generateReport() {
        HashMap<String,Double> report = new HashMap<>();
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
        report.put("Revenue", sales);
        report.put("Purchases", purchases);
        report.put("Profit/Loss", profit);
        return report;
    }

}
