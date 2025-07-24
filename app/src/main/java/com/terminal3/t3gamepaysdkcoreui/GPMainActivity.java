package com.terminal3.t3gamepaysdkcoreui;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.terminal3.gpcoreui.components.GPDefaultInputContainer;
import com.terminal3.gpcoreui.enums.GPInputState;

public class GPMainActivity extends AppCompatActivity {

    private GPDefaultInputContainer inputContainer;
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
}
