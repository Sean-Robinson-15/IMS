package IMS.Inventory;
import IMS.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {

    private ProductManager productManager;

    @BeforeEach
    void setUp() {
        productManager = new ProductManager();
    }

    @Test
    void testAddInventoryItem() {
        // Test valid input
        String output = productManager.addInventoryItem("P001", "TestProduct", "10", "19.99");
        assertEquals("Item added: P001 TestProduct", output);
        assertEquals(1, productManager.getAllItems().size());
        assertEquals("P001", productManager.getAllItems().getFirst().getID());

        assertEquals("TestProduct", productManager.getName("P001"));
        assertEquals(10, productManager.getQuantity("P001"));
        assertEquals(19.99, productManager.getPrice("P001"), 0.00001);

        // Testing invalid inputs
        output = productManager.addInventoryItem("P002", "TestProduct1", "10", "-19.99");
        assertEquals("Error: Price cannot be negative. Please try again.", output);

        output = productManager.addInventoryItem("P002", "TestProduct1", "10", "hello world");
        assertEquals("Error: Price must be an integer. Please try again.", output);

        output = productManager.addInventoryItem("P002", "TestProduct1", "-10", "19.99");
        assertEquals("Error: Quantity cannot be negative. Please try again.", output);

        output = productManager.addInventoryItem("P001", "DuplicateProduct", "5", "9.99");
        assertEquals("ID P001 arleady exists", output);

        output = productManager.addInventoryItem("P002", "", "10", "19.99");
        assertTrue(output.contains("Please fill in all fields"));

        output = productManager.addInventoryItem("P002", "test", "", "19.99");
        assertTrue(output.contains("Please fill in all fields"));

        output = productManager.addInventoryItem("P002", "test", "10", "");
        assertTrue(output.contains("Please fill in all fields"));

        // Verify item was added to inventory
        ArrayList<Product> inventory = productManager.getAllItems();
        assertEquals(1, inventory.size());
        assertEquals("P001", inventory.getFirst().getID());
    }

    @Test
    void testUpdateItem() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        String output;

        // Valid
        output = productManager.getName("P001");
        assertEquals("Product1", output);

        Integer outputInt = productManager.getQuantity("P001");
        assertEquals(50, outputInt);

        Double outputDouble = productManager.getPrice("P001");
        assertEquals(70, outputDouble);

        productManager.updateItem("P001", "Product1", "10", "70");
        assertEquals(10, productManager.getQuantity("P001"));

        productManager.updateItem("P001", 21);
        assertEquals(21, productManager.getQuantity("P001"));

        productManager.updateItem("P001", "Product1", "10", "420");
        assertEquals(420, productManager.getPrice("P001"));

        productManager.updateItem("P001", "Test1", "10", "420");
        assertEquals("Test1", productManager.getName("P001"));

        // Invalid
        output = productManager.updateItem("","Test1","10", "16");
        assertEquals("Error: Product Code [] doesnt exist.", output);

        output = productManager.updateItem("test","Test1","10", "16");
        assertEquals("Error: Product Code [test] doesnt exist.", output);

        output = productManager.updateItem("P001", "Product1", "-10", "70");
        assertNotEquals(-10, productManager.getQuantity("P001"));
        assertEquals("Quantity cannot be negative. Please try again.", output);

        output = productManager.updateItem("P001", "Product1", "string", "70");
        assertEquals("Quantity must be an integer. Please try again.", output);

        output = productManager.updateItem("P001", "Product1", "10", "-70");
        assertNotEquals(-70, productManager.getPrice("P001"));
        assertEquals("Price cannot be negative. Please try again.", output);

        output = productManager.updateItem("P001", "Product1", "10", "string");
        assertEquals("Price must be an integer. Please try again.", output);



    }

    @Test
    void testRemoveItem() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        String output;

        // Valid
        productManager.removeItem("P001");
        assertEquals(0, productManager.getAllItems().size());

        // Invalid
        output = productManager.removeItem("P002");
        assertEquals("Error: Product Code [P002] doesnt exist.", output);

        output = productManager.removeItem("");
        assertEquals("Error: Product Code is empty", output);
    }

    @Test
    void testReceiveItem() {
        //Init
        productManager.addInventoryItem("P001", "Product1", "50", "70");
        productManager.addInTransitItem(new Product("P009", "Product1", 55, 70));
        productManager.addInTransitItem(new Product("P008", "Product1", 55, 70));
        productManager.addInTransitItem(new Product("P007", "Product1", 55, 70));
        productManager.addInTransitItem(new Product("P001", "Product1", 55, 70));

        //Invalid
        productManager.receiveItem("P002", "50");
        assertEquals(0, productManager.getQuantity("P002"));

        productManager.receiveItem("P2", "50");
        assertEquals(0, productManager.getQuantity("P002"));

        productManager.receiveItem("", "50");
        assertEquals(0, productManager.getQuantity(""));

        productManager.receiveItem("P007", "-10");
        assertEquals(0, productManager.getQuantity("P007"));
        assertEquals(55, productManager.getInTransit().getFirst().getQuantity());

        productManager.receiveItem("P007", "STRING");
        assertEquals(0, productManager.getQuantity("P007"));
        assertEquals(55, productManager.getInTransit().getFirst().getQuantity());

        //Valid
        productManager.receiveItem("P008", "");//Blank == recieve all
        assertEquals(55, productManager.getQuantity("P008"));

        productManager.receiveItem("p009", "");//lowercase
        assertEquals(55, productManager.getQuantity("p009"));

        productManager.receiveItem("P007", "55");
        assertEquals(55, productManager.getQuantity("P007")); //Checks inventory value
        assertEquals(1, productManager.getInTransit().size());

        productManager.receiveItem("P001", "40");
        assertEquals(90, productManager.getQuantity("P001"));
        assertNotEquals(0, productManager.getInTransit().size());
        assertEquals(15, productManager.getInTransit().getFirst().getQuantity());

        productManager.receiveItem("P001", "15");
        assertEquals(105, productManager.getQuantity("P001"));
        assertEquals(0, productManager.getInTransit().size());
    }

}