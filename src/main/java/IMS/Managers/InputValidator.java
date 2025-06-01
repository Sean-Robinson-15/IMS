package IMS.Managers;

import java.util.ArrayList;
import java.util.TreeMap;

public class InputValidator {
    public String confirmInputs(TreeMap<String, String> inputs) {
        ArrayList<String> emptyInputs = new ArrayList<>();
        for (String key : inputs.keySet()) {
            if (inputs.get(key).isEmpty()) {
                emptyInputs.add(key);
            }
        }
        if (!emptyInputs.isEmpty()) {
            return "Please fill in all fields. Empty fields: " + emptyInputs;
        }

        String output = validateInt(inputs.get("quantityStr"), "Quantity");
        if (!output.isEmpty()) return output;

        output = validateDouble(inputs.get("priceStr"), "Price");
        if (!output.isEmpty()) return output;

        return "";
    }

    public String validateInt(String input, String variableName) {
        try {
            int quantity = Integer.parseInt(input);
            if (quantity < 0) {
                return variableName + " cannot be negative. Please try again.";
            }
        } catch (NumberFormatException e) {
            return variableName + " must be an integer. Please try again.";
        }
        return "";
    }

    public String validateDouble(String input, String variableName) {
        try {
            double quantity = Double.parseDouble(input);
            if (quantity < 0) {
                return variableName + " cannot be negative. Please try again.";
            }
        } catch (NumberFormatException e) {
            return variableName + " must be an integer. Please try again.";
        }
        return "";
    }
}
