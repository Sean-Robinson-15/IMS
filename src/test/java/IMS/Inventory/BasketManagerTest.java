package IMS.Inventory;
import IMS.Orders.Purchase;
import IMS.Orders.Sale;
import IMS.Orders.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasketManagerTest {

    private BasketManager manager;
    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        productManager = new ProductManager();
        UserManager userManager = new UserManager();
        userManager.addCustomer("C001", "Customer 1", "101 Made Up Lane", "Customer1@gmail.com");
        manager = new BasketManager(productManager);
    }

    @Test
    void testaddToBasket() {
        // Valid
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        String output = manager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(1, manager.getBasket().size());
        assertEquals(-10, manager.getBasket().getFirst().getQuantity());
        assertEquals(40, productManager.getQuantity("P001"));
        assertEquals("P001 added to basket. \n", output);

        // Test dupe ID (adds quantity together with currently in basket)
        manager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(-20, manager.getBasket().getFirst().getQuantity());
        assertEquals(30, productManager.getQuantity("P001"));
        assertEquals(1,manager.getBasket().size());

        //Invalid
        output = manager.addToBasket("C001","", "EmptyProduct", "10", 70);
        assertEquals("No Product Entered. Please try again.", output);
        assertEquals(1,manager.getBasket().size());

        output = manager.addToBasket("C001","P001", "", "10", 70);
        assertEquals("Invalid Name Entered. Please try again.", output);
        assertEquals(1,manager.getBasket().size());

        output = manager.addToBasket("C001","P001", "Product1", "", 70);
        assertEquals("Invalid Quantity Entered. Please try again.", output);
        assertEquals(1,manager.getBasket().size());
    }

    @Test
    void testIsCustomer() {
        assertTrue(manager.isCustomer("C001"));
        assertFalse(manager.isCustomer("S001"));
    }

    @Test
    void testRemoveItemFromBasket() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        manager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(1,manager.getBasket().size());
        assertEquals(-10, manager.getBasket().getFirst().getQuantity());
        assertEquals(40, productManager.getQuantity("P001"));
        assertEquals(1,manager.getBasket().size());

        //Valid
        String output = manager.removeItemFromBasket("P001");
        assertEquals("Item P001 Removed", output);
        assertEquals(0,manager.getBasket().size());

        //Invalid
        output = manager.removeItemFromBasket("P002");
        assertEquals("Item P002 Doesnt Exist in basket", output);
        assertEquals(0,manager.getBasket().size());

        manager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(1,manager.getBasket().size());
        output = manager.removeItemFromBasket("P002");
        assertEquals("Item P002 Doesnt Exist in basket", output);
        assertEquals(1,manager.getBasket().size());
    }

    @Test
    void testEmptyBasket() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInventoryItem("P002", "Product2", "30", "35");
        manager.addToBasket("C001","P001", "Product1", "10", 70);
        manager.addToBasket("C001","P002", "Product2", "10", 35);

        manager.emptyBasket();
        assertTrue(manager.isBasketEmpty());
        assertEquals(50, productManager.getQuantity("P001")); // Should restore inventory
        assertEquals(30, productManager.getQuantity("P002"));
        assertEquals("", manager.currentUser);

    }

    @Test
    void testCustomerCheckout() {
        //Init Customer
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInventoryItem("P002", "Product2", "30", "35");
        manager.addToBasket("C001","P001", "Product1", "10", 70);
        manager.addToBasket("C001","P002", "Product2", "10", 35);

        manager.checkoutBasket();
        assertTrue(manager.isBasketEmpty());
        assertEquals(40, productManager.getQuantity("P001")); // Should NOT restore inventory
        assertEquals(20, productManager.getQuantity("P002"));
        assertTrue( manager.currentUser.isEmpty() );


        //Invalid
        manager.currentUser = "S001";
    }

    @Test
    void testSupplierCheckout() {
        //Init Supplier
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInventoryItem("P002", "Product2", "30", "35");
        manager.addToBasket("S001","P001", "Product1", "10", 70);
        manager.addToBasket("S001","P002", "Product2", "10", 35);

        manager.checkoutBasket();
        assertTrue(manager.isBasketEmpty());
        assertEquals(50, productManager.getQuantity("P001")); // Should restore inventory
        assertEquals(30, productManager.getQuantity("P002"));
        assertTrue( manager.currentUser.isEmpty() );
    }

    @Test
    void testCreateSale() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        manager.addToBasket("C001","P001", "Product1", "10", 70);

        Transaction transaction = manager.createTransaction("T001");
        assertInstanceOf(Sale.class, transaction);
    }

    @Test
    void testCreatePurchase() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        manager.addToBasket("S001","P001", "Product1", "10", 70);

        Transaction transaction = manager.createTransaction("T001");
        assertInstanceOf(Purchase.class, transaction);
    }
}