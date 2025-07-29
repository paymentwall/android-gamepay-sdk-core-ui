package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.terminal3.t3gamepaysdkcoreui.R;

import java.util.Map;

public class GPFormActivity extends AppCompatActivity implements GPFormStepFragment.OnStepCompleteListener {

    private int currentStep = 0;
    private final int[] stepResources = new int[] {
            R.raw.form_step1,
            R.raw.form_step2,
            R.raw.form_step3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_host);
        if (savedInstanceState == null) {
            showStep(0);
        }
    }

    private void showStep(int index) {
        currentStep = index;
        GPFormStepFragment fragment = GPFormStepFragment.newInstance(stepResources[index]);
        fragment.setOnStepCompleteListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onStepComplete(Map<String, String> values) {
        if (currentStep + 1 < stepResources.length) {
            showStep(currentStep + 1);
        }
    }
}
