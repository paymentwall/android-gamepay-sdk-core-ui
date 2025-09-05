package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.GPSavedCardState;

public class GPSavedCardView extends LinearLayout {

    private LinearLayout viewRoot;
    private LinearLayout llCardRow;
    private ImageView cardBrandView;
    private TextView cardNameView;
    private TextView cardNumberView;
    private ImageView menuView;
    private View vSavedCardDivider;
    private EditText cvvField;
    private GPSavedCardState state = GPSavedCardState.DEFAULT;

    private boolean canDeleteCard = true;
    private boolean isRequireCVV = true;


    public GPSavedCardView(Context context) {
        super(context);
        init(context);
    }

    public GPSavedCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPSavedCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void updateConfig(Boolean canDeleteCard, Boolean isRequireCVV) {
        this.canDeleteCard = canDeleteCard;
        this.isRequireCVV = isRequireCVV;
        updateUI();
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_view_saved_card, this, true);
        viewRoot = findViewById(R.id.gp_saved_card_root);
        llCardRow = findViewById(R.id.gp_card_row);
        cardBrandView = findViewById(R.id.gp_card_brand);
        cardNameView = findViewById(R.id.gp_card_name);
        cardNumberView = findViewById(R.id.gp_card_number);
        menuView = findViewById(R.id.gp_card_menu);
        vSavedCardDivider = findViewById(R.id.gp_saved_card_divider);
        cvvField = findViewById(R.id.gp_cvv_field);

        setClipToOutline(true);
//        cvvField.setClipToOutline(true);
        setState(GPSavedCardState.DEFAULT);
        setOnClickListener(v -> toggle());
        updateUI();

        cvvField.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(4)
        });
        cvvField.setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                ContextCompat.getDrawable(getContext(), R.drawable.gp_ic_card_cvv),
                null
        );
    }

    public void setState(GPSavedCardState newState) {
        if (state == newState) return;
        state = newState;
        updateState();
    }

    public GPSavedCardState getState() {
        return state;
    }

    public void toggle() {
        if (state == GPSavedCardState.DEFAULT) {
            setState(GPSavedCardState.SELECTED);
        } else {
            setState(GPSavedCardState.DEFAULT);
        }
    }

    private void updateState() {
        if (state == GPSavedCardState.SELECTED) {
//            llCardRow.setSelected(true);
            viewRoot.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gp_saved_card_background_selected));
            vSavedCardDivider.setVisibility(isRequireCVV ? VISIBLE : GONE);
            cvvField.setVisibility(isRequireCVV ? VISIBLE : GONE);
        } else {
//            llCardRow.setSelected(false);
            viewRoot.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gp_saved_card_background_default));
            vSavedCardDivider.setVisibility(GONE);
            cvvField.setVisibility(GONE);
        }
    }

    private void updateUI() {
        menuView.setVisibility(canDeleteCard ? VISIBLE : GONE);
        updateState();
    }

    public void setCardBrandIcon(Drawable drawable) {
        cardBrandView.setImageDrawable(drawable);
    }

    public void setCardName(CharSequence name) {
        cardNameView.setText(name);
    }

    public void setMaskedCardNumber(CharSequence number) {
        cardNumberView.setText(number);
    }

    public EditText getCvvField() {
        return cvvField;
    }

    public void setOnMenuClickListener(OnClickListener listener) {
        menuView.setOnClickListener(listener);
    }
}
