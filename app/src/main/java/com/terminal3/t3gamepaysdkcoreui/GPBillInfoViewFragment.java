package com.terminal3.t3gamepaysdkcoreui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPBillInfoView;
import com.terminal3.gpcoreui.components.GPPrimaryButton;
import com.terminal3.gpcoreui.enums.GPButtonState;
import com.terminal3.gpcoreui.models.GPBillingCalculation;
import com.terminal3.gpcoreui.models.GPBillingConfig;
import com.terminal3.gpcoreui.models.GPCountry;
import com.terminal3.gpcoreui.models.GPRegion;
import com.terminal3.t3gamepaysdkcoreui.helper.GPSDKHelper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GPBillInfoViewFragment extends Fragment {

    private GPBillInfoView billInfoView;
    private GPPrimaryButton continueButton;
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<GPCountry> allCountries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill_info_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        billInfoView = view.findViewById(R.id.bill_info_view);
        continueButton = view.findViewById(R.id.continue_button);
        
        loadCountriesFromJson();
        setupListeners();
        setupContinueButton();
        
        // Set initial data with pre-selected country
        setupInitialBillingWithPreSelectedCountry();
//        setInitialBillingCalculation();
    }

    private void loadCountriesFromJson() {
        allCountries = GPSDKHelper.loadCountriesFromJson(requireContext());
    }

    private void setupListeners() {
        billInfoView.setOnBillInfoListener((event, data) -> {
            switch (event) {
                case COUNTRY_SELECTED:
                    GPCountry country = (GPCountry) data;
                    // Simulate loading delay when country changes
                    simulateDataLoading(() -> {
                        resetBillingCalculation();
                        loadBillingConfigForCountry(country.getCountryCode());
                    });
                    break;
                    
                case ZIP_CODE_APPLIED:
                    String zipCode = (String) data;
                    billInfoView.setZipCodeLoading(true);
                    
                    // Simulate tax calculation delay
                    handler.postDelayed(() -> {
                        billInfoView.setZipCodeLoading(false);
                        billInfoView.setZipCodeApplied(true);
                        
                        // Calculate tax based on ZIP code
                        calculateTaxForZipCode(zipCode);
                    }, 2000); // 2 second delay
                    break;
                    
                case REGION_SELECTED:
                    GPRegion region = (GPRegion) data;
                    // Calculate tax for selected region immediately
                    billInfoView.setCalculationLoading(true);

                    handler.postDelayed(() -> {
                        billInfoView.setCalculationLoading(false);
                        billInfoView.setTaxMessage("");
                        // Calculate tax based on selected region
                        calculateTaxForRegion(region.getRCode());
                    }, 2000);
                    break;
                    
                case TAX_ID_APPLIED:
                    String taxId = (String) data;
                    billInfoView.setTaxIdLoading(true);
                    
                    // Simulate validation delay
                    handler.postDelayed(() -> {
                        billInfoView.setTaxIdLoading(false);
                        billInfoView.setTaxIdApplied(true);
                        
                        // Show success message or update calculation
                        updateBillingCalculationWithTaxId(taxId);
                    }, 1500); // 1.5 second delay
                    break;
                    
                case VALIDATION_STATE_CHANGED:
                    Boolean isValid = (Boolean) data;
                    if (isValid) {
                        continueButton.setState(GPButtonState.DEFAULT);
                    } else {
                        continueButton.setState(GPButtonState.INACTIVE);
                    }
                    break;
            }
        });
    }

    private void setupContinueButton() {
        continueButton.setOnClickListener(v -> {
            // Get all form data using getAllData()
            HashMap<String, String> allData = billInfoView.getAllData();
            
            // Create and show alert with user selections
            showSelectionInfoAlert(allData);
        });
    }

    private void showSelectionInfoAlert(HashMap<String, String> data) {
        StringBuilder message = new StringBuilder();
        
        // Country Information
        message.append("Country: ").append(data.get("country_name")).append(" (").append(data.get("country_code")).append(")\n\n");
        
        // ZIP Code Information (if applicable)
        if ("true".equals(data.get("zip_code_enabled")) && !data.get("zip_code").isEmpty()) {
            message.append("ZIP Code: ").append(data.get("zip_code"));
            if ("true".equals(data.get("zip_code_applied"))) {
                message.append(" ✓ Applied");
            }
            message.append("\n\n");
        }
        
        // Region Information (if applicable)
        if ("true".equals(data.get("region_enabled")) && !data.get("region_name").isEmpty()) {
            message.append("State/Province: ").append(data.get("region_name")).append(" (").append(data.get("region_code")).append(")\n\n");
        }
        
        // Tax ID Information (if applicable)
        if ("true".equals(data.get("tax_id_enabled")) && !data.get("tax_id").isEmpty()) {
            message.append("Tax ID: ").append(data.get("tax_id"));
            if ("true".equals(data.get("tax_id_applied"))) {
                message.append(" ✓ Applied");
            }
            message.append("\n\n");
        }
        
        // Billing Calculation
        message.append("=== Billing Summary ===\n");
        message.append("Subtotal: ").append(data.get("subtotal")).append("\n");
        message.append("Tax: ").append(data.get("tax")).append("\n");
        if (!data.get("discount").equals("$0.00")) {
            message.append("Discount: ").append(data.get("discount")).append("\n");
        }
        message.append("Total: ").append(data.get("total")).append("\n");
        
        // Tax message (if any)
        if (!data.get("tax_message").isEmpty()) {
            message.append("\nNote: ").append(data.get("tax_message"));
        }
        
        new AlertDialog.Builder(requireContext())
                .setTitle("Selection Summary")
                .setMessage(message.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    private void setupInitialBillingWithPreSelectedCountry() {
        // Find US country from the loaded countries list
        GPCountry preSelectedCountry = findCountryByCode("US");
        
        // Create billing config for US
        GPBillingConfig config = createBillingConfigForCountry("US");

        GPBillingCalculation calculation = new GPBillingCalculation();
        calculation.setSubtotal(new BigDecimal("100.00"));
        calculation.setTax(new BigDecimal("0.00"));
        calculation.setDiscount(new BigDecimal("0.00"));
        calculation.setTotal(new BigDecimal("8.86"));
        calculation.setTaxMessage("Enter a ZIP code to calculate");

        // Use the new combined setup method
        billInfoView.setupBillingWithPreSelectedCountry(config, calculation, allCountries, preSelectedCountry);
    }
    
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

    private void loadBillingConfigForCountry(String countryCode) {
        GPBillingConfig config = createBillingConfigForCountry(countryCode);
        billInfoView.setBillingConfig(config);
    }
    
    private GPBillingConfig createBillingConfigForCountry(String countryCode) {
        GPBillingConfig config = new GPBillingConfig();
        
        switch (countryCode) {
            case "US":
                config.setCountryCode("US");
                config.setTaxEnabled(true);
                config.setCouponEnabled(false);
                config.setTaxZipCodeEnabled(true);
                config.setTaxRegionEnabled(false);
                config.setTaxIdEnabled(true);
                config.setSelectedZip("");
                break;
                
            case "CA":
                config.setCountryCode("CA");
                config.setTaxEnabled(true);
                config.setCouponEnabled(false);
                config.setTaxZipCodeEnabled(false);
                config.setTaxRegionEnabled(true);
                config.setTaxIdEnabled(false);
//                config.setSelectedRegion("BC");
                config.setRegions(getCanadianProvinces());
                break;
                
            case "DE":
                config.setCountryCode("DE");
                config.setTaxEnabled(true);
                config.setCouponEnabled(false);
                config.setTaxZipCodeEnabled(true);
                config.setTaxRegionEnabled(false);
                config.setTaxIdEnabled(true);
                config.setSelectedZip("");
                config.setSelectedTaxId("");
                break;
                
            default:
                config.setCountryCode(countryCode);
                config.setTaxEnabled(true);
                config.setCouponEnabled(false);
                config.setTaxZipCodeEnabled(false);
                config.setTaxRegionEnabled(false);
                config.setTaxIdEnabled(false);
                break;
        }
        
        return config;
    }

    private List<GPRegion> getCanadianProvinces() {
        return Arrays.asList(
            new GPRegion(555, 2, "AB", "Alberta"),
            new GPRegion(556, 2, "BC", "British Columbia"),
            new GPRegion(557, 2, "MB", "Manitoba"),
            new GPRegion(558, 2, "NB", "New Brunswick"),
            new GPRegion(559, 2, "NL", "Newfoundland and Labrador"),
            new GPRegion(560, 2, "NS", "Nova Scotia"),
            new GPRegion(561, 2, "ON", "Ontario"),
            new GPRegion(562, 2, "PE", "Prince Edward Island"),
            new GPRegion(563, 2, "QC", "Quebec"),
            new GPRegion(564, 2, "SK", "Saskatchewan"),
            new GPRegion(565, 2, "YT", "Yukon Territory"),
            new GPRegion(566, 2, "NT", "Northwest Territories"),
            new GPRegion(567, 2, "NU", "Nunavut")
        );
    }

    private void setInitialBillingCalculation() {
        GPBillingCalculation calculation = new GPBillingCalculation();
        calculation.setSubtotal(new BigDecimal("8.86"));
        calculation.setTax(new BigDecimal("0.00"));
        calculation.setDiscount(new BigDecimal("0.00"));
        calculation.setTotal(new BigDecimal("8.86"));
        calculation.setTaxMessage("Enter a ZIP code to calculate");
        
        billInfoView.setBillingCalculation(calculation);
        billInfoView.setTaxMessage("Enter a ZIP code to calculate");
    }

    private void resetBillingCalculation() {
        GPBillingCalculation calculation = new GPBillingCalculation();
        calculation.setSubtotal(new BigDecimal("8.86"));
        calculation.setTax(new BigDecimal("0.00"));
        calculation.setDiscount(new BigDecimal("0.00"));
        calculation.setTotal(new BigDecimal("8.86"));
        
        billInfoView.setBillingCalculation(calculation);
        billInfoView.setTaxMessage("");
        
        // Reset apply button states
        billInfoView.setZipCodeApplied(false);
        billInfoView.setTaxIdApplied(false);
    }

    private void calculateTaxForZipCode(String zipCode) {
        // Simulate different tax rates based on ZIP code
        BigDecimal taxRate;
        if (zipCode.startsWith("90")) { // California ZIP codes
            taxRate = new BigDecimal("0.0875"); // 8.75%
        } else if (zipCode.startsWith("10")) { // New York ZIP codes
            taxRate = new BigDecimal("0.08"); // 8%
        } else if (zipCode.startsWith("75")) { // Texas ZIP codes
            taxRate = new BigDecimal("0.0625"); // 6.25%
        } else {
            taxRate = new BigDecimal("0.07"); // Default 7%
        }
        
        BigDecimal subtotal = new BigDecimal("8.86");
        BigDecimal tax = subtotal.multiply(taxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal total = subtotal.add(tax);
        
        GPBillingCalculation calculation = new GPBillingCalculation();
        calculation.setSubtotal(subtotal);
        calculation.setTax(tax);
        calculation.setDiscount(new BigDecimal("0.00"));
        calculation.setTotal(total);
        
        billInfoView.setBillingCalculation(calculation);
        billInfoView.setTaxMessage("");
    }

    private void calculateTaxForRegion(String regionCode) {
        // Simulate Canadian provincial tax rates
        BigDecimal taxRate;
        switch (regionCode) {
            case "BC": // British Columbia
                taxRate = new BigDecimal("0.12"); // 12% HST
                break;
            case "ON": // Ontario
                taxRate = new BigDecimal("0.13"); // 13% HST
                break;
            case "AB": // Alberta
                taxRate = new BigDecimal("0.05"); // 5% GST only
                break;
            case "QC": // Quebec
                taxRate = new BigDecimal("0.14975"); // 14.975% GST+QST
                break;
            default:
                taxRate = new BigDecimal("0.10"); // Default 10%
                break;
        }
        
        BigDecimal subtotal = new BigDecimal("8.86");
        BigDecimal tax = subtotal.multiply(taxRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal total = subtotal.add(tax);
        
        GPBillingCalculation calculation = new GPBillingCalculation();
        calculation.setSubtotal(subtotal);
        calculation.setTax(tax);
        calculation.setDiscount(new BigDecimal("0.00"));
        calculation.setTotal(total);
        
        billInfoView.setBillingCalculation(calculation);
    }

    private void updateBillingCalculationWithTaxId(String taxId) {
        // Simulate tax exemption or special calculation for business tax ID
        if (taxId.length() > 5) { // Valid tax ID format
            BigDecimal subtotal = new BigDecimal("8.86");
            BigDecimal discount = new BigDecimal("0.50"); // Tax exemption discount
            BigDecimal total = subtotal.subtract(discount);
            
            GPBillingCalculation calculation = new GPBillingCalculation();
            calculation.setSubtotal(subtotal);
            calculation.setTax(new BigDecimal("0.00"));
            calculation.setDiscount(discount);
            calculation.setTotal(total);
            
            billInfoView.setBillingCalculation(calculation);
        }
    }

    private void simulateDataLoading(Runnable onComplete) {
        // Simulate loading delay for country data
        handler.postDelayed(onComplete, 500); // 0.5 second delay
    }

}