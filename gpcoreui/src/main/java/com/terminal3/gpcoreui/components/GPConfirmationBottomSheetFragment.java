package com.terminal3.gpcoreui.components;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.terminal3.gpcoreui.R;

public class GPConfirmationBottomSheetFragment extends BottomSheetDialogFragment {

    private OnDecisionListener decisionListener;
    private String title;
    private String message;
    private boolean showPositiveButton = false;
    private boolean showDestructiveButton = false;
    private boolean showCancelButton = false;
    private String positiveButtonText = "Confirm";
    private String destructiveButtonText = "Remove";
//    private String cancelButtonText = "Cancel";

    TextView tvTitle;
    TextView tvSubtitle;

    public GPConfirmationBottomSheetFragment() {}


    // region Public Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setShowPositiveButton(boolean show, String buttonText) {
        this.showPositiveButton = show;
        if (buttonText != null) {
            this.positiveButtonText = buttonText;
        }
    }

    public void setShowDestructiveButton(boolean show, String buttonText) {
        this.showDestructiveButton = show;
        if (buttonText != null) {
            this.destructiveButtonText = buttonText;
        }
    }

    public void setShowCancelButton(boolean show, String buttonText) {
        this.showCancelButton = show;
//        if (buttonText != null) {
//            this.cancelButtonText = buttonText;
//        }
    }

    public void setOnDecisionListener(OnDecisionListener listener) {
        this.decisionListener = listener;
    }

    private String cardBrand;
    private String cardType;
    private String lastFourDigits;
    private boolean isCardRemovalMode = false;

    public void setupCardRemovalConfirmation(String cardBrand, String cardType, String lastFourDigits) {
        this.cardBrand = cardBrand;
        this.cardType = cardType;
        this.lastFourDigits = lastFourDigits;
        this.isCardRemovalMode = true;
        setShowDestructiveButton(true, null);
        setShowCancelButton(true, null);
    }

    private void setMessageWithBoldText(String text, String boldPart) {
        this.message = text;
        if (tvSubtitle != null && boldPart != null) {
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            int start = text.indexOf(boldPart);
            if (start >= 0) {
                builder.setSpan(new StyleSpan(Typeface.BOLD), start, start + boldPart.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            tvSubtitle.setText(builder);
        }
    }

    // endregion

    // region Lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.gp_bottom_sheet_confirmation, container, false);

        tvTitle = root.findViewById(R.id.tvGPBottomSheetConfirmTitle);
        tvSubtitle = root.findViewById(R.id.tvGPBottomSheetConfirmMessage);
        GPPrimaryButton positiveBtn = root.findViewById(R.id.btnGPBottomSheetConfirmPositive);
        GPErrorButton destructiveBtn = root.findViewById(R.id.btnGPBottomSheetConfirmDestructive);
        ImageView ivCancel = root.findViewById(R.id.ivGPConfirmCancel);
//        GPSecondaryButton cancelBtn = root.findViewById(R.id.btnGPBottomSheetConfirmCancel);

        // Set title if provided
        if (title != null) {
            tvTitle.setText(title);
        }

        // Set message if provided (but not in card removal mode as it will be overwritten)
        if (message != null && !isCardRemovalMode) {
            tvSubtitle.setText(message);
        }

        // Handle card removal mode - do this last to avoid overwriting formatting
        if (isCardRemovalMode) {
            title = getString(R.string.gp_card_removal_title);
            tvTitle.setText(title);
            String subtitle = getString(R.string.gp_card_removal_subtitle, cardBrand, cardType, lastFourDigits);
            setMessageWithBoldText(subtitle, cardBrand + " " +  cardType + " •••• " + lastFourDigits);
        }

        // Configure positive button visibility and text
        if (showPositiveButton) {
            positiveBtn.setVisibility(View.VISIBLE);
            positiveBtn.setText(positiveButtonText);
            positiveBtn.setOnClickListener(v -> {
                if (decisionListener != null) decisionListener.onPositiveClick();
                dismiss();
            });
        } else {
            positiveBtn.setVisibility(View.GONE);
        }

        // Configure destructive button visibility and text
        if (showDestructiveButton) {
            destructiveBtn.setVisibility(View.VISIBLE);
            destructiveBtn.setText(destructiveButtonText);
            destructiveBtn.setOnClickListener(v -> {
                if (decisionListener != null) decisionListener.onDestructiveClick();
                dismiss();
            });
        } else {
            destructiveBtn.setVisibility(View.GONE);
        }

        // Configure cancel button visibility and text
        if (showCancelButton) {
            ivCancel.setVisibility(View.VISIBLE);
//            ivCancel.setText(cancelButtonText);
            ivCancel.setOnClickListener(v -> {
                if (decisionListener != null) decisionListener.onCancel();
                dismiss();
            });
        } else {
            ivCancel.setVisibility(View.GONE);
        }

        return root;
    }

    // endregion

    public interface OnDecisionListener {
        void onPositiveClick();
        void onDestructiveClick();
        void onCancel();
    }
}
