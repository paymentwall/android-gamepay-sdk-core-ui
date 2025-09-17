package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPButtonState;

public class GPApplyCodeInputView extends LinearLayout {

    private GPDefaultInputContainer inputContainer;
    private GPPrimaryButton applyButton;
    private OnApplyClickListener applyClickListener;
    
    private String lastAppliedValue = "";
    private boolean isApplied = false;
    private final String originalButtonText = "Apply";
    private final String appliedButtonText = "Applied";

    public interface OnApplyClickListener {
        void onApplyClick(String inputValue);
    }

    public GPApplyCodeInputView(Context context) {
        super(context);
        init(context);
    }

    public GPApplyCodeInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPApplyCodeInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gp_apply_code_input_view, this, true);
        
        inputContainer = findViewById(R.id.gp_input_container);
        applyButton = findViewById(R.id.gp_apply_button);
        
        applyButton.setText(originalButtonText);
        
        applyButton.setOnClickListener(v -> {
            if (applyClickListener != null) {
                String inputValue = inputContainer.getInput();
                // Only allow apply if not already applied with same value
                if (!isApplied || !inputValue.equals(lastAppliedValue)) {
                    applyClickListener.onApplyClick(inputValue);
                }
            }
        });
        
        // Add TextWatcher to monitor input changes
        inputContainer.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputChange();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void setLabel(CharSequence text) {
        inputContainer.setLabel(text);
    }

    public void setHintText(CharSequence text) {
        inputContainer.setHintText(text);
    }

    public void setText(CharSequence text) {
        inputContainer.setText(text);
    }

    public String getText() {
        return inputContainer.getInput();
    }

    public void setHelperText(CharSequence text) {
        inputContainer.setHelperText(text);
    }

    public void setErrorMessage(CharSequence errorMessage) {
        inputContainer.setErrorMessage(errorMessage);
    }

    public void clearError() {
        inputContainer.clearError();
    }

    public void setLoading(boolean loading) {
        if (loading) {
            applyButton.setState(GPButtonState.LOADING);
        } else {
            applyButton.setState(GPButtonState.DEFAULT);
        }
    }

    public void setApplied(boolean applied) {
        this.isApplied = applied;
        if (applied) {
            lastAppliedValue = inputContainer.getInput();
            applyButton.setText(appliedButtonText);
            applyButton.setState(GPButtonState.INACTIVE);
        } else {
            applyButton.setText(originalButtonText);
            applyButton.setState(GPButtonState.DEFAULT);
        }
    }

    public void setOnApplyClickListener(OnApplyClickListener listener) {
        this.applyClickListener = listener;
    }

    public void setFocus() {
        inputContainer.setFocus();
    }

    public void clearFocus() {
        inputContainer.clearFocus();
    }
    
    private void checkInputChange() {
        String currentInput = inputContainer.getInput();
        if (!lastAppliedValue.isEmpty()) {
            if (currentInput.equals(lastAppliedValue)) {
                // User entered the same value that was previously applied
                if (!isApplied) {
                    applyButton.setText(appliedButtonText);
                    applyButton.setState(GPButtonState.INACTIVE);
                    isApplied = true;
                }
            } else {
                // User entered different value, allow re-apply
                if (isApplied) {
                    applyButton.setText(originalButtonText);
                    applyButton.setState(GPButtonState.DEFAULT);
                    isApplied = false;
                }
            }
        }
    }
    
    public boolean isApplied() {
        return isApplied;
    }
    
    public String getLastAppliedValue() {
        return lastAppliedValue;
    }
    
    public void clear() {
        lastAppliedValue = "";
        isApplied = false;
        applyButton.setText(originalButtonText);
        applyButton.setState(GPButtonState.DEFAULT);
        inputContainer.setText("");
        inputContainer.clearError();
    }
}