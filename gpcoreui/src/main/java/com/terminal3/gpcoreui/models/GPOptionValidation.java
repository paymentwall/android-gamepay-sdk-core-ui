package com.terminal3.gpcoreui.models;

/**
 * Represents a validation rule for a server provided option.
 */
public class GPOptionValidation {
    private final String regex;
    private final String message;

    public GPOptionValidation(String regex, String message) {
        this.regex = regex;
        this.message = message;
    }

    public String getRegex() {
        return regex;
    }

    public String getMessage() {
        return message;
    }
}
