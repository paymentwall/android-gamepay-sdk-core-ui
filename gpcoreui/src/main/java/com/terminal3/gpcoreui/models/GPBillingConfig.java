package com.terminal3.gpcoreui.models;

import java.util.List;

public class GPBillingConfig {
    private boolean show_mor_disclaimer;
    private String mor_disclaimer_extra_text;
    private boolean is_tax_enabled;
    private boolean is_coupon_enabled;
    private boolean is_tax_zip_code_enabled;
    private boolean is_tax_region_enabled;
    private boolean is_tax_id_enabled;
    private String selected_region;
    private String selected_zip;
    private String selected_tax_id;
    private String country_code;
    private List<GPRegion> regions;

    public GPBillingConfig() {
    }

    public boolean isShowMorDisclaimer() {
        return show_mor_disclaimer;
    }

    public void setShowMorDisclaimer(boolean showMorDisclaimer) {
        this.show_mor_disclaimer = showMorDisclaimer;
    }

    public String getMorDisclaimerExtraText() {
        return mor_disclaimer_extra_text;
    }

    public void setMorDisclaimerExtraText(String morDisclaimerExtraText) {
        this.mor_disclaimer_extra_text = morDisclaimerExtraText;
    }

    public boolean isTaxEnabled() {
        return is_tax_enabled;
    }

    public void setTaxEnabled(boolean taxEnabled) {
        this.is_tax_enabled = taxEnabled;
    }

    public boolean isCouponEnabled() {
        return is_coupon_enabled;
    }

    public void setCouponEnabled(boolean couponEnabled) {
        this.is_coupon_enabled = couponEnabled;
    }

    public boolean isTaxZipCodeEnabled() {
        return is_tax_zip_code_enabled;
    }

    public void setTaxZipCodeEnabled(boolean taxZipCodeEnabled) {
        this.is_tax_zip_code_enabled = taxZipCodeEnabled;
    }

    public boolean isTaxRegionEnabled() {
        return is_tax_region_enabled;
    }

    public void setTaxRegionEnabled(boolean taxRegionEnabled) {
        this.is_tax_region_enabled = taxRegionEnabled;
    }

    public boolean isTaxIdEnabled() {
        return is_tax_id_enabled;
    }

    public void setTaxIdEnabled(boolean taxIdEnabled) {
        this.is_tax_id_enabled = taxIdEnabled;
    }

    public String getSelectedRegion() {
        return selected_region;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selected_region = selectedRegion;
    }

    public String getSelectedZip() {
        return selected_zip;
    }

    public void setSelectedZip(String selectedZip) {
        this.selected_zip = selectedZip;
    }

    public String getSelectedTaxId() {
        return selected_tax_id;
    }

    public void setSelectedTaxId(String selectedTaxId) {
        this.selected_tax_id = selectedTaxId;
    }

    public String getCountryCode() {
        return country_code;
    }

    public void setCountryCode(String countryCode) {
        this.country_code = countryCode;
    }

    public List<GPRegion> getRegions() {
        return regions;
    }

    public void setRegions(List<GPRegion> regions) {
        this.regions = regions;
    }
}