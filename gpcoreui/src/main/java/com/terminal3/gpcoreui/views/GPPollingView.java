package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;

public class GPPollingView extends LinearLayout {

    public GPPollingAnimatedProcessingView animatedProcessingView;
    public TextView titleText;
    public TextView subtitleText;
    public GPFooterTermsView footerTermsView;

    public GPPollingView(Context context) {
        super(context);
        init(context);
    }

    public GPPollingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPPollingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_gp_polling, this, true);
        
        animatedProcessingView = findViewById(R.id.pollingAnimatedView);
        titleText = findViewById(R.id.titleText);
        subtitleText = findViewById(R.id.subtitleText);
    }

    public void startPollingAnimation() {
        if (animatedProcessingView != null) {
            animatedProcessingView.startAnimation();
        }
    }

    public void stopPollingAnimation() {
        if (animatedProcessingView != null) {
            animatedProcessingView.stopAnimation();
        }
    }

    public boolean isPollingAnimationRunning() {
        return animatedProcessingView != null && animatedProcessingView.isAnimating();
    }

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

    public void setOnTermsClickListener(@Nullable View.OnClickListener listener) {
        if (footerTermsView != null) {
            footerTermsView.setOnTermsClickListener(listener);
        }
    }

    public void setOnPrivacyClickListener(@Nullable View.OnClickListener listener) {
        if (footerTermsView != null) {
            footerTermsView.setOnPrivacyClickListener(listener);
        }
    }
}