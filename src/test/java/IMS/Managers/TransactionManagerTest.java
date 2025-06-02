package IMS.Managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionManagerTest {


    private TransactionManager transactionManager;

    @BeforeEach
    void setUp() {
        transactionManager = new TransactionManager();
    }

    @Test
    void testAddTransactionAndGetAllTransactions () {
        transactionManager.addTransaction("T001", new IMS.Orders.Purchase("T001", "S001", null));
        assertNotNull(transactionManager.getAllTransactions());
        assertEquals(1, transactionManager.getAllTransactions().size());
        assertEquals("T001", transactionManager.getAllTransactions().getFirst().getOrderID());

    }


}