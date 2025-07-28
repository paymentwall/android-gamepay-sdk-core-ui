package com.terminal3.gpcoreui.utils.validator.rules;

public class GPCreditCardNumberRule extends GPBaseRule {
    public GPCreditCardNumberRule(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        // Remove all non-digit characters
        String cleaned = input.replaceAll("[^0-9]", "");

        // Basic length check (13-19 digits)
        if (cleaned.length() < 13 || cleaned.length() > 19) {
            return false;
        }

        // Luhn Algorithm implementation
        int sum = 0;
        boolean alternate = false;
        for (int i = cleaned.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cleaned.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }
}
