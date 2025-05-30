package IMS.Inventory;
import IMS.Products.Product;
import IMS.UI.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BasketManagerTest {

    private BasketManager manager;
    private ProductManager productManager;
    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        productManager = new ProductManager();
        manager = new BasketManager(productManager);
    }

    @Test
    void testAddToBasket() {
        // Valid
        productManager.addInventoryItem("P001", "Product1", "50", "69");
        String output = manager.addToBasket("P001", "Product1", "10", 69);
        assertEquals(1, manager.getBasket().size());
        assertEquals(10, manager.getBasket().getFirst().getQuantity());
        assertEquals(40, productManager.getQuantity("P001"));
        assertEquals("P001 added to basket. \n", output);

        // Test dupe ID (adds quantity together with currently in basket)
        manager.addToBasket("P001", "Product1", "10", 69);
        assertEquals(20, manager.getBasket().getFirst().getQuantity());
        assertEquals(30, productManager.getQuantity("P001"));
        assertEquals(1,manager.getBasket().size());

        //Invalid
        output = manager.addToBasket("", "EmptyProduct", "10", 70);
        assertEquals("No Product Entered. Please try again.", output);
        assertEquals(1,manager.getBasket().size());

        output = manager.addToBasket("P001", "", "10", 70);
        assertEquals("Invalid Name Entered. Please try again.", output);
        assertEquals(1,manager.getBasket().size());

        output = manager.addToBasket("P001", "Product1", "", 70);
        assertEquals("Invalid Quantity Entered. Please try again.", output);
        assertEquals(1,manager.getBasket().size());
    }
}