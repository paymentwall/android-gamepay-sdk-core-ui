package com.terminal3.gpcoreui.utils.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPValidator {
    private Map<GPValidatable, List<GPValidationRule>> validationRules = new HashMap<>();
    private boolean autoDisplayError;

    private GPValidator(boolean autoDisplayError) {
        this.autoDisplayError = autoDisplayError;
    }

    public void addRule(GPValidatable field, GPValidationRule rule) {
        if (!validationRules.containsKey(field)) {
            validationRules.put(field, new ArrayList<>());
        }
        validationRules.get(field).add(rule);
    }

    /**
     * Adds a list of validation rules to be applied to a specific field.
     *
     * @param field The {@link GPValidatable} field to which the rules will be added.
     * @param rules A list of {@link GPValidationRule} objects to be applied to the field.
     */
    public void addRules(GPValidatable field, List<GPValidationRule> rules) {
        for (GPValidationRule rule : rules) {
            addRule(field, rule);
        }
    }

    /**
     * Removes any existing validation rules for the given field and adds the new rules.
     *
     * @param field The {@link GPValidatable} field to replace and add rules for.
     * @param rules A list of {@link GPValidationRule} objects to be applied to the field.
     */
    public void replaceAndAddRules(GPValidatable field, List<GPValidationRule> rules) {
        validationRules.remove(field);
        addRules(field, rules);
    }

    public GPValidationResult validate() {
        Map<GPValidatable, List<String>> errors = new HashMap<>();

        // First clear all errors
        validationRules.keySet().forEach(field -> {
            if (field instanceof GPErrorDisplayable) {
                ((GPErrorDisplayable) field).clearError();
            }
        });

        for (Map.Entry<GPValidatable, List<GPValidationRule>> entry : validationRules.entrySet()) {
            GPValidatable field = entry.getKey();
            List<GPValidationRule> rules = entry.getValue();
            List<String> fieldErrors = new ArrayList<>();

            for (GPValidationRule rule : rules) {
                if (!rule.isValid(field.getInput())) {
                    fieldErrors.add(rule.getErrorMessage());
                    if (autoDisplayError && field instanceof GPErrorDisplayable) {
                        // Display first error immediately and stop checking other rules
                        ((GPErrorDisplayable) field).setErrorMessage(rule.getErrorMessage());
                        break;
                    }
                }
            }

            if (!fieldErrors.isEmpty()) {
                errors.put(field, fieldErrors);
            }
        }

        return new GPValidationResult(errors);
    }

    public static class Builder {
        private boolean autoDisplayError;

        /**
         * Sets whether errors should be automatically displayed on the fields.
         * If set to true, the first validation error encountered for a field
         * will be displayed immediately on that field (if it implements {@link GPErrorDisplayable}),
         * and no further rules for that field will be checked.
         * If false, all rules for all fields will be checked, and errors can be retrieved
         * from the {@link GPValidationResult}.
         *
         * @param autoDisplayError true to automatically display errors, false otherwise.
         * @return This Builder instance for chaining.
         */
        public Builder setAutoDisplayError(boolean autoDisplayError) {
            this.autoDisplayError = autoDisplayError;
            return this;
        }

        public GPValidator build() {
            return new GPValidator(autoDisplayError);
        }
    }
}

