package IMS.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;

    @BeforeEach
    void setUp() {
        userManager = new UserManager();
    }

    @Test
    void testAddCustomer() {
        //Init
        String output;

        // Valid
        userManager.addCustomer("c001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com");
        assertEquals(1, userManager.getCustomers().size());
        assertEquals("C001", userManager.getCustomers().getFirst().getID());
        assertEquals("Cus Tomer", userManager.getCustomers().getFirst().getName());
        assertEquals("101 Made Up Lane", userManager.getCustomers().getFirst().getAddress());
        assertEquals("Cus@tomer.com", userManager.getCustomers().getFirst().getEmail());

        // Invalid
        output = userManager.addCustomer("C001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com");
        assertEquals("Customer with ID C001 exists.", output);

        output = userManager.addCustomer("C002", "", "101 Made Up Lane", "Cus@tomer.com");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getCustomers().size());

        output = userManager.addCustomer("C002", "Cus Tomer", "", "Cus@tomer.com");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getCustomers().size());

        output = userManager.addCustomer("C002", "Cus Tomer", "101 Made Up Lane", "");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getCustomers().size());
    }

    @Test
    void testAddSupplier() {
        //Init
        String output;

        // Valid
        userManager.addSupplier("s001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com", "Department");
        assertEquals(1, userManager.getSuppliers().size());
        assertEquals("S001", userManager.getSuppliers().getFirst().getID());
        assertNotEquals("s001", userManager.getSuppliers().getFirst().getID());
        assertEquals("Cus Tomer", userManager.getSuppliers().getFirst().getName());
        assertEquals("101 Made Up Lane", userManager.getSuppliers().getFirst().getAddress());
        assertEquals("Cus@tomer.com", userManager.getSuppliers().getFirst().getEmail());
        assertEquals("Department", userManager.getSuppliers().getFirst().getDepartment());

        // Invalid
        output = userManager.addSupplier("s001", "Cus Tomer", "101 Made Up Lane", "Cus@tomer.com", "Department");
        assertEquals("Supplier with ID s001 exists.", output);

        output = userManager.addSupplier("s002", "", "101 Made Up Lane", "Cus@tomer.com", "Department");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getSuppliers().size());

        output = userManager.addSupplier("s002", "Cus Tomer", "", "", "Department");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getSuppliers().size());

        output = userManager.addSupplier("s002", "Cus Tomer", "101 Made Up Lane", "", "Department");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getSuppliers().size());

        output = userManager.addSupplier("s002", "Cus Tomer", "101 Made Up Lane", "", "");
        assertEquals("Please enter all necessary fields.", output);
        assertEquals(1, userManager.getSuppliers().size());
    }

    @Test
    void testUpdateUser() {
        //Init
        userManager.addCustomer("C001", "Cus Tomer", "101 Made up Lane", "Cus@tomer.com");
        userManager.addSupplier("S001", "Cus Tomer", "101 Made up Lane", "Cus@tomer.com", "Department");

        //Valid
        String output = userManager.updateUser("C001", "Test Name", "", "");
        assertEquals("Test Name", userManager.getUserName("C001"));
        assertEquals("User Updated: C001", output);

        output = userManager.updateUser("c001", "Test lowercase", "", "");
        assertEquals("Test lowercase", userManager.getUserName("C001"));
        assertEquals("User Updated: c001", output);

        output = userManager.updateUser("C001", "", "New Address", "");
        assertEquals("New Address", userManager.getUserAddress("C001"));
        assertEquals("User Updated: C001", output);

        output = userManager.updateUser("C001", "", "", "new@email.com");
        assertEquals("new@email.com", userManager.getUserEmail("C001"));
        assertEquals("User Updated: C001", output);

        output = userManager.updateUser("C001", "", "Address2", "NewER@email.com");
        assertEquals("NewER@email.com", userManager.getUserEmail("C001"));
        assertEquals("Address2", userManager.getUserAddress("C001"));
        assertEquals("User Updated: C001", output);

        output = userManager.updateUser("S001", "", "", "new@email.com", "Department");
        assertEquals("new@email.com", userManager.getUserEmail("S001"));
        assertEquals("Department", userManager.getUserDepartment("S001"));
        assertEquals("User Updated: S001", output);

        output = userManager.updateUser("S001", "", "", "", "Department2");
        assertEquals("Department2", userManager.getUserDepartment("S001"));
        assertEquals("User Updated: S001", output);

        output = userManager.updateUser("s001", "", "", "", "lowercase");
        assertEquals("lowercase", userManager.getUserDepartment("S001"));
        assertEquals("User Updated: s001", output);

        //Invalid
        output = userManager.updateUser("C001", "", "", "");
        assertEquals("Error: All fields are empty for [C001]", output);

        output = userManager.updateUser("C002", "shouldnt work", "", "");
        assertEquals("Error: User with ID [C002] does not exist.", output);

        output = userManager.updateUser("S001", "", "", "", "");
        assertEquals("Error: All fields are empty for [S001]", output);

        output = userManager.updateUser("S002", "shouldnt work", "", "", "");
        assertEquals("Error: User with ID [S002] does not exist.", output);

    }
}
