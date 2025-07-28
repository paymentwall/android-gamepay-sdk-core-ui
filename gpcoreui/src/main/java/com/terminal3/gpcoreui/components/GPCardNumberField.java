package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.utils.textwatchers.GPTextWatcher;

public class GPCardNumberField extends GPDefaultInputContainer {

    private final CardBrandDetector cardBrandDetector = new CardBrandDetector();

    private CardBrand currentCardBrand = null;
    private boolean isFieldEmpty = true;

    public GPCardNumberField(Context context) {
        super(context);
        initCardBrandUI();
    }

    public GPCardNumberField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCardBrandUI();
    }

    public GPCardNumberField(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCardBrandUI();
    }

    private void initCardBrandUI() {
        // Add card brand icon to the right side
        getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(getContext(), R.drawable.ic_card_brands_supported),
                null
        );
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    @Override
    public void addTextWatcher(GPTextWatcher watcher) {
        super.addTextWatcher(watcher);
        // Set up text watcher for card number formatting and brand detection
        getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                _gpTextWatcher.beforeTextChanged(s, start, count, after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _gpTextWatcher.onTextChanged(s, start, before, count);
            }

            @Override
            public void afterTextChanged(Editable s) {
                _gpTextWatcher.afterTextChanged(s);
                // Format card number with spaces
                String formatted = formatCardNumber(getInput());
                if (!formatted.equals(s.toString())) {
                    getEditText().setText(formatted);
                    getEditText().setSelection(formatted.length());
                }

                // Detect and update card brand
                updateCardBrandIcon(s.toString());
            }
        });
    }

    private String formatCardNumber(String cardNumber) {
        // Remove all non-digit characters
        String digitsOnly = cardNumber.replaceAll("[^0-9]", "");

        // Add spaces every 4 digits
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digitsOnly.length(); i++) {
            if (i > 0 && i % 4 == 0) {
                formatted.append(" ");
            }
            formatted.append(digitsOnly.charAt(i));
        }

        return formatted.toString();
    }

    private void updateCardBrandIcon(String cardNumber) {
        // Early return if state hasn't changed
        boolean isEmptyNow = cardNumber == null || cardNumber.isEmpty();
        if (isEmptyNow == isFieldEmpty &&
                (isEmptyNow || currentCardBrand == cardBrandDetector.detect(cardNumber))) {
            return;
        }

        // Update state trackers
        isFieldEmpty = isEmptyNow;
        currentCardBrand = isFieldEmpty ? null : cardBrandDetector.detect(cardNumber);

        // Clear any existing right drawables first
        getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);

        if (isFieldEmpty) {
            // Show the right views container when no input
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_card_brands_supported),
                    null
            );
            return;
        }

        if (currentCardBrand != null && currentCardBrand != CardBrand.UNKNOWN) {
            // Set detected brand as right drawable
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), currentCardBrand.getIconResId()),
                    null
            );
        } else {
            // Set unknown card icon when brand not detected
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.ic_card_unknown),
                    null
            );
        }
    }

    public String getCardNumber() {
        // Return raw digits only (without spaces)
        return getInput().replaceAll("[^0-9]", "");
    }

    // Card brand enum and detector
    public enum CardBrand {
        VISA(R.drawable.ic_card_brand_visa),
        MASTERCARD(R.drawable.ic_card_brand_master),
        AMEX(R.drawable.ic_card_brand_amex),
        DISCOVER(R.drawable.ic_card_unknown),
        DINERS(R.drawable.ic_card_unknown),
        JCB(R.drawable.ic_card_brand_jcb),
        UNKNOWN(0);

        private final int iconResId;

        CardBrand(int iconResId) {
            this.iconResId = iconResId;
        }

        public int getIconResId() {
            return iconResId;
        }
    }

    public static class CardBrandDetector {
        public CardBrand detect(String cardNumber) {
            String digitsOnly = cardNumber.replaceAll("[^0-9]", "");

            if (digitsOnly.isEmpty()) {
                return null;
            }

            // Visa: starts with 4
            if (digitsOnly.matches("^4.*")) {
                return CardBrand.VISA;
            }
            // Mastercard: starts with 51-55 or 2221-2720
            else if (digitsOnly.matches("^(5[1-5]|222[1-9]|22[3-9]|2[3-6]|27[01]).*")) {
                return CardBrand.MASTERCARD;
            }
            // Amex: starts with 34 or 37
            else if (digitsOnly.matches("^3[47].*")) {
                return CardBrand.AMEX;
            }
            // Discover: starts with 6011, 644-649, or 65
            else if (digitsOnly.matches("^(6011|64[4-9]|65).*")) {
                return CardBrand.DISCOVER;
            }
            // Diners Club: starts with 300-305, 36, or 38-39
            else if (digitsOnly.matches("^(30[0-5]|36|38|39).*")) {
                return CardBrand.DINERS;
            }
            // JCB: starts with 3528-3589
            else if (digitsOnly.matches("^(352[8-9]|35[3-8]).*")) {
                return CardBrand.JCB;
            }

            return CardBrand.UNKNOWN;
        }
    }
}
