package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.terminal3.gpcoreui.R;

public class GPSecondaryButton extends GPPrimaryButton{
    public GPSecondaryButton(Context context) {
        super(context);
    }

    public GPSecondaryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GPSecondaryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        // Customize properties before calling super
        initCustomColors();
        super.init(context);
    }

    @Override
    protected void init(Context context, AttributeSet attrs) {
        initCustomColors();
        super.init(context, attrs); // This will call initCustomConfig with our new values
    }

    private void initCustomColors() {
        // Customize properties before calling super
        this.backgroundResId = R.drawable.gp_secondary_button_background;
        this.textColorStateResId = R.color.gp_secondary_button_text_color;
        this.progressColor = R.color.gp_text_button_dark;
    }
}
