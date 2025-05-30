package IMS.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputValidatorTest {
    private InputValidator manager;

    @BeforeEach
    void setUp() {
        manager = new InputValidator();
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
        assertEquals("", output);

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
}
