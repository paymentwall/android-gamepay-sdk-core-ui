package com.terminal3.gpcoreui.utils.validator.rules;
import com.terminal3.gpcoreui.utils.validator.GPValidationRule;

import java.util.Calendar;

public class GPExpiryDateRule implements GPValidationRule {
    private final String errorMessage;
    private final boolean allowPastDates;

    public GPExpiryDateRule(String errorMessage) {
        this(errorMessage, false);
    }

    public GPExpiryDateRule(String errorMessage, boolean allowPastDates) {
        this.errorMessage = errorMessage;
        this.allowPastDates = allowPastDates;
    }

    @Override
    public boolean isValid(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        // Remove all non-digit characters
        String digitsOnly = input.replaceAll("[^\\d]", "");

        // Must be exactly 4 digits (MMYY)
        if (digitsOnly.length() != 4) {
            return false;
        }

        try {
            int month = Integer.parseInt(digitsOnly.substring(0, 2));
            int year = Integer.parseInt(digitsOnly.substring(2));

            // Basic month validation
            if (month < 1 || month > 12) {
                return false;
            }

            if (!allowPastDates) {
                // Get current date
                Calendar now = Calendar.getInstance();
                int currentYear = now.get(Calendar.YEAR) % 100; // Last 2 digits
                int currentMonth = now.get(Calendar.MONTH) + 1; // 1-12

                // Year is before current year
                if (year < currentYear) {
                    return false;
                }

                // Same year but month has passed
                if (year == currentYear && month < currentMonth) {
                    return false;
                }
            }

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}
