package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

public class GPPrimaryButton extends FrameLayout {

    protected int backgroundResId = R.drawable.gp_primary_button_background;
    protected int textColorStateResId = R.color.gp_primary_button_text_color;

    protected int progressColor = R.color.gp_text_button_light;

    protected Button btn;
    protected ProgressBar progressBar;
    protected GPButtonState state = GPButtonState.DEFAULT;

    public GPPrimaryButton(Context context) {
        super(context);
        init(context);
    }

    public GPPrimaryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GPPrimaryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    protected void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.gp_button, this, true);
        btn = findViewById(R.id.gp_button_title);
        progressBar = findViewById(R.id.gp_progress);
        setState(GPButtonState.DEFAULT);
        initCustomConfig(context);
    }

    protected void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.gp_button, this, true);
        btn = findViewById(R.id.gp_button_title);
        progressBar = findViewById(R.id.gp_progress);
        setState(GPButtonState.DEFAULT);
        if (attrs != null) {
            int[] attrSet = new int[]{android.R.attr.text};
            android.content.res.TypedArray a = context.obtainStyledAttributes(attrs, attrSet);
            CharSequence txt = a.getText(0);
            if (txt != null) {
                btn.setText(txt);
            }
            a.recycle();
        }
        initCustomConfig(context);
    }

    protected void initCustomConfig(Context context) {
        setBackground(ContextCompat.getDrawable(context, backgroundResId));
        btn.setTextColor(ContextCompat.getColorStateList(context, textColorStateResId));
        progressBar.setIndeterminateTintList(ContextCompat.getColorStateList(context, progressColor));
    }

    public void setText(CharSequence text) {
        btn.setText(text);
    }

    public CharSequence getText() {
        return btn.getText();
    }

    public void setState(GPButtonState newState) {
        if (state == newState) return;
        state = newState;
        updateState();
    }

    public GPButtonState getState() {
        return state;
    }

    protected void updateState() {
        switch (state) {
            case INACTIVE:
                setEnabled(false);
                progressBar.setVisibility(GONE);
                btn.setVisibility(VISIBLE);
                btn.setEnabled(false);
                break;
            case LOADING:
                setEnabled(true);
                progressBar.setVisibility(VISIBLE);
                btn.setVisibility(GONE);
                btn.setEnabled(false);
                break;
            case PRESS:
            case DEFAULT:
            default:
                setEnabled(true);
                progressBar.setVisibility(GONE);
                btn.setVisibility(VISIBLE);
                btn.setEnabled(true);
                break;
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        btn.setOnClickListener(l);
//        super.setOnClickListener(l);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (state == GPButtonState.LOADING || !isEnabled()) {
//            return super.onTouchEvent(event);
//        }
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                setPressed(true);
//                setState(GPButtonState.PRESS);
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//                setPressed(false);
//                setState(GPButtonState.DEFAULT);
//                break;
//        }
//        return super.onTouchEvent(event);
//    }
}
