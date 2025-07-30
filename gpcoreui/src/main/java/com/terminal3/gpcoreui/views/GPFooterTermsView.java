package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;

/**
 * Footer view displaying Paymentwall logo with links to Terms and Privacy.
 */
public class GPFooterTermsView extends LinearLayout {

    private TextView termsButton;
    private TextView privacyButton;

    public GPFooterTermsView(Context context) {
        super(context);
        init(context);
    }

    public GPFooterTermsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPFooterTermsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_footer_terms, this, true);
        termsButton = findViewById(R.id.termsButton);
        privacyButton = findViewById(R.id.privacyButton);
    }

    /**
     * Set listener for Terms button clicks.
     */
    public void setOnTermsClickListener(@Nullable OnClickListener listener) {
        termsButton.setOnClickListener(listener);
    }

    /**
     * Set listener for Privacy button clicks.
     */
    public void setOnPrivacyClickListener(@Nullable OnClickListener listener) {
        privacyButton.setOnClickListener(listener);
    }
}
