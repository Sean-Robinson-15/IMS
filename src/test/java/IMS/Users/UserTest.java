package IMS.Users;

import IMS.Managers.TransactionManager;
import IMS.Managers.UserManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private Customer customer;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        customer = new Customer("C001", "Cus Tomer", "101 Made Up Lane", "customer@email.com");
        supplier = new Supplier("S001", "Supp Lier", "202 Not Real Road", "Supplier@email.com", "Department");
    }

    @Test
    void testCustomerGetters() {
        //Valid
        String output = customer.getID();
        assertEquals("C001", output);

        output = customer.getName();
        assertEquals("Cus Tomer", output);


        output = customer.getAddress();
        assertEquals("101 Made Up Lane", output);

        output = customer.getEmail();
        assertEquals("customer@email.com", output);

    }

    @Test
    void testSupplierGetters() {
        String output = supplier.getID();
        assertEquals("S001", output);

        output = supplier.getName();
        assertEquals("Supp Lier", output);

        output = supplier.getAddress();
        assertEquals("202 Not Real Road", output);

        output = supplier.getEmail();
        assertEquals("Supplier@email.com", output);

        output = supplier.getDepartment();
        assertEquals("Department", output);

    }

    @Test
    void testCustomerSettersValid() {
        customer.setName("Cus Tomer 2");
        assertEquals("Cus Tomer 2", customer.getName());

        customer.setEmail("<EMAIL>");
        assertEquals("<EMAIL>", customer.getEmail());

        customer.setAddress("101 Made Up Lane 2");
        assertEquals("101 Made Up Lane 2", customer.getAddress());

    }


    @Test
    void testSupplierSettersValid() {
        supplier.setName("Supp Lier 2");
        assertEquals("Supp Lier 2", supplier.getName());

        supplier.setAddress("202 Not Real Road 2");
        assertEquals("202 Not Real Road 2", supplier.getAddress());

        supplier.setDepartment("Department 2");
        assertEquals("Department 2", supplier.getDepartment());
    }

    @Test
    void testCustomerSettersInvalid() {
        //Invalid
        customer.setName("");
        assertEquals("Cus Tomer", customer.getName());
    }

    @Test
    void testSupplierSettersInvalid() {
        //Invalid
        supplier.setName("");
        assertEquals("Supp Lier", supplier.getName());
    }

}
