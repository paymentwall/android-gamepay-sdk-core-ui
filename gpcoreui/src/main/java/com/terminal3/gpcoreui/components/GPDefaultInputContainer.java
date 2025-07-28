package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.utils.textwatchers.GPTextWatcher;
import com.terminal3.gpcoreui.utils.validator.GPErrorDisplayable;
import com.terminal3.gpcoreui.utils.validator.GPValidatable;

public class GPDefaultInputContainer extends LinearLayout implements GPValidatable, GPErrorDisplayable {

    private TextView labelView;
    private GPDefaultEditText editText;

//    protected LinearLayout llEditTextContainer, llRightViews;

    private View errorView;
    private TextView errorTextView;
    private TextView helperView;
    protected GPTextWatcher _gpTextWatcher;

    public GPDefaultInputContainer(Context context) {
        super(context);
        init(context);
    }

    public GPDefaultInputContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GPDefaultInputContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    // region Init

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_payment_input_container, this, true);
        labelView = findViewById(R.id.gp_label);
        editText = findViewById(R.id.gp_edit_text);
        errorView = findViewById(R.id.gp_error);
        helperView = findViewById(R.id.gp_helper);
        initCustomConfig();
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_payment_input_container, this, true);
        labelView = findViewById(R.id.gp_label);
        editText = findViewById(R.id.gp_edit_text);
        errorView = findViewById(R.id.gp_error);
        errorTextView = findViewById(R.id.gp_error_text);
        helperView = findViewById(R.id.gp_helper);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GPDefaultInputContainer);

            try {
                // Set label text if provided
                CharSequence labelText = a.getText(R.styleable.GPDefaultInputContainer_labelText);
                if (labelText != null) {
                    setLabel(labelText);
                }

                // Set hint text if provided
                CharSequence hintText = a.getText(R.styleable.GPDefaultInputContainer_hintText);
                if (hintText != null) {
                    setHintText(hintText);
                }

                // set text if provided
                CharSequence text = a.getText(R.styleable.GPDefaultInputContainer_text);
                if (labelText != null) {
                    setText(text);
                }

                // Set helper text if provided
                CharSequence helperText = a.getText(R.styleable.GPDefaultInputContainer_helperText);
                if (helperText != null) {
                    setHelperText(helperText);
                }

                // Set error text if provided (will show error state)
                CharSequence errorText = a.getText(R.styleable.GPDefaultInputContainer_errorText);
                if (errorText != null) {
                    setErrorMessage(errorText);
                }

                // Set input type if provided
                int inputType = a.getInt(R.styleable.GPDefaultInputContainer_inputType, EditorInfo.TYPE_NULL);
                if (inputType != EditorInfo.TYPE_NULL) {
                    editText.setInputType(inputType);
                }
            } finally {
                a.recycle();
            }
        }
        initCustomConfig();
    }

    protected void initCustomConfig() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText.getState() == GPInputState.ERROR) {
                    editText.setState(GPInputState.DEFAULT);
                }
            }
            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });
    }

    // end region

    public void addTextWatcher(GPTextWatcher watcher) {
        _gpTextWatcher = watcher;
        editText.addTextChangedListener(watcher);
    };

    public GPDefaultEditText getEditText() {
        return editText;
    }

    public void setLabel(CharSequence text) {
        labelView.setText(text);
    }

    public void setHintText(CharSequence text) {
        editText.setHint(text);
    }

    public void setText(CharSequence text) {
        editText.setText(text);
    }

    public void setInactive() {
        editText.setState(GPInputState.FILLED_INACTIVE);
    }

    @Override
    public void setErrorMessage(CharSequence errorMessage) {
        errorTextView.setText(errorMessage);
        animateVisibility(errorView, true);
        editText.setState(GPInputState.ERROR);
    }

    public void clearError() {
        animateVisibility(errorView, false);
        editText.clearError();
    }

    public void setFocus() {
        editText.requestFocus();
    }

    public void clearFocus() {
        editText.clearFocus();
    }

    protected void setState(GPInputState state) {
        editText.setState(state);
//        setBackgroundState(state);
    }

    public void setHelperText(CharSequence text) {
        helperView.setText(text);
    }

    private void animateVisibility(View view, boolean show) {
        AlphaAnimation anim = new AlphaAnimation(show ? 0f : 1f, show ? 1f : 0f);
        anim.setDuration(200);
        anim.setFillAfter(true);
        view.startAnimation(anim);
        view.setVisibility(show ? VISIBLE : GONE);
    }

//    private void setBackgroundState(GPInputState state) {
//        Drawable bg = null;
//        switch (state) {
//            case ACTIVE:
//                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_active);
//                break;
//            case ERROR:
//                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_error);
//                break;
//            case FILLED_INACTIVE:
//                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_filled_inactive);
//                break;
////            case FILLED:
////                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_active);
////                break;
//            default:
//                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_default);
//                break;
//        }
//        llEditTextContainer.setBackground(bg);
//    }

    @Override
    public String getInput() {
        if (null == _gpTextWatcher) {
            if (null == getEditText().getText()) return "";
            Log.d("GPDefaultInputContainer", "getInput: " + getEditText().getText().toString());
            return getEditText().getText().toString();
        }
        else {
            String input = _gpTextWatcher.getRealValue();
            Log.d("GPDefaultInputContainer", "getInput: " + input);
            return input;
        }
    }
}
