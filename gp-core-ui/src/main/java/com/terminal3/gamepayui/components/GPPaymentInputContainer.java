package com.terminal3.gamepayui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gamepayui.R;
import com.terminal3.gamepayui.enums.GPInputState;

public class GPPaymentInputContainer extends LinearLayout {

    private TextView labelView;
    private GPBasePaymentEditText editText;
    private TextView errorView;
    private TextView helperView;

    public GPPaymentInputContainer(Context context) {
        super(context);
        init(context);
    }

    public GPPaymentInputContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPPaymentInputContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_payment_input_container, this, true);
        labelView = findViewById(R.id.gp_label);
        editText = findViewById(R.id.gp_edit_text);
        errorView = findViewById(R.id.gp_error);
        helperView = findViewById(R.id.gp_helper);
    }

    public void setLabel(CharSequence text) {
        labelView.setText(text);
    }

    public GPBasePaymentEditText getEditText() {
        return editText;
    }

    public void setError(CharSequence text) {
        errorView.setText(text);
        animateVisibility(errorView, true);
        editText.setState(GPInputState.ERROR);
    }

    public void clearError() {
        animateVisibility(errorView, false);
        editText.clearError();
    }

    public void setHelperText(CharSequence text) {
        helperView.setText(text);
    }

    private void animateVisibility(TextView view, boolean show) {
        AlphaAnimation anim = new AlphaAnimation(show ? 0f : 1f, show ? 1f : 0f);
        anim.setDuration(200);
        anim.setFillAfter(true);
        view.startAnimation(anim);
        view.setVisibility(show ? VISIBLE : GONE);
    }
}
