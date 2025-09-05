package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.components.GPDropdown;
import com.terminal3.gpcoreui.components.GPPrimaryButton;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.models.GPCountry;

import java.util.ArrayList;
import java.util.List;

public class GPNoPaymentMethodsView extends LinearLayout {

    private ImageView iconView;
    private TextView titleView;
    private TextView subtitleView;
    private GPDropdown countryDropdown;
    private GPPrimaryButton continueButton;
    
    private List<GPCountry> supportedCountries;
    private OnCountrySelectedListener countrySelectedListener;
    private OnContinueClickListener continueClickListener;

    public GPNoPaymentMethodsView(Context context) {
        super(context);
        init(context);
    }

    public GPNoPaymentMethodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPNoPaymentMethodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_view_no_payment_methods, this, true);
        
        iconView = findViewById(R.id.gp_no_payment_methods_icon);
        titleView = findViewById(R.id.gp_no_payment_methods_title);
        subtitleView = findViewById(R.id.gp_no_payment_methods_subtitle);
        countryDropdown = findViewById(R.id.gp_country_dropdown);
        continueButton = findViewById(R.id.gp_continue_button);
        
        setupCountryDropdown();
        setupContinueButton();
    }

    private void setupCountryDropdown() {
        countryDropdown.setLabel(getContext().getString(R.string.gp_country));
        countryDropdown.setHintText(getContext().getString(R.string.gp_choose_country));
        countryDropdown.setOnItemSelectedListener(item -> {
            if (countrySelectedListener != null && supportedCountries != null) {
                for (GPCountry country : supportedCountries) {
                    if (country.getCountryCode().equals(item.getId())) {
                        countrySelectedListener.onCountrySelected(country);
                        break;
                    }
                }
            }
        });
    }

    private void setupContinueButton() {
        continueButton.setOnClickListener(v -> {
            if (continueClickListener != null) {
                continueClickListener.onContinueClick();
            }
        });
    }

    public void setSupportedCountries(List<GPCountry> countries) {
        this.supportedCountries = countries;
        updateCountryDropdown();
    }

    private void updateCountryDropdown() {
        if (supportedCountries == null) return;
        
        List<DropdownItem> dropdownItems = new ArrayList<>();
        for (GPCountry country : supportedCountries) {
            DropdownItem item = new DropdownItem(
                country.getCountryCode(),
                country.getCountryName(),
                country.getFlagUrl()
            );
            dropdownItems.add(item);
        }
        countryDropdown.setItems(dropdownItems);
    }

    public void setTitle(String title) {
        if (titleView != null) {
            titleView.setText(title);
        }
    }

    public void setSubtitle(String subtitle) {
        if (subtitleView != null) {
            subtitleView.setText(subtitle);
        }
    }

    public void setOnCountrySelectedListener(OnCountrySelectedListener listener) {
        this.countrySelectedListener = listener;
    }

    public void setOnContinueClickListener(OnContinueClickListener listener) {
        this.continueClickListener = listener;
    }

    public GPCountry getSelectedCountry() {
        if (countryDropdown.getSelectedItem() != null && supportedCountries != null) {
            String selectedCountryCode = countryDropdown.getSelectedItem().getId();
            for (GPCountry country : supportedCountries) {
                if (country.getCountryCode().equals(selectedCountryCode)) {
                    return country;
                }
            }
        }
        return null;
    }

    public interface OnCountrySelectedListener {
        void onCountrySelected(GPCountry country);
    }

    public interface OnContinueClickListener {
        void onContinueClick();
    }
}