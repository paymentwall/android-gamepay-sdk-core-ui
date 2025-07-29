package com.terminal3.gpcoreui.utils.validator.rules;

import org.junit.Test;

import static org.junit.Assert.*;

public class GPRegexRuleTest {
    @Test
    public void emailRegex_validatesCorrectly() {
        String regex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
        GPRegexRule rule = new GPRegexRule(regex, "Invalid email");

        assertTrue(rule.isValid("user@example.com"));
        assertFalse(rule.isValid("not-an-email"));
    }
}
