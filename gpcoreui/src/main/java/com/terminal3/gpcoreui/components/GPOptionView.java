package com.terminal3.gpcoreui.components;

import com.terminal3.gpcoreui.models.GPOption;
import com.terminal3.gpcoreui.utils.validator.GPValidatable;

/**
 * Interface that must be implemented by all dynamic input components
 * which are rendered based on server provided options.
 */
public interface GPOptionView extends GPValidatable {

    /**
     * Bind the option data to this view.
     */
    void bindOption(GPOption option);

    /**
     * Returns the identifier of the option.
     */
    String getOptionId();

    /**
     * Returns the current value of the input.
     */
    String getOptionValue();

    /**
     * Sets a listener that will be notified whenever the value changes.
     */
    void setOnOptionValueChangeListener(OnOptionValueChangeListener listener);

    /**
     * Listener for value change events.
     */
    interface OnOptionValueChangeListener {
        void onOptionValueChanged(String optionId, String value);
    }
}
