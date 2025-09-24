package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPAgreementTextView;

public class GPAgreementTextViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_agreement_text_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        GPAgreementTextView agreementTextView = view.findViewById(R.id.agreementTextView);
        agreementTextView.configure(
                "Terms of Service",
                "https://www.fasterpay.com/terms-of-service",
                "Privacy Policy", 
                "https://www.fasterpay.com/privacy-policy",
                "FasterPay",
                "\nThank you for choosing FasterPay."
        );
    }
}