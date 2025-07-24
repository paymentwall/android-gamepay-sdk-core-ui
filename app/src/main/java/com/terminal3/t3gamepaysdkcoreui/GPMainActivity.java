package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.terminal3.gpcoreui.components.GPDefaultInputContainer;
import com.terminal3.gpcoreui.components.GPDropdown;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.models.DropdownItem;

import java.util.ArrayList;
import java.util.List;

public class GPMainActivity extends AppCompatActivity {

    private GPDefaultInputContainer inputContainer;
    private GPDropdown dropdown;
    private Button btnSwitch;
    private GPInputState currentState = GPInputState.DEFAULT;
    private int counter = 0;

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
        inputContainer = rootView.findViewById(R.id.gp_input_container);
        btnSwitch = rootView.findViewById(R.id.btnSwitch);
        dropdown = rootView.findViewById(R.id.countryDropdown);
        setupDropdown();
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                switch (counter % 4) {
                    case 0:
                        currentState = GPInputState.DEFAULT;
                        inputContainer.clearError();
                        inputContainer.setText("");
                        inputContainer.clearFocus();
                        break;
                    case 1:
                        currentState = GPInputState.ACTIVE;
                        inputContainer.clearError();
                        inputContainer.setText("4111 1111 1111 1111");
                        inputContainer.setFocus();
                        break;
                    case 2:
                        currentState = GPInputState.ERROR;
                        inputContainer.setText("1234 5678 9012 3456");
                        inputContainer.setError("Invalid card number");
                        break;
                    case 3:
                        currentState = GPInputState.FILLED_INACTIVE;
                        inputContainer.setInactive();
                        break;
                }

                // Update button text
                btnSwitch.setText("Cycle States (Current: " + currentState.name() + ")");
            }
        });
    }

    private void setupDropdown() {
        // Create country list
        List<DropdownItem> countries = new ArrayList<>();
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
        countries.add(new DropdownItem("us", "United States", R.drawable.ic_flag_us));
        countries.add(new DropdownItem("ca", "Canada", R.drawable.ic_flag_ca));
        countries.add(new DropdownItem("gb", "Autralia", R.drawable.ic_flag_au));
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
