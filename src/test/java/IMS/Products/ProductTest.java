package IMS.Products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("P001", "Test Product", 50, 70);
    }

    @Test
    void testGetID() {
        String output = product.getID();
        assertEquals("P001", output);
    }

    @Test
    void testGetName() {
        String output = product.getName();
        assertEquals("Test Product", output);

    }

    @Test
    void testGetPrice() {
        double output = product.getPrice();
        assertEquals(70, output, 0.0001);
    }

    @Test
    void testGetQuantity() {
        int output = product.getQuantity();
        assertEquals(50, output);
    }

    @Test
    void testSetName() {
        product.setName("Test Product 2");
        String output = product.getName();
        assertEquals("Test Product 2", output);
    }

    @Test
    void testSetPrice() {
        product.setPrice(100);
        double output = product.getPrice();
        assertEquals(100, output, 0.0001);
    }

    @Test
    void testSetQuantity() {
        product.setQuantity(100);
        int output = product.getQuantity();
        assertEquals(100, output);
    }
}

