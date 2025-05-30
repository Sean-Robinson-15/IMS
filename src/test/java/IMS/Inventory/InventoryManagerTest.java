package IMS.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    private InventoryManager manager;

    @BeforeEach
    void setUp() {
        ProductManager productManager = new ProductManager();
        BasketManager basketManager = new BasketManager(productManager);
        UserManager userManager = new UserManager();
        TransactionManager transactionManager = new TransactionManager();
        manager = new InventoryManager(productManager, basketManager, userManager, transactionManager);
    }

    @Test
    void testGetNextOrderID() {
        //Init
        manager.demoMode();

        //Valid
        String output = manager.getNextOrderID();
        assertEquals("T003", output);
    }
}