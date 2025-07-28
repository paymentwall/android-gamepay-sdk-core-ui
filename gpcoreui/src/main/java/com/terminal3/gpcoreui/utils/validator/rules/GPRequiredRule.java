package com.terminal3.gpcoreui.utils.validator.rules;

public class GPRequiredRule extends GPBaseRule {

    public GPRequiredRule(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }
}
