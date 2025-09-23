package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPAgreementCheckboxView;
import com.terminal3.gpcoreui.components.GPCardCVVField;
import com.terminal3.gpcoreui.components.GPCardExpiryDateField;
import com.terminal3.gpcoreui.components.GPCardNumberField;
import com.terminal3.gpcoreui.components.GPDefaultInputContainer;
import com.terminal3.gpcoreui.components.GPDropdown;
import com.terminal3.gpcoreui.components.GPCountryDropdown;
import com.terminal3.gpcoreui.components.GPErrorButton;
import com.terminal3.gpcoreui.components.GPOutlinedButton;
import com.terminal3.gpcoreui.components.GPPrimaryButton;
import com.terminal3.gpcoreui.components.GPSecondaryButton;
import com.terminal3.gpcoreui.enums.GPButtonState;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.utils.textwatchers.GPCardNumberTextWatcher;
import com.terminal3.gpcoreui.utils.validator.GPValidator;
import com.terminal3.gpcoreui.utils.validator.rules.GPCVVRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPCreditCardNumberRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPExpiryDateRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPRequiredRule;
import com.terminal3.gpcoreui.views.GPFooterTermsView;

import java.util.ArrayList;
import java.util.List;

public class GPInputFieldsFragment extends Fragment {

    private GPCardNumberField ipCardNumber;
    private GPDefaultInputContainer ipExpiryDate, ipCVV;
    private GPDropdown dropdown;
    private GPCountryDropdown countryDropdown;
    private GPOutlinedButton btnSwitch, btnOpenSavedCard;
    private GPPrimaryButton btnValidate, btnOpenForm;
    private GPSecondaryButton btnSecondary;
    private GPErrorButton btnError;
    private GPAgreementCheckboxView agreementView;
    private GPFooterTermsView footerView;
    private GPInputState currentState = GPInputState.DEFAULT;
    private int counter = 0;
    private GPValidator validator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input_fields, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View rootView) {
        ipCardNumber = rootView.findViewById(R.id.ip_card_number);
        ipCardNumber.setCardBrandList(java.util.Arrays.asList(
                GPCardNumberField.CardBrand.VISA,
                GPCardNumberField.CardBrand.MASTERCARD,
                GPCardNumberField.CardBrand.DISCOVER,
                GPCardNumberField.CardBrand.DINERS,
                GPCardNumberField.CardBrand.JCB,
                GPCardNumberField.CardBrand.UNIONPAY,
                GPCardNumberField.CardBrand.AMEX
        ));
        ipExpiryDate = rootView.findViewById(R.id.ip_expiry_date);
        ipCVV = rootView.findViewById(R.id.ip_cvv);
        btnSwitch = rootView.findViewById(R.id.btnSwitch);
        btnOpenSavedCard = rootView.findViewById(R.id.btnOpenSavedCard);
        btnValidate = rootView.findViewById(R.id.btnValidate);
        btnOpenForm = rootView.findViewById(R.id.btnOpenForm);
        btnSecondary = rootView.findViewById(R.id.btnSecondary);
        btnError = rootView.findViewById(R.id.btnError);
        dropdown = rootView.findViewById(R.id.countryDropdown);
        countryDropdown = rootView.findViewById(R.id.countryDropdownNew);
        agreementView = rootView.findViewById(R.id.agreementView);
        footerView = rootView.findViewById(R.id.footerView);
        agreementView.configure(
                "Terms of Service",
                "https://example.com/tos",
                "Privacy Policy",
                "https://example.com/privacy",
                "support@paymentwall.com",
                "Paymentwall"
        );
        footerView.setOnTermsClickListener(v -> openUrl("https://www.paymentwall.com/terms"));
        footerView.setOnPrivacyClickListener(v -> openUrl("https://www.paymentwall.com/privacy"));
        setupDropdown();
        setupCountryDropdown();
        setupRules();
        setupListener();
    }

    private void setupRules() {
        validator = new GPValidator.Builder()
                .setAutoDisplayError(true)
                .build();

        ipCardNumber.addTextWatcher(new GPCardNumberTextWatcher());
        validator.addRules(ipCardNumber, List.of(
                new GPRequiredRule("Card number is required"),
                new GPCreditCardNumberRule("Invalid card number")
        ));

        validator.addRules(ipExpiryDate, List.of(
                new GPRequiredRule("Expiry date is required"),
                new GPExpiryDateRule("Invalid expiry date")
        ));

        validator.addRules(ipCVV, List.of(
                new GPRequiredRule("CVV is required"),
                new GPCVVRule("Invalid CVV")
        ));

        validator.addRules(dropdown, List.of(
                new GPRequiredRule("Please select an option")
        ));

        validator.addRules(countryDropdown, List.of(
                new GPRequiredRule("Please select a country")
        ));
    }

    private void setupListener() {
        btnValidate.setOnClickListener(v -> validator.validate());

        btnOpenForm.setOnClickListener(v ->
                ((GPMainActivity) requireActivity()).showFragment(new GPFormHostFragment(), true));

        btnOpenSavedCard.setOnClickListener(v ->
                ((GPMainActivity) requireActivity()).showFragment(new GPSavedCardFragment(), true));

        btnSwitch.setOnClickListener(v -> {
            counter++;
            switch (counter % 4) {
                case 0:
                    currentState = GPInputState.DEFAULT;
                    ipCardNumber.clearError();
                    ipCardNumber.setText("");
                    ipCardNumber.clearFocus();
                    btnOpenForm.setState(GPButtonState.DEFAULT);
                    btnSecondary.setState(GPButtonState.DEFAULT);
                    btnError.setState(GPButtonState.DEFAULT);
                    break;
                case 1:
                    currentState = GPInputState.ACTIVE;
                    ipCardNumber.clearError();
                    ipCardNumber.setText("4111 1111 1111 1111");
                    ipCardNumber.setFocus();
                    btnOpenForm.setState(GPButtonState.DEFAULT);
                    btnSecondary.setState(GPButtonState.DEFAULT);
                    btnError.setState(GPButtonState.DEFAULT);
                    break;
                case 2:
                    currentState = GPInputState.ERROR;
                    ipCardNumber.setText("1234 5678 9012 3456");
                    ipCardNumber.setErrorMessage("Invalid card number");
                    btnOpenForm.setState(GPButtonState.LOADING);
                    btnSecondary.setState(GPButtonState.LOADING);
                    btnError.setState(GPButtonState.LOADING);
                    break;
                case 3:
                    currentState = GPInputState.FILLED_INACTIVE;
                    ipCardNumber.setInactive();
                    btnOpenForm.setState(GPButtonState.INACTIVE);
                    btnSecondary.setState(GPButtonState.INACTIVE);
                    btnError.setState(GPButtonState.INACTIVE);
                    break;
            }
            btnSwitch.setText(String.format("State : %s", currentState.name()));
        });
    }

    private void openUrl(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception ignored) {}
    }

    private void setupDropdown() {
        List<DropdownItem> banks = new ArrayList<>();
        banks.add(new DropdownItem("109", "Mandiri", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/goc/Mandiri.png"));
        banks.add(new DropdownItem("107", "BRI", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/goc/BRI.png"));
        banks.add(new DropdownItem("12", "BCA", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/goc/BCA.png"));
        banks.add(new DropdownItem("11", "BNI", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/goc/BNI.png"));
        banks.add(new DropdownItem("62", "Permata", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/goc/Permata.png"));
        banks.add(new DropdownItem("64", "CIMB", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/goc/CIMB.png"));
        dropdown.setLabel("Select your bank");
        dropdown.setItems(banks);
        dropdown.setOnItemSelectedListener(position -> {
            dropdown.setText(position.getText());
            Log.d("GPDropdown", "Selected: " + position.getText() + " (ID: " + position.getId() + ")");
        });
    }

    private void setupCountryDropdown() {
        List<DropdownItem> countries = new ArrayList<>();
        // Adding sample countries with SVG flag URLs
        countries.add(new DropdownItem("US", "United States", "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg"));
        countries.add(new DropdownItem("CA", "Canada", "https://upload.wikimedia.org/wikipedia/commons/d/d9/Flag_of_Canada_%28Pantone%29.svg"));
        countries.add(new DropdownItem("GB", "United Kingdom", "https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg"));
        countries.add(new DropdownItem("AU", "Australia", "https://upload.wikimedia.org/wikipedia/commons/8/88/Flag_of_Australia_%28converted%29.svg"));
        countries.add(new DropdownItem("DE", "Germany", "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"));
        countries.add(new DropdownItem("FR", "France", "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg"));
        countries.add(new DropdownItem("JP", "Japan", "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg"));
        countries.add(new DropdownItem("KR", "South Korea", "https://upload.wikimedia.org/wikipedia/commons/0/09/Flag_of_South_Korea.svg"));
        countries.add(new DropdownItem("IN", "India", "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg"));
        countries.add(new DropdownItem("CN", "China", "https://upload.wikimedia.org/wikipedia/commons/f/fa/Flag_of_the_People%27s_Republic_of_China.svg"));

        countryDropdown.setLabel("Select Country");
        countryDropdown.setHintText("Choose a country");
        countryDropdown.setSearchEnabled(true);
        countryDropdown.setItems(countries);
        countryDropdown.setOnItemSelectedListener(item -> {
            Log.d("GPCountryDropdown", "Selected: " + item.getText() + " (ID: " + item.getId() + ")");
        });
    }
}
