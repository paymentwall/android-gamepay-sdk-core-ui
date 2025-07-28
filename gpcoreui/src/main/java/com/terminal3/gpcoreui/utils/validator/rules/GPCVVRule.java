package com.terminal3.gpcoreui.utils.validator.rules;

public class GPCVVRule extends GPBaseRule {

    public GPCVVRule(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        // Remove all non-digit characters
        String cleaned = input.replaceAll("[^0-9]", "");

        // Check if length is 3 or 4 and all digits
        return (cleaned.length() == 3 || cleaned.length() == 4) && cleaned.matches("\\d+");
    }
}
