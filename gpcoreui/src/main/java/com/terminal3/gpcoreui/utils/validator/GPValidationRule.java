package com.terminal3.gpcoreui.utils.validator;

public interface GPValidationRule {
    boolean isValid(String inputString);
    String getErrorMessage();
}
