package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPInputState;

public class GPDefaultInputContainer extends LinearLayout {

    private TextView labelView;
    private GPDefaultEditText editText;
    private View errorView;
    private TextView errorTextView;
    private TextView helperView;

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
                    setError(errorText);
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
    }

    // end region

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

    public void setError(CharSequence text) {
        errorTextView.setText(text);
        animateVisibility(errorView, true);
        editText.setState(GPInputState.ERROR);
    }

    public void setInactive() {
        editText.setState(GPInputState.FILLED_INACTIVE);
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
}
