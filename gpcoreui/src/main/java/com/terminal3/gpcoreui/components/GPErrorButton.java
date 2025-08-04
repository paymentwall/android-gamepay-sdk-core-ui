package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;

import com.terminal3.gpcoreui.R;

public class GPErrorButton extends GPPrimaryButton{
    public GPErrorButton(Context context) {
        super(context);
    }

    public GPErrorButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GPErrorButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
        // Customize properties before calling super
        initCustomColors();
        super.init(context, attrs); // This will call initCustomConfig with our new values
    }

    private void initCustomColors() {
        this.backgroundResId = R.drawable.gp_error_button_background;
        this.textColorStateResId = R.color.gp_error_button_text_color;
        this.progressColor = R.color.gp_text_error;
    }
}
