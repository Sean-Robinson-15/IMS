package IMS.Inventory;
import IMS.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagerTest {

    private ProductManager manager;

    @BeforeEach
    void setUp() {
        manager = new ProductManager();
    }

    @Test
    void testAddInventoryItem() {
        // Test valid input
        String output = manager.addInventoryItem("P001", "TestProduct", "10", "19.99");
        assertEquals("Item added: P001 TestProduct", output);
        assertEquals(1, manager.getAllItems().size());
        assertEquals("P001", manager.getAllItems().getFirst().getID());

        assertEquals("TestProduct", manager.getName("P001"));
        assertEquals(10, manager.getQuantity("P001"));
        assertEquals(19.99, manager.getPrice("P001"), 0.00001);

        // Testing invalid inputs
        output = manager.addInventoryItem("P002", "TestProduct1", "10", "-19.99");
        assertEquals("Error: Price cannot be negative. Please try again.", output);

        output = manager.addInventoryItem("P002", "TestProduct1", "10", "hello world");
        assertEquals("Error: Price must be an integer. Please try again.", output);

        output = manager.addInventoryItem("P002", "TestProduct1", "-10", "19.99");
        assertEquals("Error: Quantity cannot be negative. Please try again.", output);

        output = manager.addInventoryItem("P001", "DuplicateProduct", "5", "9.99");
        assertEquals("ID P001 arleady exists", output);

        output = manager.addInventoryItem("P002", "", "10", "19.99");
        assertTrue(output.contains("Please fill in all fields"));

        output = manager.addInventoryItem("P002", "test", "", "19.99");
        assertTrue(output.contains("Please fill in all fields"));

        output = manager.addInventoryItem("P002", "test", "10", "");
        assertTrue(output.contains("Please fill in all fields"));

        // Verify item was added to inventory
        ArrayList<Product> inventory = manager.getAllItems();
        assertEquals(1, inventory.size());
        assertEquals("P001", inventory.getFirst().getID());
    }

    @Test
    void testUpdateItem() {
        //Init
        manager.addInventoryItem("P001", "Product1", "50", "70");
        String output;

        // Valid
        output = manager.getName("P001");
        assertEquals("Product1", output);

        Integer outputInt = manager.getQuantity("P001");
        assertEquals(50, outputInt);

        Double outputDouble = manager.getPrice("P001");
        assertEquals(70, outputDouble);

        manager.updateItem("P001", "Product1", "10", "70");
        assertEquals(10, manager.getQuantity("P001"));

        manager.updateItem("P001", 21);
        assertEquals(21, manager.getQuantity("P001"));

        manager.updateItem("P001", "Product1", "10", "420");
        assertEquals(420, manager.getPrice("P001"));

        manager.updateItem("P001", "Test1", "10", "420");
        assertEquals("Test1", manager.getName("P001"));

        // Invalid
        output = manager.updateItem("","Test1","10", "16");
        assertEquals("Error: Product Code [] doesnt exist.", output);

        output = manager.updateItem("test","Test1","10", "16");
        assertEquals("Error: Product Code [test] doesnt exist.", output);

        output = manager.updateItem("P001", "Product1", "-10", "70");
        assertNotEquals(-10, manager.getQuantity("P001"));
        assertEquals("Quantity cannot be negative. Please try again.", output);

        output = manager.updateItem("P001", "Product1", "string", "70");
        assertEquals("Quantity must be an integer. Please try again.", output);

        output = manager.updateItem("P001", "Product1", "10", "-70");
        assertNotEquals(-70, manager.getPrice("P001"));
        assertEquals("Price cannot be negative. Please try again.", output);

        output = manager.updateItem("P001", "Product1", "10", "string");
        assertEquals("Price must be an integer. Please try again.", output);



    }

    @Test
    void testRemoveItem() {
        //Init
        manager.addInventoryItem("P001", "Product1", "50", "70");
        String output;

        // Valid
        manager.removeItem("P001");
        assertEquals(0, manager.getAllItems().size());

        // Invalid
        output = manager.removeItem("P002");
        assertEquals("Error: Product Code [P002] doesnt exist.", output);

        output = manager.removeItem("");
        assertEquals("Error: Product Code is empty", output);
    }

    @Test
    void testReceiveItem() {
        //Init
        manager.addInventoryItem("P001", "Product1", "50", "70");
        manager.addInTransitItem(new Product("P009", "Product1", 55, 70));
        manager.addInTransitItem(new Product("P008", "Product1", 55, 70));
        manager.addInTransitItem(new Product("P007", "Product1", 55, 70));
        manager.addInTransitItem(new Product("P001", "Product1", 55, 70));

        //Invalid
        manager.receiveItem("P002", "50");
        assertEquals(0, manager.getQuantity("P002"));

        manager.receiveItem("P2", "50");
        assertEquals(0, manager.getQuantity("P002"));

        manager.receiveItem("", "50");
        assertEquals(0, manager.getQuantity(""));

        manager.receiveItem("P007", "-10");
        assertEquals(0, manager.getQuantity("P007"));
        assertEquals(55, manager.getInTransit().getFirst().getQuantity());

        manager.receiveItem("P007", "STRING");
        assertEquals(0, manager.getQuantity("P007"));
        assertEquals(55, manager.getInTransit().getFirst().getQuantity());

        //Valid
        manager.receiveItem("P008", "");//Blank == recieve all
        assertEquals(55, manager.getQuantity("P008"));

        manager.receiveItem("p009", "");//lowercase
        assertEquals(55, manager.getQuantity("p009"));

        manager.receiveItem("P007", "55");
        assertEquals(55, manager.getQuantity("P007")); //Checks inventory value
        assertEquals(1, manager.getInTransit().size());

        manager.receiveItem("P001", "40");
        assertEquals(90, manager.getQuantity("P001"));
        assertNotEquals(0, manager.getInTransit().size());
        assertEquals(15, manager.getInTransit().getFirst().getQuantity());

        manager.receiveItem("P001", "15");
        assertEquals(105, manager.getQuantity("P001"));
        assertEquals(0, manager.getInTransit().size());
    }

}