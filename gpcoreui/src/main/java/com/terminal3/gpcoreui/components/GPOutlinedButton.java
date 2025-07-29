package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPButtonState;

public class GPOutlinedButton extends FrameLayout {

    private TextView textView;
    private ProgressBar progressBar;
    private GPButtonState state = GPButtonState.DEFAULT;

    public GPOutlinedButton(Context context) {
        super(context);
        init(context);
    }

    public GPOutlinedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        if (attrs != null) {
            int[] attrSet = new int[]{android.R.attr.text};
            android.content.res.TypedArray a = context.obtainStyledAttributes(attrs, attrSet);
            CharSequence txt = a.getText(0);
            if (txt != null) {
                textView.setText(txt);
            }
            a.recycle();
        }
    }

    public GPOutlinedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gp_button, this, true);
        textView = findViewById(R.id.gp_text);
        progressBar = findViewById(R.id.gp_progress);
        setBackground(ContextCompat.getDrawable(context, R.drawable.gp_outlined_button_background));
        textView.setTextColor(ContextCompat.getColor(context, R.color.gp_text_button_dark));
        setState(GPButtonState.DEFAULT);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public CharSequence getText() {
        return textView.getText();
    }

    public void setState(GPButtonState newState) {
        if (state == newState) return;
        state = newState;
        updateState();
    }

    public GPButtonState getState() {
        return state;
    }

    private void updateState() {
        switch (state) {
            case INACTIVE:
                setEnabled(false);
                progressBar.setVisibility(GONE);
                textView.setVisibility(VISIBLE);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_inactive_secondary));
                break;
            case LOADING:
                setEnabled(false);
                progressBar.setVisibility(VISIBLE);
                textView.setVisibility(INVISIBLE);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_dark));
                break;
            case PRESS:
            case DEFAULT:
            default:
                setEnabled(true);
                progressBar.setVisibility(GONE);
                textView.setVisibility(VISIBLE);
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.gp_text_button_dark));
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (state == GPButtonState.LOADING || !isEnabled()) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);
                setState(GPButtonState.PRESS);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setPressed(false);
                setState(GPButtonState.DEFAULT);
                break;
        }
        return super.onTouchEvent(event);
    }
}
