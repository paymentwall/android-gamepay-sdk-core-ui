package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.models.GPOption;

public class GPRedirectionView extends LinearLayout implements GPOptionView {

    //#region Fields and Constants
    private TextView message;
    private String optionId;
    private String optionValue;
    private OnOptionValueChangeListener listener;
    //#endregion

    //#region Constructors
    public GPRedirectionView(Context context) {
        super(context);
        init(context);
    }

    public GPRedirectionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPRedirectionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //#endregion

    //#region Initialization
    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_redirection_view, this, true);
        message = findViewById(R.id.gp_redirection_message);
//        btnContinue = findViewById(R.id.gp_redirection_continue);
//        btnContinue.setOnClickListener(v -> {
//            if (listener != null) {
//                listener.onOptionValueChanged(optionId, optionValue);
//            }
//        });
    }
    //#endregion

    //#region GPOptionView Implementation
    @Override
    public void bindOption(GPOption option) {
        optionId = option.getId();
        optionValue = option.getValue();
        if (option.customLabel.length() > 0) {
            message.setText(option.customLabel);
        }
    }

    @Override
    public String getOptionId() {
        return optionId;
    }

    @Override
    public String getOptionValue() {
        return optionValue;
    }

    @Override
    public void setOnOptionValueChangeListener(OnOptionValueChangeListener listener) {
        this.listener = listener;
        if (null != optionId && null != optionValue) {
            listener.onOptionValueChanged(optionId, optionValue);
        }
    }

    @Override
    public String getInput() {
        return optionValue;
    }
    //#endregion
}
