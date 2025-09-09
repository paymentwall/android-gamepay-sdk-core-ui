package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.components.GPPrimaryButton;

public class GPAlertViewBottom extends LinearLayout {

    public interface OnBackToMerchantClickListener {
        void onBackToMerchantClick();
    }

    public static class UIModel {
        public int iconRes;
        public String title;
        public String subtitle;
        public String buttonText;

        public static UIModel createSecurity(Context context) {
            UIModel model = new UIModel();
            model.iconRes = R.drawable.ic_bottom_alert_security;
            model.title = context.getString(R.string.gp_alert_title_security);;
            model.subtitle = context.getString(R.string.gp_alert_subtitle_security);
            model.buttonText = context.getString(R.string.gp_back_to_merchant);
            return model;
        }

        public static UIModel createUnexpected(Context context, String errorMessage) {
            UIModel model = new UIModel();
            model.iconRes = R.drawable.ic_bottom_alert_warning;
            model.title = context.getString(R.string.gp_alert_title_unexpected);
            model.subtitle = errorMessage;
            model.buttonText = context.getString(R.string.gp_back_to_merchant);
            return model;
        }
    }

    // region Properties
    private ImageView ivGPAlertIconHeader;
    private TextView tvGPAlertTitle;
    private TextView tvGPAlertSubtitle;
    public GPPrimaryButton btnGPAlertBackToMerchant;

    private OnBackToMerchantClickListener onBackToMerchantClickListener;

    // endregion


    public GPAlertViewBottom(Context context) {
        super(context);
        init(context);
    }

    public GPAlertViewBottom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPAlertViewBottom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.gp_bottom_alert_view, this, true);
        ivGPAlertIconHeader = findViewById(R.id.ivGPAlertIconHeader);
        tvGPAlertTitle = findViewById(R.id.tvGPAlertTitle);
        tvGPAlertSubtitle = findViewById(R.id.tvGPAlertSubtitle);
        btnGPAlertBackToMerchant = findViewById(R.id.btnGPAlertBackToMerchant);

        btnGPAlertBackToMerchant.setOnClickListener( v -> {
            if (onBackToMerchantClickListener != null) {
                onBackToMerchantClickListener.onBackToMerchantClick();
            }
        });
    }

    // region Public methods
    public void setUIModel(UIModel model) {
        ivGPAlertIconHeader.setImageResource(model.iconRes);
        tvGPAlertTitle.setText(model.title);
        tvGPAlertSubtitle.setText(model.subtitle);
        btnGPAlertBackToMerchant.setText(model.buttonText);
    };

    public void setOnBackToMerchantClickListener(@Nullable OnBackToMerchantClickListener listener) {
        onBackToMerchantClickListener = listener;
    }
    // endregion
}
