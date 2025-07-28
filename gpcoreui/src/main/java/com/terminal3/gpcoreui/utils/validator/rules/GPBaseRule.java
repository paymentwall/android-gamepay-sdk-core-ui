package com.terminal3.gpcoreui.utils.validator.rules;

import com.terminal3.gpcoreui.utils.validator.GPValidationRule;

public abstract class GPBaseRule implements GPValidationRule {
    protected String errorMessage;

    public GPBaseRule(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public abstract boolean isValid(String input);
}
