package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;

import com.terminal3.gpcoreui.utils.textwatchers.GPExpiryDateTextWatcher;

public class GPCardExpiryDateField extends GPDefaultInputContainer{
    public GPCardExpiryDateField(Context context) {
        super(context);
    }
    public GPCardExpiryDateField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public GPCardExpiryDateField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initCustomConfig() {
        super.initCustomConfig();
        getEditText().setHint("MM/YY");
        getEditText().addTextChangedListener(new GPExpiryDateTextWatcher());
        getEditText().setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
    }
}
