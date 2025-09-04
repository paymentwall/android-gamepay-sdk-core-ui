package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;

/**
 * GPOrDivider - Simple Android component for payment SDK
 * Displays horizontal divider lines with editable text in a circular background
 */
public class GPOrDivider extends LinearLayout {

    private TextView orTextView;

    public GPOrDivider(Context context) {
        super(context);
        init(context);
    }

    public GPOrDivider(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPOrDivider(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // Inflate the layout
        LayoutInflater.from(context).inflate(R.layout.gp_or_divider, this, true);

        // Find the text view
        orTextView = findViewById(R.id.gp_or_text);
    }

    /**
     * Set the text displayed in the center circle
     * @param text The text to display
     */
    public void setText(String text) {
        if (orTextView != null) {
            orTextView.setText(text);
        }
    }

    /**
     * Get the current text
     * @return Current text string
     */
    public String getText() {
        if (orTextView != null) {
            return orTextView.getText().toString();
        }
        return "";
    }
}
