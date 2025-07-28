package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPInputState;

/**
 * Base payment EditText handling input states and basic error/helper text logic.
 */
public class GPDefaultEditText extends AppCompatEditText {

    private GPInputState currentState = GPInputState.DEFAULT;
    private String errorMessage;
    private String helperText;
    private OnFocusChangeListener externalFocusChangeListener;

    public GPDefaultEditText(Context context) {
        super(context);
        init();
    }

    public GPDefaultEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GPDefaultEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundState(GPInputState.DEFAULT);
        super.setOnFocusChangeListener(this::onFocusChangedInternal);
//        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gp_transparent));
//        addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
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
        if (state == currentState) return;
        currentState = state;
        setBackgroundState(state);
        Log.i("GPDefaultEditText", "setState: " + state);
    }

    public GPInputState getState() {
        return currentState;
    }

    public void setErrorMessage(String message) {
        Log.i("GPDefaultEditText", "setErrorMessage: " + message);
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
                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_active);
                break;
            case ERROR:
                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_error);
                break;
            case FILLED_INACTIVE:
                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_filled_inactive);
                break;
//            case FILLED:
//                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_active);
//                break;
            default:
                bg = ContextCompat.getDrawable(getContext(), R.drawable.input_bg_default);
                break;
        }
        setBackground(bg);
    }

    private void setTextColorState(GPInputState state) {
        switch (state) {
            case FILLED_INACTIVE:
                setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_secondary));
                break;
            default:
                setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_primary));
                break;
        }
    };
}
