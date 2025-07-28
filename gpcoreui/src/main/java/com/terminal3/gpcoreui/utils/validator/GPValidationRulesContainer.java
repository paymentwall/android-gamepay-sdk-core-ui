package com.terminal3.gpcoreui.utils.validator;

import java.util.HashMap;
import java.util.Map;

public class GPValidationRulesContainer {
    private final Map<GPValidationRule, String> rules = new HashMap<>();

    public void addRule(GPValidationRule rule, String errorMessage) {
        rules.put(rule, errorMessage);
    }

    public Map<GPValidationRule, String> getRules() {
        return rules;
    }

    public String getErrorMessage(GPValidationRule rule) {
        return rules.get(rule);
    }
}

