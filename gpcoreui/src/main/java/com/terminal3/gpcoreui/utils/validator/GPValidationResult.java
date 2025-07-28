package com.terminal3.gpcoreui.utils.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GPValidationResult {
    private final Map<GPValidatable, List<String>> errors;

    public GPValidationResult(Map<GPValidatable, List<String>> errors) {
        this.errors = errors;
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public Map<GPValidatable, List<String>> getAllErrors() {
        return errors;
    }

    public List<String> getErrorsForField(GPValidatable field) {
        return errors.getOrDefault(field, new ArrayList<>());
    }

    public String getFirstErrorForField(GPValidatable field) {
        List<String> fieldErrors = getErrorsForField(field);
        return fieldErrors.isEmpty() ? null : fieldErrors.get(0);
    }
}

