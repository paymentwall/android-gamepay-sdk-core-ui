package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.enums.SavedCardState;

public class GPSavedCardView extends LinearLayout {

    private ImageView cardBrandView;
    private TextView cardNameView;
    private TextView cardNumberView;
    private ImageView menuView;
    private GPCardCVVField cvvField;
    private SavedCardState state = SavedCardState.DEFAULT;

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

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_saved_card, this, true);
        cardBrandView = findViewById(R.id.gp_card_brand);
        cardNameView = findViewById(R.id.gp_card_name);
        cardNumberView = findViewById(R.id.gp_card_number);
        menuView = findViewById(R.id.gp_card_menu);
        cvvField = findViewById(R.id.gp_cvv_field);
        setState(SavedCardState.DEFAULT);
        setOnClickListener(v -> toggle());
    }

    public void setState(SavedCardState newState) {
        if (state == newState) return;
        state = newState;
        updateState();
    }

    public SavedCardState getState() {
        return state;
    }

    public void toggle() {
        if (state == SavedCardState.DEFAULT) {
            setState(SavedCardState.SELECTED);
        } else {
            setState(SavedCardState.DEFAULT);
        }
    }

    private void updateState() {
        if (state == SavedCardState.SELECTED) {
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gp_saved_card_background_selected));
            cvvField.setVisibility(VISIBLE);
        } else {
            setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gp_saved_card_background_default));
            cvvField.setVisibility(GONE);
        }
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

    public GPCardCVVField getCvvField() {
        return cvvField;
    }
}
