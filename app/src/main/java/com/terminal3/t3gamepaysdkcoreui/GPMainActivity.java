package com.terminal3.t3gamepaysdkcoreui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.terminal3.gpcoreui.components.GPPrimaryButton;
import com.terminal3.gpcoreui.components.GPOutlinedButton;

import androidx.appcompat.app.AppCompatActivity;

import com.terminal3.gpcoreui.components.GPDefaultInputContainer;
import com.terminal3.gpcoreui.components.GPDropdown;
import com.terminal3.gpcoreui.enums.GPButtonState;
import com.terminal3.gpcoreui.components.GPAgreementCheckboxView;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.utils.textwatchers.GPCardNumberTextWatcher;
import com.terminal3.gpcoreui.utils.validator.rules.GPExpiryDateRule;
import com.terminal3.gpcoreui.utils.validator.GPValidator;
import com.terminal3.gpcoreui.utils.validator.rules.GPCVVRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPCreditCardNumberRule;
import com.terminal3.gpcoreui.utils.validator.rules.GPRequiredRule;

import java.util.ArrayList;
import java.util.List;

public class GPMainActivity extends AppCompatActivity {

    private GPDefaultInputContainer ipCardNumber, ipExpiryDate, ipCVV;
    private GPDropdown dropdown;
    private GPOutlinedButton btnSwitch, btnOpenSavedCard;
    private GPPrimaryButton btnValidate, btnOpenForm;
    private GPAgreementCheckboxView agreementView;
    private GPInputState currentState = GPInputState.DEFAULT;
    private int counter = 0;

    GPValidator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View rootView = getLayoutInflater().inflate(R.layout.main_activity, null);
        initView(rootView);
        setContentView(rootView);

//        GPDefaultInputContainer container = new GPDefaultInputContainer(this);
//        container.setLabel("Sample Input");
//        container.setHintText("Sample Input");
//        setContentView(container);
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

        btnOpenForm.setOnClickListener(v -> {
            Intent intent = new Intent(GPMainActivity.this, GPFormActivity.class);
            startActivity(intent);
        });

        btnOpenSavedCard.setOnClickListener(v -> {
            Intent intent = new Intent(GPMainActivity.this, GPSavedCardActivity.class);
            startActivity(intent);
        });

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

            // Update button text
            btnSwitch.setText(String.format("State : %s", currentState.name()));
        });
    }


    private void setupDropdown() {
        // Create country list
        List<DropdownItem> countries = new ArrayList<>();
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("th", "ThaiLand", "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/banktransferthailand/logo_Bay.png"));
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("ag", "Austria", 0));
// Add more countries...

// Setup dropdown
        dropdown.setLabel("Country");
        dropdown.setItems(countries);
        dropdown.setOnItemSelectedListener(position -> {
            // Handle selection
            dropdown.setText(position.getText());
            Log.d("GPDropdown", "Selected: " + position.getText());
        });
    }
}
