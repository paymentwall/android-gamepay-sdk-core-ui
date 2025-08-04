package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.terminal3.gpcoreui.R;

public class GPPayAltoButton extends LinearLayout {

    private ImageView ivIcon;
    private TextView tvTitle;

    public GPPayAltoButton(Context context) {
        super(context);
        init(context, null);
    }

    public GPPayAltoButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GPPayAltoButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.gp_payalto_button, this, true);
        ivIcon = findViewById(R.id.iv_payalto_icon);
        tvTitle = findViewById(R.id.tv_payalto_title);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GPPayAltoButton);

            // Set text if provided in XML
            CharSequence text = a.getText(R.styleable.GPPayAltoButton_title);
            if (text != null) {
                setText(text);
            }

            // Set image if provided in XML
            int imageRes = a.getResourceId(R.styleable.GPPayAltoButton_imageSrc, -1);
            if (imageRes != -1) {
                setImageResource(imageRes);
            }

            a.recycle();
        }
    }

    /**
     * Sets the text for the button
     * @param text The text to display
     */
    public void setText(CharSequence text) {
        tvTitle.setText(text);
    }

    /**
     * Gets the current text of the button
     * @return The current text
     */
    public CharSequence getText() {
        return tvTitle.getText();
    }

    /**
     * Sets the image resource for the icon
     * @param resId The drawable resource ID
     */
    public void setImageResource(@DrawableRes int resId) {
        ivIcon.setImageResource(resId);
        ivIcon.setVisibility(View.VISIBLE);
    }

    /**
     * Loads a logo from a URL using Glide
     * @param logoUrl The URL of the logo image
     */
    public void setLogo(String logoUrl) {
        if (logoUrl == null || logoUrl.isEmpty()) {
            ivIcon.setVisibility(View.GONE);
            return;
        }

        ivIcon.setVisibility(View.VISIBLE);
        Glide.with(getContext())
                .load(logoUrl)
                .into(ivIcon);
    }

//    /**
//     * Loads a logo from a URL using Glide with custom options
//     * @param logoUrl The URL of the logo image
//     * @param placeholderResId Optional placeholder resource ID
//     * @param errorResId Optional error placeholder resource ID
//     * @param circleCrop Whether to apply circle crop transformation
//     */
//    public void setLogo(String logoUrl, @DrawableRes int placeholderResId, @DrawableRes int errorResId, boolean circleCrop) {
//        if (logoUrl == null || logoUrl.isEmpty()) {
//            ivIcon.setVisibility(View.GONE);
//            return;
//        }
//
//        ivIcon.setVisibility(View.VISIBLE);
//        Glide.RequestBuilder<?> builder = Glide.with(getContext())
//                .load(logoUrl);
//
//        if (placeholderResId != 0) {
//            builder.placeholder(placeholderResId);
//        }
//
//        if (errorResId != 0) {
//            builder.error(errorResId);
//        }
//
//        if (circleCrop) {
//            builder.circleCrop();
//        }
//
//        builder.into(ivIcon);
//    }
}
