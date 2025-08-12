package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;

public class GPTransactionUnderReviewView extends FrameLayout {

    //region Fields
    public ProgressBar progressIndicator;
    public TextView titleText;
    public TextView subtitleText;
    public ImageButton closeButton;
    //endregion

    //region Constructors
    public GPTransactionUnderReviewView(Context context) {
        super(context);
        init(context);
    }

    public GPTransactionUnderReviewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPTransactionUnderReviewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //endregion

    //region Initialization
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_gp_transaction_under_review, this, true);
        progressIndicator = findViewById(R.id.progressIndicator);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
        closeButton = findViewById(R.id.closeButton);
    }
    //endregion

    //region Public API
    public void setTitleText(String title) {
        if (titleText != null) {
            titleText.setText(title);
        }
    }

    public void setSubtitleText(String subtitle) {
        if (subtitleText != null) {
            subtitleText.setText(subtitle);
        }
    }

    public void setOnCloseClickListener(@Nullable OnClickListener listener) {
        if (closeButton != null) {
            closeButton.setOnClickListener(listener);
        }
    }
    //endregion
}
