package IMS.Orders;

import IMS.Managers.TransactionManager;
import IMS.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private TransactionManager transactionManager;
    private Transaction transaction;
    private ArrayList<Product> products;
    private Product product;

    @BeforeEach
    void setUp() {
        products = new ArrayList<Product>();
        transactionManager = new TransactionManager();
        product = new Product("P001", "Test Product", 50, 70);
        products.add(product);
        transactionManager.addTransaction("T001", new Sale("T001", "C001", products));
        transaction = transactionManager.getAllTransactions().getFirst();
    }

    @Test
    void testGetOrderID() {
        //Valid
        String output = transaction.getOrderID();
        assertEquals("T001", output);
    }
    @Test
    void testGetUserID() {
        //Valid
        String output = transaction.getUserID();
        assertEquals("C001", output);
    }

    @Test
    void testGetTotalCost() {
        //Valid
        double output = transaction.getTotalCost();
        assertEquals(3500, output);
    }

    @Test
    void testGetTotalProducts() {
        int output = transaction.getTotalProducts();
        assertEquals(1, output);
    }

    @Test
    void testGetProducts() {
        ArrayList<Product> output = transaction.getProducts();
        assertEquals(1, transaction.getProducts().size());
        assertEquals("P001", output.getFirst().getID());
    }

}
