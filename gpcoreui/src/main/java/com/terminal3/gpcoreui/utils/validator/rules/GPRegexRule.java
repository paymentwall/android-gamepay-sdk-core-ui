package com.terminal3.gpcoreui.utils.validator.rules;

import java.util.regex.Pattern;

/**
 * Validation rule that checks input against a regular expression.
 */
public class GPRegexRule extends GPBaseRule {
    private final Pattern pattern;

    public GPRegexRule(String regex, String errorMessage) {
        super(errorMessage);
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String input) {
        if (input == null) return false;
        return pattern.matcher(input).matches();
    }
}
