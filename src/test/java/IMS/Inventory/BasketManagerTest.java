package IMS.Inventory;
import IMS.Orders.Purchase;
import IMS.Orders.Sale;
import IMS.Orders.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasketManagerTest {

    private BasketManager basketManager;
    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        productManager = new ProductManager();
        UserManager userManager = new UserManager();
        userManager.addCustomer("C001", "Customer 1", "101 Made Up Lane", "Customer1@gmail.com");
        basketManager = new BasketManager(productManager);
    }
    //test

    @Test
    void testaddToBasket() {
        // Valid
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        String output = basketManager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(1, basketManager.getBasket().size());
        assertEquals(-10, basketManager.getBasket().getFirst().getQuantity());
        assertEquals(40, productManager.getQuantity("P001"));
        assertEquals("P001 added to basket. \n", output);

        // Test dupe ID (adds quantity together with currently in basket)
        basketManager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(-20, basketManager.getBasket().getFirst().getQuantity());
        assertEquals(30, productManager.getQuantity("P001"));
        assertEquals(1, basketManager.getBasket().size());

        //Invalid
        output = basketManager.addToBasket("C001","", "EmptyProduct", "10", 70);
        assertEquals("No Product Entered. Please try again.", output);
        assertEquals(1, basketManager.getBasket().size());

        output = basketManager.addToBasket("C001","P001", "", "10", 70);
        assertEquals("Invalid Name Entered. Please try again.", output);
        assertEquals(1, basketManager.getBasket().size());

        output = basketManager.addToBasket("C001","P001", "Product1", "", 70);
        assertEquals("Invalid Quantity Entered. Please try again.", output);
        assertEquals(1, basketManager.getBasket().size());
    }

    @Test
    void testIsCustomer() {
        assertTrue(basketManager.isCustomer("C001"));
        assertFalse(basketManager.isCustomer("S001"));
    }

    @Test
    void testRemoveItemFromBasket() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        basketManager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(1, basketManager.getBasket().size());
        assertEquals(-10, basketManager.getBasket().getFirst().getQuantity());
        assertEquals(40, productManager.getQuantity("P001"));
        assertEquals(1, basketManager.getBasket().size());

        //Valid
        String output = basketManager.removeItemFromBasket("P001");
        assertEquals("Item P001 Removed", output);
        assertEquals(0, basketManager.getBasket().size());

        //Invalid
        output = basketManager.removeItemFromBasket("P002");
        assertEquals("Item P002 Doesnt Exist in basket", output);
        assertEquals(0, basketManager.getBasket().size());

        basketManager.addToBasket("C001","P001", "Product1", "10", 70);
        assertEquals(1, basketManager.getBasket().size());
        output = basketManager.removeItemFromBasket("P002");
        assertEquals("Item P002 Doesnt Exist in basket", output);
        assertEquals(1, basketManager.getBasket().size());
    }

    @Test
    void testEmptyBasket() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInventoryItem("P002", "Product2", "30", "35");
        basketManager.addToBasket("C001","P001", "Product1", "10", 70);
        basketManager.addToBasket("C001","P002", "Product2", "10", 35);

        basketManager.emptyBasket();
        assertTrue(basketManager.isBasketEmpty());
        assertEquals(50, productManager.getQuantity("P001")); // Should restore inventory
        assertEquals(30, productManager.getQuantity("P002"));
        assertEquals("", basketManager.currentUser);

    }

    @Test
    void testCustomerCheckout() {
        //Init Customer
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInventoryItem("P002", "Product2", "30", "35");
        basketManager.addToBasket("C001","P001", "Product1", "10", 70);
        basketManager.addToBasket("C001","P002", "Product2", "10", 35);

        basketManager.checkoutBasket();
        assertTrue(basketManager.isBasketEmpty());
        assertEquals(40, productManager.getQuantity("P001")); // Should NOT restore inventory
        assertEquals(20, productManager.getQuantity("P002"));
        assertTrue( basketManager.currentUser.isEmpty() );


        //Invalid
        basketManager.currentUser = "S001";
    }

    @Test
    void testSupplierCheckout() {
        //Init Supplier
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInventoryItem("P002", "Product2", "30", "35");
        basketManager.addToBasket("S001","P001", "Product1", "10", 70);
        basketManager.addToBasket("S001","P002", "Product2", "10", 35);

        basketManager.checkoutBasket();
        assertTrue(basketManager.isBasketEmpty());
        assertEquals(50, productManager.getQuantity("P001")); // Should restore inventory
        assertEquals(30, productManager.getQuantity("P002"));
        assertTrue( basketManager.currentUser.isEmpty() );
    }

    @Test
    void testCreateSale() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        basketManager.addToBasket("C001","P001", "Product1", "10", 70);

        Transaction transaction = basketManager.createTransaction("T001");
        assertInstanceOf(Sale.class, transaction);
    }

    @Test
    void testCreatePurchase() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        basketManager.addToBasket("S001","P001", "Product1", "10", 70);

        Transaction transaction = basketManager.createTransaction("T001");
        assertInstanceOf(Purchase.class, transaction);
    }
}