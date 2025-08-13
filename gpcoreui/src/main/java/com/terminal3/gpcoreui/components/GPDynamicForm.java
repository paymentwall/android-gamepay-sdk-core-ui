package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.text.TextUtils;

import com.terminal3.gpcoreui.enums.GPOptionType;
import com.terminal3.gpcoreui.models.GPOption;
import com.terminal3.gpcoreui.models.GPOptionValidation;
import com.terminal3.gpcoreui.utils.textwatchers.GPEpinTextWatcher;
import com.terminal3.gpcoreui.utils.validator.GPValidator;
import com.terminal3.gpcoreui.utils.validator.GPValidationRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPRegexRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPRequiredRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container that dynamically renders input fields based on a list of {@link GPOption}.
 * Each option is bound to a {@link GPOptionView} implementation.
 */
public class GPDynamicForm extends LinearLayout implements GPOptionView.OnOptionValueChangeListener {

    //#region Fields
    private final Map<String, GPOptionView> optionViews = new HashMap<>();
    private final Map<String, Map<String, List<GPOption>>> groupedOptions = new HashMap<>();
    private final Map<String, List<String>> activeGroupOptionIds = new HashMap<>();
    private final Map<String, GPOption> allOptions = new HashMap<>();
    private OnFormValueChangedListener formListener;
    private GPValidator validator;
    private OnFormViewAddedListener viewAddedListener;
    //#endregion

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
        if (validator != null) {
            for (GPOptionView view : optionViews.values()) {
                validator.removeField(view);
            }
        }
        optionViews.clear();
        groupedOptions.clear();
        activeGroupOptionIds.clear();
        allOptions.clear();
        if (options == null) return;

        collectAllOptions(options);

        for (GPOption option : options) {
            if (option.getGroups() != null && !option.getGroups().isEmpty()) {
                groupedOptions.put(option.getId(), option.getGroups());
            }
            if (option.getType() == GPOptionType.GROUP) {
                continue;
            }
            GPOptionView view = createViewForOption(option);
            view.setOnOptionValueChangeListener(this);
            optionViews.put(option.getId(), view);
            addView((View) view);
            registerValidations(option, view);
            notifyViewAdded(view);

            if (groupedOptions.containsKey(option.getId()) && option.getValue() != null && !option.getValue().isEmpty()) {
                onOptionValueChanged(option.getId(), option.getValue());
            }
        }
    }

    @Override
    public void onOptionValueChanged(String optionId, String value) {
        if (groupedOptions.containsKey(optionId)) {
            List<String> existingIds = activeGroupOptionIds.remove(optionId);
            if (existingIds != null) {
                for (String id : existingIds) {
                    removeOptionAndChildren(id);
                }
            }

            Map<String, List<GPOption>> groups = groupedOptions.get(optionId);
            List<GPOption> toAdd = groups.get(value);
            if (toAdd != null) {
                int insertIndex = indexOfChild((View) optionViews.get(optionId)) + 1;
                List<String> newIds = new java.util.ArrayList<>();
                for (GPOption opt : toAdd) {
                    GPOptionView view = createViewForOption(opt);
                    view.setOnOptionValueChangeListener(this);
                    optionViews.put(opt.getId(), view);
                    addView((View) view, insertIndex++);
                    registerValidations(opt, view);
                    notifyViewAdded(view);
                    newIds.add(opt.getId());

                    if (opt.getGroups() != null && !opt.getGroups().isEmpty()) {
                        groupedOptions.put(opt.getId(), opt.getGroups());
                        if (opt.getValue() != null && !opt.getValue().isEmpty()) {
                            onOptionValueChanged(opt.getId(), opt.getValue());
                        }
                    }
                }
                activeGroupOptionIds.put(optionId, newIds);
            }
        }

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
     * Returns the current values for all options in this form, including those
     * not currently visible. Hidden options will return an empty string if no value is set.
     */
    public Map<String, String> getAllValues() {
        Map<String, String> result = new HashMap<>();
        for (String id : allOptions.keySet()) {
            GPOptionView view = optionViews.get(id);
            String value;
            if (view != null) {
                value = view.getOptionValue();
            } else {
                GPOption option = allOptions.get(id);
                value = option.getValue() != null ? option.getValue() : "";
            }
            result.put(id, value);
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
     * Sets the validator used to validate option views in this form.
     */
    public void setValidator(GPValidator validator) {
        this.validator = validator;
    }

    /**
     * Sets a listener that will be notified whenever new option views are added.
     */
    public void setOnFormViewAddedListener(OnFormViewAddedListener listener) {
        this.viewAddedListener = listener;
    }

    /**
     * Listener for form-wide value change events.
     */
    public interface OnFormValueChangedListener {
        void onValueChanged(String optionId, String value);
    }

    /**
     * Listener for newly added option views.
     */
    public interface OnFormViewAddedListener {
        void onViewAdded(GPOptionView view);
    }

    //#region Helpers
    private void collectAllOptions(List<GPOption> options) {
        if (options == null) return;
        for (GPOption option : options) {
            if (option.getType() != GPOptionType.GROUP) {
                allOptions.put(option.getId(), option);
            }
            if (option.getGroups() != null && !option.getGroups().isEmpty()) {
                for (List<GPOption> groupOptions : option.getGroups().values()) {
                    collectAllOptions(groupOptions);
                }
            }
        }
    }

    private GPOptionView createViewForOption(GPOption option) {
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
        return view;
    }

    private void registerValidations(GPOption option, GPOptionView view) {
        if (validator == null || option == null) return;
        List<GPValidationRule> rules = new ArrayList<>();
        String label = option.getLabel() != null ? option.getLabel() : option.getId();
        rules.add(new GPRequiredRule(label + " is required"));
        for (GPOptionValidation val : option.getValidations()) {
            if (!TextUtils.isEmpty(val.getRegex())) {
                rules.add(new GPRegexRule(val.getRegex(), val.getMessage()));
            }
        }
        validator.addRules(view, rules);
    }

    private void notifyViewAdded(GPOptionView view) {
        if (viewAddedListener != null) {
            viewAddedListener.onViewAdded(view);
        }
    }

    private void removeOptionAndChildren(String optionId) {
        groupedOptions.remove(optionId);
        GPOptionView view = optionViews.remove(optionId);
        if (view != null) {
            removeView((View) view);
            if (validator != null) {
                validator.removeField(view);
            }
        }
        List<String> childIds = activeGroupOptionIds.remove(optionId);
        if (childIds != null) {
            for (String childId : childIds) {
                removeOptionAndChildren(childId);
            }
        }
    }
    //#endregion
}
