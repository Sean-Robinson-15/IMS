package IMS.Inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidatorTest {
    private InputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidator();
    }

    @Test
    void testConfirmInputs() {
        //Init
        TreeMap<String, String> inputs = new TreeMap<>();
        inputs.put("ID", "P001");
        inputs.put("name", "TestProduct");
        inputs.put("quantityStr", "10");
        inputs.put("priceStr", "70");

        //Valid
        String output = validator.confirmInputs(inputs);
        assertEquals("", output);

        //Invalid
        inputs.put("priceStr", "");
        output = validator.confirmInputs(inputs);
        assertEquals("Please fill in all fields. Empty fields: [priceStr]", output);

        inputs.put("priceStr", "-10");
        output = validator.confirmInputs(inputs);
        assertEquals("Price cannot be negative. Please try again.", output);

        inputs.put("priceStr", "hello world");
        output = validator.confirmInputs(inputs);
        assertEquals("Price must be an integer. Please try again.", output);
    }

        @Test
        void testValidateInteger() {
            //Valid
            String output = validator.validateInt("21", "Variable");
            assertEquals("", output );

            //Invalid
            output = validator.validateInt("Not an Int", "Variable");
            assertEquals("Variable must be an integer. Please try again.", output );

            output = validator.validateInt("-21", "Variable");
            assertEquals("Variable cannot be negative. Please try again.", output );

        }
}
