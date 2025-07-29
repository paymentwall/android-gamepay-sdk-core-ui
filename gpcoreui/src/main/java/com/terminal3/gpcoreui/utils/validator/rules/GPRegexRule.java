package com.terminal3.gpcoreui.utils.validator.rules;

import java.util.regex.Pattern;

/**
 * Validation rule based on a regular expression.
 */
public class GPRegexRule extends GPBaseRule {
    private final Pattern pattern;

    /**
     * Creates a new regex rule.
     *
     * @param regex        Regular expression pattern to match.
     * @param errorMessage Error message to display when validation fails.
     */
    public GPRegexRule(String regex, String errorMessage) {
        super(errorMessage);
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String input) {
        return input != null && pattern.matcher(input).matches();
    }
}
