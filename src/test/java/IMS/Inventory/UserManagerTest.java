package IMS.Inventory;
import IMS.Products.Product;
import IMS.UI.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager manager;
    private InputValidator inputValidator;

    @BeforeEach
    void setUp() {
        manager = new UserManager();
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
}
