package IMS.Managers;
import IMS.Orders.Purchase;
import IMS.Orders.Sale;
import IMS.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    private InventoryManager inventoryManager;
    private TransactionManager transactionManager;
    private ProductManager productManager;


    @BeforeEach
    void setUp() {
        productManager = new ProductManager();
        BasketManager basketManager = new BasketManager(productManager);
        UserManager userManager = new UserManager();
        transactionManager = new TransactionManager();
        inventoryManager = new InventoryManager(productManager, basketManager, userManager, transactionManager);
    }

    @Test
    void testGetNextOrderID() {
        //Init
        productManager.randomize((int)(Math.random()*10)+10);
        ArrayList<Product> inventoryArray = productManager.getAllItems();
        transactionManager.addTransaction("T001", new Sale("T001", "C001", inventoryManager.getRandomProducts(inventoryArray, (int)(Math.random()*10) + 1)));
        transactionManager.addTransaction("T002", new Purchase("T002", "S001", inventoryManager.getRandomProducts(inventoryArray, (int)(Math.random()*10) + 1)));

        //Valid
        String output = inventoryManager.getNextOrderID();
        assertEquals("T003", output);
    }
}