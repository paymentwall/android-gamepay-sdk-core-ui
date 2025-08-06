package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.terminal3.gpcoreui.enums.GPOptionType;
import com.terminal3.gpcoreui.models.GPOption;
import com.terminal3.gpcoreui.utils.GPHelper;
import com.terminal3.gpcoreui.utils.textwatchers.GPEpinTextWatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container that dynamically renders input fields based on a list of {@link GPOption}.
 * Each option is bound to a {@link GPOptionView} implementation.
 */
public class GPDynamicForm extends LinearLayout implements GPOptionView.OnOptionValueChangeListener {

    private final Map<String, GPOptionView> optionViews = new HashMap<>();
    private OnFormValueChangedListener formListener;

    public GPDynamicForm(Context context) {
        super(context);
        init();
    }

    public GPDynamicForm(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GPDynamicForm(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }

    /**
     * Binds a list of options and creates corresponding input views.
     */
    public void setOptions(List<GPOption> options) {
        removeAllViews();
        optionViews.clear();
        if (options == null) return;

        for (GPOption option : options) {
            GPOptionView view;
            if (option.getType() == GPOptionType.DROPDOWN) {
                GPDropdown dropdown = new GPDropdown(getContext());
                dropdown.bindOption(option);
                view = dropdown;
            } else if (option.getType() == GPOptionType.REDIRECT) {
                GPRedirectionView redirect = new GPRedirectionView(getContext());
                redirect.bindOption(option);
                view = redirect;
            } else {
                GPDefaultInputContainer input = new GPDefaultInputContainer(getContext());
                input.bindOption(option);
                view = input;
                if (option.getType() == GPOptionType.EPIN) {
                    input.addTextWatcher(new GPEpinTextWatcher());
                }
            }
            view.setOnOptionValueChangeListener(this);
            optionViews.put(option.getId(), view);
            addView((View) view);
        }
    }

    @Override
    public void onOptionValueChanged(String optionId, String value) {
        if (formListener != null) {
            formListener.onValueChanged(optionId, value);
        }
    }

    /**
     * Returns the current values for all options in this form.
     */
    public Map<String, String> getValues() {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, GPOptionView> entry : optionViews.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getOptionValue());
        }
        return result;
    }

    /**
     * Retrieves the {@link GPOptionView} associated with the given option ID.
     * This allows callers to access the underlying input view for validation
     * or additional customization.
     */
    public GPOptionView getOptionView(String optionId) {
        return optionViews.get(optionId);
    }

    /**
     * Sets a listener that will be notified whenever any option value changes.
     */
    public void setOnFormValueChangedListener(OnFormValueChangedListener listener) {
        this.formListener = listener;
    }

    /**
     * Listener for form-wide value change events.
     */
    public interface OnFormValueChangedListener {
        void onValueChanged(String optionId, String value);
    }
}
