package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.utils.GPViewAnimationHelper;
import com.terminal3.gpcoreui.utils.textwatchers.GPTextWatcher;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import java.util.ArrayList;
import java.util.List;

public class GPCardNumberField extends GPDefaultInputContainer {

    private final CardBrandDetector cardBrandDetector = new CardBrandDetector();

    private CardBrand currentCardBrand = null;
    private boolean isFieldEmpty = true;

    private final List<CardBrand> cardBrandList = new ArrayList<>();
    private Drawable cardBrandIconsDrawable;
    
    // Card brand container views
    private LinearLayout cardBrandContainer;
    
    // Animation constants
    private static final int ANIMATION_DURATION_MS = 250;

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

    @Override
    protected int getLayoutResId() {
        return R.layout.gp_payment_card_number_input_container;
    }

    private void initCardBrandUI() {
//        cardBrandIconsDrawable = ContextCompat.getDrawable(getContext(), R.drawable.gp_ic_card_brands_supported);
        cardBrandIconsDrawable = ContextCompat.getDrawable(getContext(), R.drawable.gp_ic_card_brand_unknown);
        // Add card brand icon to the right side
        getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                cardBrandIconsDrawable,
                null
        );
        getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        
        // Initialize card brand container views
        initCardBrandContainer();
    }
    
    private void initCardBrandContainer() {
        // Find the card brand container in the layout
        cardBrandContainer = findViewById(R.id.gp_card_brand_container);
        if (cardBrandContainer != null) {
            // Clear existing views and collect ImageViews
            cardBrandContainer.removeAllViews();
            // Set default alpha for all card brand icons (show all supported brands dimmed)
            updateCardBrandContainerDisplay();
        }
    }

    public void setCardBrandList(List<CardBrand> brands) {
        cardBrandList.clear();
        if (brands != null) {
            cardBrandList.addAll(brands);
        }
//        cardBrandIconsDrawable = createCardBrandIconsDrawable();
        if (isFieldEmpty) {
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    cardBrandIconsDrawable,
                    null
            );
        }
        
        // Update the card brand container display
        updateCardBrandContainerDisplay();
    }

    private Drawable createCardBrandIconsDrawable() {
        if (cardBrandList.isEmpty()) {
            return ContextCompat.getDrawable(getContext(), R.drawable.gp_ic_card_brands_supported);
        }

        int spacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
                getResources().getDisplayMetrics());
        List<Drawable> drawables = new ArrayList<>();
        int totalWidth = 0;
        int maxHeight = 0;
        for (CardBrand brand : cardBrandList) {
            Drawable d = ContextCompat.getDrawable(getContext(), brand.getIconResId());
            if (d != null) {
                drawables.add(d);
                totalWidth += d.getIntrinsicWidth();
                maxHeight = Math.max(maxHeight, d.getIntrinsicHeight());
            }
        }
        if (drawables.isEmpty()) {
            return ContextCompat.getDrawable(getContext(), R.drawable.gp_ic_card_brands_supported);
        }
        totalWidth += spacing * (drawables.size() - 1);
        Bitmap bitmap = Bitmap.createBitmap(totalWidth, maxHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        int x = 0;
        for (Drawable d : drawables) {
            int top = (maxHeight - d.getIntrinsicHeight()) / 2;
            d.setBounds(x, top, x + d.getIntrinsicWidth(), top + d.getIntrinsicHeight());
            d.draw(canvas);
            x += d.getIntrinsicWidth() + spacing;
        }
        return new BitmapDrawable(getResources(), bitmap);
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
    
    private void updateCardBrandContainerDisplay() {
        if (cardBrandContainer == null) {
            return;
        }

        if (!cardBrandList.isEmpty()){
            cardBrandContainer.setVisibility(View.VISIBLE);
        }
        else {
            cardBrandContainer.setVisibility(View.GONE);
        }
        
        // Clear existing views
        cardBrandContainer.removeAllViews();
        
        // Use provided brands or default to common supported brands
        List<CardBrand> brandsToShow = cardBrandList;
        if (brandsToShow == null || brandsToShow.isEmpty()) {
            brandsToShow = new ArrayList<>();
            brandsToShow.add(CardBrand.VISA);
            brandsToShow.add(CardBrand.MASTERCARD);
        }
        
        // Create ImageViews for each supported brand
        for (CardBrand brand : brandsToShow) {
            if (brand.getIconResId() == 0) continue; // Skip brands without icons
            
            ImageView imageView = new ImageView(getContext());
            
            // Set layout parameters
            int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, 
                    getResources().getDisplayMetrics());
            int marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, 
                    getResources().getDisplayMetrics());
            
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.rightMargin = marginRight;
            imageView.setLayoutParams(params);
            
            // Set the drawable
            imageView.setImageResource(brand.getIconResId());
            
            // Set initial alpha based on current state
            float alpha = getAlphaForBrand(brand);
            imageView.setAlpha(alpha);
            
            // Add to container
            cardBrandContainer.addView(imageView);
        }
    }
    
    private float getAlphaForBrand(CardBrand brand) {
        float dimmedAlpha = 0.3f;
        float activeAlpha = 1.0f;
        
        if (isFieldEmpty || currentCardBrand == null) {
            return activeAlpha; // Show all brands with full opacity when field is empty
        } else if (currentCardBrand == brand) {
            return activeAlpha; // Highlight the detected card brand
        } else {
            return dimmedAlpha; // Dim other brands when a card is detected
        }
    }
    
    private void animateContainerVisible() {
        if (cardBrandContainer == null || cardBrandContainer.getVisibility() == View.VISIBLE) {
            return;
        }

        GPViewAnimationHelper.expandView(cardBrandContainer, ANIMATION_DURATION_MS, new GPViewAnimationHelper.AnimationListener() {
            @Override
            public void onAnimationStart() {
                // Optional: pre-animation setup
            }

            @Override
            public void onAnimationEnd() {
                // Optional: post-animation cleanup
                cardBrandContainer.setVisibility(VISIBLE);
            }
        });

////        if (cardBrandContainer.getVisibility() != VISIBLE) {
//            cardBrandContainer.setAlpha(0f);
//            cardBrandContainer.setVisibility(VISIBLE);
//            cardBrandContainer.animate()
//                    .alpha(1f)
//                    .setDuration(300)
//                    .setInterpolator(new AccelerateDecelerateInterpolator())
//                    .start();
////        }
    }
    
    private void animateContainerGone() {
        if (cardBrandContainer == null || cardBrandContainer.getVisibility() == View.GONE) {
            return;
        }

        GPViewAnimationHelper.collapseView(cardBrandContainer, ANIMATION_DURATION_MS, new GPViewAnimationHelper.AnimationListener() {
            @Override
            public void onAnimationStart() {
                // Optional: pre-animation setup
            }

            @Override
            public void onAnimationEnd() {
                // Optional: post-animation cleanup
                cardBrandContainer.setVisibility(GONE);
            }
        });

//        if (cardBrandContainer.getVisibility() == VISIBLE) {
//            cardBrandContainer.animate()
//                    .alpha(0f)
//                    .setDuration(300)
//                    .setInterpolator(new AccelerateDecelerateInterpolator())
//                    .withEndAction(new Runnable() {
//                        @Override
//                        public void run() {
//                            cardBrandContainer.setVisibility(GONE);
//                            // Reset alpha for next time it's shown
//                            cardBrandContainer.setAlpha(1f);
//                        }
//                    })
//                    .start();
//        } else {
//            cardBrandContainer.setVisibility(GONE);
//        }
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
            animateContainerVisible();
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    cardBrandIconsDrawable,
                    null
            );
            return;
        }

        animateContainerGone();

        if (currentCardBrand != null && currentCardBrand != CardBrand.UNKNOWN && cardBrandList.contains(currentCardBrand)) {
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), currentCardBrand.getIconResId()),
                    null
            );
        } else {
            getEditText().setCompoundDrawablesRelativeWithIntrinsicBounds(
                    null,
                    null,
                    ContextCompat.getDrawable(getContext(), R.drawable.gp_ic_card_brand_unknown),
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
        VISA(R.drawable.gp_ic_card_brand_visa),
        MASTERCARD(R.drawable.gp_ic_card_brand_master),
        AMEX(R.drawable.gp_ic_card_brand_amex),
        DISCOVER(R.drawable.gp_ic_card_brand_discover),
        DINERS(R.drawable.gp_ic_card_brand_dinners),
        JCB(R.drawable.gp_ic_card_brand_jcb),
        UNIONPAY(R.drawable.gp_ic_card_brand_unionpay),
        UNKNOWN(R.drawable.gp_ic_card_brand_unknown);

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
            // UnionPay: starts with 62
            else if (digitsOnly.matches("^62.*")) {
                return CardBrand.UNIONPAY;
            }

            return CardBrand.UNKNOWN;
        }
    }
}
