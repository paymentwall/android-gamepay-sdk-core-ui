package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Map;

public class GPFormHostFragment extends Fragment implements GPFormStepFragment.OnStepCompleteListener {

    private int currentStep = 0;
    private final int[] stepResources = new int[] {
            R.raw.form_step5,
            R.raw.form_step6,
            R.raw.form_step1,
            R.raw.form_step2,
            R.raw.form_step3,
            R.raw.form_step4,
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_form_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            showStep(0);
        }
    }

    private void showStep(int index) {
        currentStep = index;
        GPFormStepFragment fragment = GPFormStepFragment.newInstance(stepResources[index]);
        fragment.setOnStepCompleteListener(this);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onStepComplete(Map<String, String> values) {
        Log.d("GPFormHost", "Step complete: " + values);
        if (currentStep + 1 < stepResources.length) {
            showStep(currentStep + 1);
        }
    }
}
