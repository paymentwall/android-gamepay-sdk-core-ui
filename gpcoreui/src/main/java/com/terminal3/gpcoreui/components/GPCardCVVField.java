package com.terminal3.gpcoreui.components;


import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.utils.textwatchers.GPExpiryDateTextWatcher;

public class GPCardCVVField extends GPDefaultInputContainer{
    public GPCardCVVField(Context context) {
        super(context);
    }

    public GPCardCVVField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GPCardCVVField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initCustomConfig() {
        super.initCustomConfig();
        getEditText().setHint("CVV");
        getEditText().setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(4)
        });
        getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(getContext(), R.drawable.ic_card_cvv),
                null
        );

        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//        getEditText().setRawInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
    }
}
