package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.views.GPAlertViewBottom;

public class GPAlertViewBottomFragment extends Fragment {

    private GPAlertViewBottom alertViewBottom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alert_view_bottom, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alertViewBottom = view.findViewById(R.id.alertViewBottom);
        Button btnSecurity = view.findViewById(R.id.btnTestSecurity);
        Button btnUnexpectedError = view.findViewById(R.id.btnTestUnexpectedError);
        Button btnCustom = view.findViewById(R.id.btnTestCustom);

        alertViewBottom.setOnBackToMerchantClickListener(() -> {
            Toast.makeText(requireContext(), "Back to merchant clicked!", Toast.LENGTH_SHORT).show();
        });

        btnSecurity.setOnClickListener(v -> {
            GPAlertViewBottom.UIModel securityModel = GPAlertViewBottom.UIModel.createSecurity(requireContext());
            alertViewBottom.setUIModel(securityModel);
            Toast.makeText(requireContext(), "Security alert model applied", Toast.LENGTH_SHORT).show();
        });

        btnUnexpectedError.setOnClickListener(v -> {
            String customErrorMessage = "Network connection failed. Please check your internet connection and try again.";
            GPAlertViewBottom.UIModel errorModel = GPAlertViewBottom.UIModel.createUnexpected(requireContext(), customErrorMessage);
            alertViewBottom.setUIModel(errorModel);
            Toast.makeText(requireContext(), "Unexpected error model applied", Toast.LENGTH_SHORT).show();
        });

        btnCustom.setOnClickListener(v -> {
            GPAlertViewBottom.UIModel customModel = new GPAlertViewBottom.UIModel();
            customModel.iconRes = com.terminal3.gpcoreui.R.drawable.gp_ic_warning_fill;
            customModel.title = "Custom Alert Title";
            customModel.subtitle = "This is a custom alert message created for testing purposes with longer text to see how it wraps.";
            customModel.buttonText = "Custom Action";
            alertViewBottom.setUIModel(customModel);
            Toast.makeText(requireContext(), "Custom model applied", Toast.LENGTH_SHORT).show();
        });

        // Set default model on load
        GPAlertViewBottom.UIModel defaultModel = GPAlertViewBottom.UIModel.createSecurity(requireContext());
        alertViewBottom.setUIModel(defaultModel);
    }
}