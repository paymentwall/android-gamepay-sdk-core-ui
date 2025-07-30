package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPAgreementCheckboxView;
import com.terminal3.gpcoreui.components.GPCardCVVField;
import com.terminal3.gpcoreui.components.GPCardExpiryDateField;
import com.terminal3.gpcoreui.components.GPCardNumberField;
import com.terminal3.gpcoreui.components.GPDefaultInputContainer;
import com.terminal3.gpcoreui.components.GPDropdown;
import com.terminal3.gpcoreui.components.GPOutlinedButton;
import com.terminal3.gpcoreui.components.GPPrimaryButton;
import com.terminal3.gpcoreui.enums.GPButtonState;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.utils.textwatchers.GPCardNumberTextWatcher;
import com.terminal3.gpcoreui.utils.validator.GPValidator;
import com.terminal3.gpcoreui.utils.validator.rules.GPCVVRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPCreditCardNumberRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPExpiryDateRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPRequiredRule;

import java.util.ArrayList;
import java.util.List;

public class GPInputFieldsFragment extends Fragment {

    private GPDefaultInputContainer ipCardNumber, ipExpiryDate, ipCVV;
    private GPDropdown dropdown;
    private GPOutlinedButton btnSwitch, btnOpenSavedCard;
    private GPPrimaryButton btnValidate, btnOpenForm;
    private GPAgreementCheckboxView agreementView;
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
        ipExpiryDate = rootView.findViewById(R.id.ip_expiry_date);
        ipCVV = rootView.findViewById(R.id.ip_cvv);
        btnSwitch = rootView.findViewById(R.id.btnSwitch);
        btnOpenSavedCard = rootView.findViewById(R.id.btnOpenSavedCard);
        btnValidate = rootView.findViewById(R.id.btnValidate);
        btnOpenForm = rootView.findViewById(R.id.btnOpenForm);
        dropdown = rootView.findViewById(R.id.countryDropdown);
        agreementView = rootView.findViewById(R.id.agreementView);
        agreementView.configure(
                "Terms of Service",
                "https://example.com/tos",
                "Privacy Policy",
                "https://example.com/privacy",
                "support@paymentwall.com",
                "Paymentwall"
        );
        setupDropdown();
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
                    break;
                case 1:
                    currentState = GPInputState.ACTIVE;
                    ipCardNumber.clearError();
                    ipCardNumber.setText("4111 1111 1111 1111");
                    ipCardNumber.setFocus();
                    btnOpenForm.setState(GPButtonState.DEFAULT);
                    break;
                case 2:
                    currentState = GPInputState.ERROR;
                    ipCardNumber.setText("1234 5678 9012 3456");
                    ipCardNumber.setErrorMessage("Invalid card number");
                    btnOpenForm.setState(GPButtonState.LOADING);
                    break;
                case 3:
                    currentState = GPInputState.FILLED_INACTIVE;
                    ipCardNumber.setInactive();
                    btnOpenForm.setState(GPButtonState.INACTIVE);
                    break;
            }
            btnSwitch.setText(String.format("State : %s", currentState.name()));
        });
    }

    private void setupDropdown() {
        List<DropdownItem> countries = new ArrayList<>();
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("th", "ThaiLand", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/banktransferthailand/logo_Bay.png"));
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("ag", "Austria", 0));
        dropdown.setLabel("Country");
        dropdown.setItems(countries);
        dropdown.setOnItemSelectedListener(position -> {
            dropdown.setText(position.getText());
            Log.d("GPDropdown", "Selected: " + position.getText());
        });
    }
}
