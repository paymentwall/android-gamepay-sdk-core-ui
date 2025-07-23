package com.terminal3.gamepayui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import com.terminal3.gamepayui.enums.GPInputState;

/**
 * Base payment EditText handling input states and basic error/helper text logic.
 */
public class GPBasePaymentEditText extends AppCompatEditText {

    private GPInputState currentState = GPInputState.DEFAULT;
    private String errorMessage;
    private String helperText;
    private OnFocusChangeListener externalFocusChangeListener;

    public GPBasePaymentEditText(Context context) {
        super(context);
        init();
    }

    public GPBasePaymentEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GPBasePaymentEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundState(GPInputState.DEFAULT);
        super.setOnFocusChangeListener(this::onFocusChangedInternal);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && currentState != GPInputState.ERROR) {
                    setState(GPInputState.FILLED);
                } else if (s.length() == 0 && currentState != GPInputState.ERROR) {
                    setState(GPInputState.DEFAULT);
                }
            }
        });
    }

    private void onFocusChangedInternal(View v, boolean hasFocus) {
        if (hasFocus) {
            if (currentState != GPInputState.ERROR) {
                setState(GPInputState.ACTIVE);
            }
        } else {
            if (currentState == GPInputState.ACTIVE) {
                setState(GPInputState.DEFAULT);
            }
        }
        if (externalFocusChangeListener != null) {
            externalFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        externalFocusChangeListener = l;
    }

    public void setState(GPInputState state) {
        currentState = state;
        setBackgroundState(state);
    }

    public void setErrorMessage(String message) {
        errorMessage = message;
        setState(GPInputState.ERROR);
    }

    public void clearError() {
        errorMessage = null;
        setState(GPInputState.DEFAULT);
    }

    public void setHelperText(String text) {
        helperText = text;
    }

    private void setBackgroundState(GPInputState state) {
        Drawable bg = null;
        switch (state) {
            case ACTIVE:
                bg = getResources().getDrawable(android.R.drawable.editbox_background_normal);
                break;
            case ERROR:
                bg = getResources().getDrawable(android.R.drawable.editbox_background_normal);
                break;
            case FILLED:
                bg = getResources().getDrawable(android.R.drawable.editbox_background_normal);
                break;
            case INACTIVE:
                bg = getResources().getDrawable(android.R.drawable.editbox_background_normal);
                break;
            default:
                bg = getResources().getDrawable(android.R.drawable.editbox_background_normal);
                break;
        }
        setBackground(bg);
    }
}
