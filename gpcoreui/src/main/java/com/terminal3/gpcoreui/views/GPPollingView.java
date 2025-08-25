package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
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
        LayoutInflater.from(context).inflate(R.layout.gp_view_polling, this, true);
        
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

    private void setSubtitleTextWithBoldParam(String text, String boldParam) {
        if (subtitleText != null && boldParam != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            int start = text.indexOf(boldParam);
            if (start >= 0) {
                builder.setSpan(new StyleSpan(Typeface.BOLD), start, start + boldParam.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            subtitleText.setText(builder);
        }
    }

    // region Setup Title & SubTitle
    public void setPaymentUnderReviewTexts(String paymentId) {
        setTitleText(getContext().getString(R.string.gp_payment_under_review_title));
        String subtitle = getContext().getString(R.string.gp_payment_under_review_subtitle, paymentId);
        setSubtitleTextWithBoldParam(subtitle, paymentId);
    }

    public void setPayAltoPollingTexts(String paymentMethod) {
        setTitleText(getContext().getString(R.string.gp_payalto_polling_title));
        String subtitle = getContext().getString(R.string.gp_payalto_polling_subtitle, paymentMethod);
        setSubtitleTextWithBoldParam(subtitle, paymentMethod);
    }

    // end region
}