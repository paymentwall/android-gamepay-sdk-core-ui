package com.terminal3.gpcoreui.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.terminal3.gpcoreui.utils.transformation.SvgLoaderWithBorder;

import java.util.ArrayList;
import java.util.List;

public class GPNoPaymentMethodsView extends LinearLayout {

    private ImageView iconView;
    private TextView titleView;
    private TextView subtitleView;
    private GPDropdown countryDropdown;
    private GPPrimaryButton continueButton;
    
    private List<GPCountry> supportedCountries;
    public List<DropdownItem> dropdownItems = new ArrayList<>();
    private OnCountrySelectedListener countrySelectedListener;
    private OnContinueClickListener continueClickListener;
//    private String selectedCountry = "";

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
        countryDropdown.setHintText(getContext().getString(R.string.gp_country));
        countryDropdown.setSearchEnabled(true);
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
//        this.selectedCountry = selectedCountry;
        this.supportedCountries = countries;
        updateCountryDropdown();
    }

    private void updateCountryDropdown() {
        if (supportedCountries == null) return;

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

    public void setSelectedCountryByCode(String countryCode) {
        if (countryCode == null || supportedCountries == null || dropdownItems == null) {
            return;
        }

        // Find the country by code
        GPCountry targetCountry = null;
        for (GPCountry country : supportedCountries) {
            if (country.getCountryCode().equalsIgnoreCase(countryCode)) {
                targetCountry = country;
                break;
            }
        }

        if (targetCountry == null) {
            return;
        }

        // Find the corresponding dropdown item
        DropdownItem targetItem = null;
        for (DropdownItem item : dropdownItems) {
            if (item.getId().equalsIgnoreCase(countryCode)) {
                targetItem = item;
                break;
            }
        }

        if (targetItem != null) {
            // Load the flag and set as selected
            if (targetItem.getPhotoUrl() != null && !targetItem.getPhotoUrl().isEmpty()) {
                if (targetItem.getPhotoUrl().toLowerCase().endsWith(".svg")) {
                    // Load SVG flag
                    DropdownItem finalTargetItem = targetItem;
                    SvgLoaderWithBorder.loadSvgWithBorder(
                            getContext(),
                            targetItem.getPhotoUrl(),
                            new SvgLoaderWithBorder.SvgLoadCallback() {
                                @Override
                                public void onSuccess(Drawable drawable) {
                                    countryDropdown.setSelectedItem(finalTargetItem, drawable);
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    // Set without flag on error
                                    countryDropdown.setSelectedItem(finalTargetItem, null);
                                }
                            }
                    );
                } else {
                    // For non-SVG images, set item without pre-loaded drawable
                    // The dropdown will handle loading internally
                    countryDropdown.setSelectedItem(targetItem, null);
                }
            } else {
                // No flag URL, set item without drawable
                countryDropdown.setSelectedItem(targetItem, null);
            }
        }
    }

    public interface OnCountrySelectedListener {
        void onCountrySelected(GPCountry country);
    }

    public interface OnContinueClickListener {
        void onContinueClick();
    }
}