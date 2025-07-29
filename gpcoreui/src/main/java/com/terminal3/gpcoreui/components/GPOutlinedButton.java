package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPButtonState;

public class GPOutlinedButton extends GPPrimaryButton {

    public GPOutlinedButton(Context context) {
        super(context);
    }

    public GPOutlinedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GPOutlinedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initCustomConfig(Context context) {
        super.initCustomConfig(context);
        setBackground(ContextCompat.getDrawable(context, R.drawable.gp_outlined_button_background));
        btn.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_dark));
        btn.setEnabled(true);
        setEnabled(true);
    }

    @Override
    public void updateState() {
        switch (state) {
            case INACTIVE:
                setEnabled(false);
                progressBar.setVisibility(GONE);
                btn.setVisibility(VISIBLE);
                btn.setEnabled(false);
                btn.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_inactive_secondary));
                break;
            case LOADING:
                setEnabled(true);
                progressBar.setVisibility(VISIBLE);
                btn.setVisibility(INVISIBLE);
                btn.setEnabled(false);
                btn.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_dark));
                break;
            case PRESS:
            case DEFAULT:
            default:
                setEnabled(true);
                progressBar.setVisibility(GONE);
                btn.setVisibility(VISIBLE);
                btn.setEnabled(true);
                btn.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_dark));
                break;
        }
    }
}
