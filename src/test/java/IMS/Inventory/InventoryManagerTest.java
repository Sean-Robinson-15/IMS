package IMS.Inventory;
import IMS.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class InventoryManagerTest {

    private InventoryManager manager;

    @BeforeEach
    void setUp() {
        manager = new InventoryManager();
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
        assertEquals("Price cannot be negative. Please try again.", output);

        output = manager.addInventoryItem("P002", "TestProduct1", "10", "hello world");
        assertEquals("Price must be an integer. Please try again.", output);

        output = manager.addInventoryItem("P002", "TestProduct1", "-10", "19.99");
        assertEquals("Quantity cannot be negative. Please try again.", output);

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
    void testAddToBasket() {
        // Valid
        manager.addInventoryItem("P001", "Product1", "50", "69");
        String output = manager.addToBasket("P001", "Product1", "10", 69);
        assertEquals(1, manager.getBasket().size());
        assertEquals(10, manager.getBasket().getFirst().getQuantity());
        assertEquals(40, manager.getQuantity("P001"));
        assertEquals("P001 added to basket. \n", output);

        // Test dupe ID (adds quantity together with currently in basket)
        manager.addToBasket("P001", "Product1", "10", 69);
        assertEquals(20, manager.getBasket().getFirst().getQuantity());
        assertEquals(30, manager.getQuantity("P001"));
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


    @Test
    void testConfirmInputs() {
        //Init
        HashMap<String, String> inputs = new HashMap<>();
        inputs.put("ID", "P001");
        inputs.put("name", "TestProduct");
        inputs.put("quantityStr", "10");
        inputs.put("priceStr", "69");

        //Valid
        String output = manager.confirmInputs(inputs);
        assertEquals("", output );

        //Invalid
        inputs.put("priceStr", "");
        output = manager.confirmInputs(inputs);
        assertEquals("Please fill in all fields. Empty fields: [priceStr]", output);

        inputs.put("priceStr", "-10");
        output = manager.confirmInputs(inputs);
        assertEquals("Price cannot be negative. Please try again.", output);

        inputs.put("priceStr", "hello world");
        output = manager.confirmInputs(inputs);
        assertEquals("Price must be an integer. Please try again.", output);
    }

    @Test
    void testValidateInteger() {
        //Valid
        String output = manager.validateInt("21", "Variable");
        assertEquals("", output );

        //Invalid
        output = manager.validateInt("Not an Int", "Variable");
        assertEquals("Variable must be an integer. Please try again.", output );

        output = manager.validateInt("-21", "Variable");
        assertEquals("Variable cannot be negative. Please try again.", output );

    }
    @Test
    void testUpdateItem() {
        //Init
        manager.addInventoryItem("P001", "Product1", "50", "69");
        String output = "";

        // Valid
        manager.updateItem("P001", "Product1", "10", "69");
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
        assertEquals("Error: Product Code [TEST] doesnt exist.", output);

        output = manager.updateItem("P001", "Product1", "-10", "69");
        assertNotEquals(-10, manager.getQuantity("P001"));
        assertEquals("Quantity cannot be negative. Please try again.", output);

        output = manager.updateItem("P001", "Product1", "string", "69");
        assertEquals("Quantity must be an integer. Please try again.", output);

        output = manager.updateItem("P001", "Product1", "10", "-69");
        assertNotEquals(-69, manager.getPrice("P001"));
        assertEquals("Price cannot be negative. Please try again.", output);

        output = manager.updateItem("P001", "Product1", "10", "string");
        assertEquals("Price must be an integer. Please try again.", output);

    }
    @Test
    void testAddCustomer() {
        //Init
        String output = "";

        // Valid
        manager.addCustomer("c001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com");
        assertEquals(1, manager.getCustomers().size());
        assertEquals("C001", manager.getCustomers().getFirst().getID());
        assertEquals("Cus Tomer", manager.getCustomers().getFirst().getName());
        assertEquals("101 Made Up Lane", manager.getCustomers().getFirst().getAddress());
        assertEquals("Cus@tomer.com", manager.getCustomers().getFirst().getEmail());

        // Invalid
        output = manager.addCustomer("C001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com");
        assertEquals("Customer with ID C001 already exists.", output);

        output = manager.addCustomer("C002", "", "101 Made Up Lane", "Cus@tomer.com");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getCustomers().size());

        output = manager.addCustomer("C002", "Cus Tomer", "", "Cus@tomer.com");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getCustomers().size());

        output = manager.addCustomer("C002", "Cus Tomer", "101 Made Up Lane", "");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getCustomers().size());
    }

    @Test
    void testAddSupplier() {
        //Init
        String output = "";

        // Valid
        manager.addSupplier("s001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com", "Department");
        assertEquals(1, manager.getSuppliers().size());
        assertEquals("S001", manager.getSuppliers().getFirst().getID());
        assertNotEquals("s001", manager.getSuppliers().getFirst().getID());
        assertEquals("Cus Tomer", manager.getSuppliers().getFirst().getName());
        assertEquals("101 Made Up Lane", manager.getSuppliers().getFirst().getAddress());
        assertEquals("Cus@tomer.com", manager.getSuppliers().getFirst().getEmail());
        assertEquals("Department", manager.getSuppliers().getFirst().getDepartment());

        // Invalid
        output = manager.addSupplier("s001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com", "Department");
        assertEquals("Supplier with ID S001 already exists.", output);

        output = manager.addSupplier("s002", "", "101 Made Up Lane", "Cus@tomer.com", "Department");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getSuppliers().size());

        output = manager.addSupplier("s002", "Cus Tomer", "", "", "Department");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getSuppliers().size());

        output = manager.addSupplier("s002", "Cus Tomer", "101 Made Up Lane", "", "Department");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getSuppliers().size());

        output = manager.addSupplier("s002", "Cus Tomer", "101 Made Up Lane", "", "");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, manager.getSuppliers().size());
    }

    @Test
    void testUpdateUser() {
        //Init
        manager.addCustomer("C001", "Cus Tomer", "101 Made up Lane", "Cus@tomer.com");
        manager.addSupplier("S001", "Cus Tomer", "101 Made up Lane", "Cus@tomer.com", "Department");

        //Valid
        String output = manager.updateUser("C001", "Test Name", "", "");
        assertEquals("Test Name", manager.getUserName("C001"));
        assertEquals("User Updated: C001", output);

        output = manager.updateUser("c001", "Test lowercase", "", "");
        assertEquals("Test lowercase", manager.getUserName("C001"));
        assertEquals("User Updated: C001", output);

        output = manager.updateUser("C001", "", "New Address", "");
        assertEquals("New Address", manager.getUserAddress("C001"));
        assertEquals("User Updated: C001", output);

        output = manager.updateUser("C001", "", "", "new@email.com");
        assertEquals("new@email.com", manager.getUserEmail("C001"));
        assertEquals("User Updated: C001", output);

        output = manager.updateUser("C001", "", "Address2", "NewER@email.com");
        assertEquals("NewER@email.com", manager.getUserEmail("C001"));
        assertEquals("Address2", manager.getUserAddress("C001"));
        assertEquals("User Updated: C001", output);

        output = manager.updateUser("S001", "", "", "new@email.com", "Department");
        assertEquals("new@email.com", manager.getUserEmail("S001"));
        assertEquals("Department", manager.getUserDepartment("S001"));
        assertEquals("User Updated: S001", output);

        output = manager.updateUser("S001", "", "", "", "Department2");
        assertEquals("Department2", manager.getUserDepartment("S001"));
        assertEquals("User Updated: S001", output);

        output = manager.updateUser("s001", "", "", "", "lowercase");
        assertEquals("lowercase", manager.getUserDepartment("S001"));
        assertEquals("User Updated: S001", output);

        //Invalid
        output = manager.updateUser("C001", "", "", "");
        assertEquals("Error: All fields are empty for [C001]", output);

        output = manager.updateUser("C002", "", "", "");
        assertEquals("Error: User ID [C002] doesnt exist.", output);

        output = manager.updateUser("S001", "", "", "", "");
        assertEquals("Error: All fields are empty for [S001]", output);

        output = manager.updateUser("S002", "", "", "", "");
        assertEquals("Error: User ID [S002] doesnt exist.", output);

    }

    @Test
    void testRemoveItem() {
        //Init
        manager.addInventoryItem("P001", "Product1", "50", "69");
        String output = "";

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
    void testGetAllItems() {
        //Init
    }
}