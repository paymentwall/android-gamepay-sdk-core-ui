package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.models.GPBillingCalculation;
import com.terminal3.gpcoreui.models.GPBillingConfig;
import com.terminal3.gpcoreui.models.GPCountry;
import com.terminal3.gpcoreui.models.GPRegion;
import com.terminal3.gpcoreui.utils.transformation.SvgLoaderWithBorder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GPBillInfoView extends LinearLayout {

    // region Properties
    public GPCountryDropdown countryDropdown;
    public GPApplyCodeInputView zipCodeInput;
    public GPDropdown regionDropdown;
    public GPApplyCodeInputView taxIdInput;

    public TextView subtotalLabel;
    public TextView subtotalValue;
    public View vTaxContainer;
    public TextView taxLabel;
    public TextView taxValue;
    public TextView taxMessage;
    public View discountItem;
    public TextView discountLabel;
    public TextView discountValue;
    public TextView totalLabel;
    public TextView totalValue;
    public LinearLayout calculationContainer;
    public ShimmerFrameLayout shimmerContainer;
    public LinearLayout fieldsContainer;
    public ShimmerFrameLayout fieldsShimmerContainer;

    public GPBillingConfig currentConfig;
    public GPBillingCalculation currentCalculation;

    public OnBillInfoListener billInfoListener;

    public GPCountry selectedCountry;
    public GPRegion selectedRegion;
    public List<GPCountry> allCountries;

    // endregion

    // region Listeners
    public enum GPBillInfoEvent {
        COUNTRY_SELECTED,
        ZIP_CODE_APPLIED,
        REGION_SELECTED,
        TAX_ID_APPLIED,
        VALIDATION_STATE_CHANGED
    }

    public interface OnBillInfoListener {
        void onBillInfoEvent(GPBillInfoEvent event, Object data);
    }

    // endregion

    // region Init Methods
    public GPBillInfoView(Context context) {
        super(context);
        init(context);
    }

    public GPBillInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GPBillInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.gp_bill_info_view, this, true);

        initViews();
        setupListeners();
        initializeDefaultValues();
    }

    private void initViews() {
        countryDropdown = findViewById(R.id.gp_country_dropdown);
        zipCodeInput = findViewById(R.id.gp_zip_code_input);
        regionDropdown = findViewById(R.id.gp_region_dropdown);
        taxIdInput = findViewById(R.id.gp_tax_id_input);

        subtotalLabel = findViewById(R.id.gp_subtotal_item).findViewById(R.id.gp_calculation_label);
        subtotalValue = findViewById(R.id.gp_subtotal_item).findViewById(R.id.gp_calculation_value);

        vTaxContainer = findViewById(R.id.gp_tax_container);
        taxLabel = findViewById(R.id.gp_tax_label);
        taxValue = findViewById(R.id.gp_tax_value);
        taxMessage = findViewById(R.id.gp_tax_message);

        discountItem = findViewById(R.id.gp_discount_item);
        discountLabel = findViewById(R.id.gp_discount_item).findViewById(R.id.gp_calculation_label);
        discountValue = findViewById(R.id.gp_discount_item).findViewById(R.id.gp_calculation_value);
        
        totalLabel = findViewById(R.id.gp_total_label);
        totalValue = findViewById(R.id.gp_total_value);
        calculationContainer = findViewById(R.id.gp_calculation_container);
        shimmerContainer = findViewById(R.id.gp_calculation_shimmer_container);
        fieldsContainer = findViewById(R.id.gp_fields_container);
        fieldsShimmerContainer = findViewById(R.id.gp_fields_shimmer_container);
    }

    private void setupListeners() {
        countryDropdown.setLabel(getContext().getString(R.string.gp_country));
        countryDropdown.setHintText(getContext().getString(R.string.gp_country));
        countryDropdown.setSearchEnabled(true);
        countryDropdown.setOnItemSelectedListener(item -> {
            if (billInfoListener != null) {
                setFieldsLoading(true);
                GPCountry country = findCountryByCode(item.getId());
                if (country != null) {
                    selectedCountry = country;
                    billInfoListener.onBillInfoEvent(GPBillInfoEvent.COUNTRY_SELECTED, country);
                    checkValidationState();
                }
            }
        });

        zipCodeInput.setLabel("ZIP code");
        zipCodeInput.setHintText("Enter ZIP code");
        zipCodeInput.setOnApplyClickListener(zipCode -> {
            if (billInfoListener != null) {
                setCalculationLoading(true);
                billInfoListener.onBillInfoEvent(GPBillInfoEvent.ZIP_CODE_APPLIED, zipCode);
//                checkValidationState();
            }
        });

        regionDropdown.setLabel("State/Province");
        regionDropdown.setHintText("Select state/province");
        regionDropdown.setOnItemSelectedListener(item -> {
            if (billInfoListener != null) {
                GPRegion region = findRegionByCode(item.getId());
                if (region != null) {
                    selectedRegion = region;
                    billInfoListener.onBillInfoEvent(GPBillInfoEvent.REGION_SELECTED, region);
//                    checkValidationState();
                }
            }
        });

        taxIdInput.setLabel("Tax ID");
        taxIdInput.setHintText("Enter Tax ID");
        taxIdInput.setOnApplyClickListener(taxId -> {
            if (billInfoListener != null) {
                setCalculationLoading(true);
                billInfoListener.onBillInfoEvent(GPBillInfoEvent.TAX_ID_APPLIED, taxId);
                // Tax ID is optional, so doesn't affect validation
            }
        });
    }

    private void initializeDefaultValues() {
        subtotalLabel.setText("Subtotal");
        discountLabel.setText("Discount");
        totalLabel.setText("Total");
        
        currentCalculation = new GPBillingCalculation();
        updateCalculationDisplay();
    }

    // endregion

    // region Set Billing Config
    public void setupBillingWithPreSelectedCountry(GPBillingConfig config, GPBillingCalculation calculation, List<GPCountry> countries, GPCountry preSelectedCountry) {
        // Set countries first
        setCountries(countries);

        // Pre-select the country without triggering listener
        if (preSelectedCountry != null) {
            setPreSelectedCountry(preSelectedCountry);
        }

        // Set billing config which will configure fields based on the country
        setBillingConfig(config);
        setBillingCalculation(calculation);
    }

    public void setBillingConfig(GPBillingConfig config) {
        this.currentConfig = config;

        // Stop fields loading when config is received
        setFieldsLoading(false);

        // Clear apply input states when changing billing config (e.g., country change)
        zipCodeInput.clear();
        taxIdInput.clear();
        selectedRegion = null;

        updateFieldVisibility();
        populateRegionsDropdown();
        setSelectedValues();
        updateCalculationDisplay();
        checkValidationState();
    }

    public void setPreSelectedCountry(GPCountry country) {
        if (country == null) return;

        this.selectedCountry = country;
        // Set the dropdown selection without triggering the listener
        DropdownItem finalTargetItem = new DropdownItem(country.getCountryCode(), country.getCountryName(), country.getFlagUrl());
        if (finalTargetItem.getPhotoUrl() != null && !finalTargetItem.getPhotoUrl().isEmpty()) {
            if (finalTargetItem.getPhotoUrl().toLowerCase().endsWith(".svg")) {
                SvgLoaderWithBorder.loadSvgWithBorder(
                        getContext(),
                        finalTargetItem.getPhotoUrl(),
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
            }
        }
    }

    private void updateFieldVisibility() {
        if (currentConfig == null) return;
        zipCodeInput.setVisibility(currentConfig.isTaxZipCodeEnabled() ? VISIBLE : GONE);
        regionDropdown.setVisibility(currentConfig.isTaxRegionEnabled() ? VISIBLE : GONE);
        taxIdInput.setVisibility(currentConfig.isTaxIdEnabled() ? VISIBLE : GONE);
        discountItem.setVisibility(currentConfig.isDiscountEnable() ? VISIBLE : GONE);
    }

    private void populateRegionsDropdown() {
        if (currentConfig == null || currentConfig.getRegions() == null) return;

        List<DropdownItem> regionItems = new ArrayList<>();
        for (GPRegion region : currentConfig.getRegions()) {
            regionItems.add(new DropdownItem(region.getRCode(), region.getRName(), 0));
        }
        regionDropdown.setItems(regionItems);
    }

    private void setSelectedValues() {
        if (currentConfig == null) return;

        if (currentConfig.getSelectedZip() != null && !currentConfig.getSelectedZip().isEmpty()) {
            zipCodeInput.setText(currentConfig.getSelectedZip());
            zipCodeInput.setApplied(true);
        }

        if (currentConfig.getSelectedTaxId() != null && !currentConfig.getSelectedTaxId().isEmpty()) {
            taxIdInput.setText(currentConfig.getSelectedTaxId());
            taxIdInput.setApplied(true);
        }

        if (currentConfig.getSelectedRegion() != null && !currentConfig.getSelectedRegion().isEmpty()) {
            GPRegion selectedRegion = findRegionByCode(currentConfig.getSelectedRegion());
            if (selectedRegion != null) {
                DropdownItem regionItem = new DropdownItem(selectedRegion.getRCode(), selectedRegion.getRName(), 0);
                regionDropdown.setSelectedItem(regionItem, null);
            }
        }
    }

    // endregion

    public void setCountries(List<GPCountry> countries) {
        this.allCountries = countries;
        List<DropdownItem> countryItems = new ArrayList<>();
        for (GPCountry country : countries) {
            countryItems.add(new DropdownItem(country.getCountryCode(), country.getCountryName(), country.getFlagUrl()));
        }
        countryDropdown.setItems(countryItems);
    }

    // region Set Billing Calculation
    public void setBillingCalculation(GPBillingCalculation calculation) {
        this.currentCalculation = calculation;
        if (calculation.getTax().compareTo(BigDecimal.ZERO) > 0) {
            currentCalculation.setTaxMessage("");
        }
        else if (currentConfig.isTaxZipCodeEnabled()) {
            currentCalculation.setTaxMessage(getResources().getString(R.string.gp_msg_enter_a_zip_code_to_calculate));
        }
        else if (currentConfig.isTaxRegionEnabled()) {
            currentCalculation.setTaxMessage(getResources().getString(R.string.gp_msg_select_a_state_to_calculate));
        }
        else {
            currentCalculation.setTaxMessage("");
        }
        setCalculationLoading(false);
        updateCalculationDisplay();
        checkValidationState();
    }

    private void updateCalculationDisplay() {
        if (currentCalculation == null) return;

        subtotalValue.setText(currentCalculation.getFormattedSubtotal());
        taxLabel.setText(currentCalculation.getDisplayTaxLabel());
        taxValue.setText(currentCalculation.getFormattedTax());
        discountValue.setText(currentCalculation.getFormattedDiscount());
        totalValue.setText(currentCalculation.getFormattedTotal());

        if (currentCalculation.getTaxMessage() != null && !currentCalculation.getTaxMessage().isEmpty()) {
            taxMessage.setText(currentCalculation.getTaxMessage());
            taxMessage.setVisibility(VISIBLE);
            taxValue.setVisibility(GONE);
        } else {
            taxMessage.setVisibility(GONE);
            taxValue.setVisibility(VISIBLE);
        }
    }

    // endregion

    // region Set Fields State
    public void setZipCodeLoading(boolean loading) {
        zipCodeInput.setLoading(loading);
    }

    public void setZipCodeApplied(boolean applied) {
        zipCodeInput.setApplied(applied);
    }

    public void setTaxIdLoading(boolean loading) {
        taxIdInput.setLoading(loading);
    }

    public void setTaxIdApplied(boolean applied) {
        taxIdInput.setApplied(applied);
    }

    public void setTaxMessage(String message) {
        if (message != null && !message.isEmpty()) {
            taxMessage.setText(message);
            taxMessage.setVisibility(VISIBLE);
        } else {
            taxMessage.setVisibility(GONE);
        }
    }

    public void setCalculationLoading(boolean loading) {
        if (loading) {
            calculationContainer.setVisibility(GONE);
            shimmerContainer.setVisibility(VISIBLE);
            shimmerContainer.startShimmer();
        } else {
            shimmerContainer.stopShimmer();
            shimmerContainer.setVisibility(GONE);
            calculationContainer.setVisibility(VISIBLE);
        }
    }

    public void setFieldsLoading(boolean loading) {
        if (loading) {
            fieldsContainer.setVisibility(GONE);
            fieldsShimmerContainer.setVisibility(VISIBLE);
            fieldsShimmerContainer.startShimmer();
        } else {
            fieldsShimmerContainer.stopShimmer();
            fieldsShimmerContainer.setVisibility(GONE);
            fieldsContainer.setVisibility(VISIBLE);
        }
    }
    // endregion

    // region Setup Listener
    public void setOnBillInfoListener(OnBillInfoListener listener) {
        this.billInfoListener = listener;
    }
    // endregion

    // region Helper
    private GPCountry findCountryByCode(String countryCode) {
        if (allCountries != null) {
            for (GPCountry country : allCountries) {
                if (country.getCountryCode().equals(countryCode)) {
                    return country;
                }
            }
        }
        return new GPCountry("", countryCode);
    }

    private GPRegion findRegionByCode(String regionCode) {
        if (currentConfig == null || currentConfig.getRegions() == null) return null;
        
        for (GPRegion region : currentConfig.getRegions()) {
            if (region.getRCode().equals(regionCode)) {
                return region;
            }
        }
        return null;
    }
    
    private void checkValidationState() {
        boolean isValid = isFormValid();
        if (billInfoListener != null) {
            billInfoListener.onBillInfoEvent(GPBillInfoEvent.VALIDATION_STATE_CHANGED, isValid);
        }
    }
    
    public boolean isFormValid() {
        if (currentConfig == null || selectedCountry == null) {
            return false;
        }
        
        // Check required fields based on config
        if (currentConfig.isTaxZipCodeEnabled()) {
            // ZIP code is required and must be applied
            if (!zipCodeInput.isApplied() || zipCodeInput.getText().trim().isEmpty()) {
                return false;
            }
        }
        
        if (currentConfig.isTaxRegionEnabled()) {
            // Region is required and must be selected
            if (selectedRegion == null) {
                return false;
            }
        }
        
        return true;
    }
    // endregion
    
    // region Data retrieval methods
    public GPCountry getSelectedCountry() {
        return selectedCountry;
    }
    
    public String getSelectedZipCode() {
        return zipCodeInput.getText();
    }
    
    public GPRegion getSelectedRegion() {
        return selectedRegion;
    }
    
    public String getSelectedTaxId() {
        return taxIdInput.getText();
    }
    
    public GPBillingCalculation getCurrentBillingCalculation() {
        return currentCalculation;
    }
    
    public GPBillingConfig getCurrentBillingConfig() {
        return currentConfig;
    }
    
    public HashMap<String, String> getAllData() {
        HashMap<String, String> data = new HashMap<>();
        
        // Country information
        if (selectedCountry != null) {
            data.put("country_code", selectedCountry.getCountryCode());
            data.put("country_name", selectedCountry.getCountryName());
        } else {
            data.put("country_code", "");
            data.put("country_name", "");
        }
        
        // ZIP code information
        data.put("zip_code", getSelectedZipCode() != null ? getSelectedZipCode() : "");
        data.put("zip_code_applied", String.valueOf(zipCodeInput.isApplied()));
        
        // Region information
        if (selectedRegion != null) {
            data.put("region_code", selectedRegion.getRCode());
            data.put("region_name", selectedRegion.getRName());
        } else {
            data.put("region_code", "");
            data.put("region_name", "");
        }
        
        // Tax ID information
        data.put("tax_id", getSelectedTaxId() != null ? getSelectedTaxId() : "");
        data.put("tax_id_applied", String.valueOf(taxIdInput.isApplied()));
        
        // Billing calculation information
        if (currentCalculation != null) {
            data.put("subtotal", currentCalculation.getFormattedSubtotal());
            data.put("tax", currentCalculation.getFormattedTax());
            data.put("discount", currentCalculation.getFormattedDiscount());
            data.put("total", currentCalculation.getFormattedTotal());
            data.put("tax_message", currentCalculation.getTaxMessage() != null ? currentCalculation.getTaxMessage() : "");
        } else {
            data.put("subtotal", "$0.00");
            data.put("tax", "$0.00");
            data.put("discount", "$0.00");
            data.put("total", "$0.00");
            data.put("tax_message", "");
        }
        
        // Form validation state
        data.put("form_valid", String.valueOf(isFormValid()));
        
        // Field visibility states
        if (currentConfig != null) {
            data.put("zip_code_enabled", String.valueOf(currentConfig.isTaxZipCodeEnabled()));
            data.put("region_enabled", String.valueOf(currentConfig.isTaxRegionEnabled()));
            data.put("tax_id_enabled", String.valueOf(currentConfig.isTaxIdEnabled()));
        } else {
            data.put("zip_code_enabled", "false");
            data.put("region_enabled", "false");
            data.put("tax_id_enabled", "false");
        }
        
        return data;
    }

    // endregion
}