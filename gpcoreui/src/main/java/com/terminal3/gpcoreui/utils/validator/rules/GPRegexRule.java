package com.terminal3.gpcoreui.utils.validator.rules;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Validation rule based on a regular expression.
 */
public class GPRegexRule extends GPBaseRule {
    private Pattern pattern;

    /**
     * Creates a new regex rule.
     *
     * @param regex        Regular expression pattern to match.
     * @param errorMessage Error message to display when validation fails.
     */
    public GPRegexRule(String regex, String errorMessage) {
        super(errorMessage);
        try {
            this.pattern = Pattern.compile(regex);
        } catch (PatternSyntaxException e) {
            this.pattern = Pattern.compile(".*");
        }
    }

    @Override
    public boolean isValid(String input) {
        return input != null && pattern.matcher(input).matches();
    }
}
