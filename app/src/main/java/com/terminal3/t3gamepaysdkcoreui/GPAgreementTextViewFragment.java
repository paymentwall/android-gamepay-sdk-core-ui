package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPAgreementTextView;
import com.terminal3.gpcoreui.components.GPPrimaryButton;

public class GPAgreementTextViewFragment extends Fragment {

    private GPPrimaryButton btnDisableView;
    private View rootView; // Your root layout
    private CountDownTimer countDownTimer;
    private final long DISABLE_DURATION = 3000; // 3 seconds

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

        btnDisableView = view.findViewById(R.id.btnDisableView);
        rootView = view; // Replace with your root layout ID

        btnDisableView.setOnClickListener(v -> disableScreenWithCountdown());
    }

    // region Disable view in period

    private void disableScreenWithCountdown() {
        // Disable the entire screen
        disableScreen();

        // Start countdown timer
        startCountdownTimer();
    }

    private void disableScreen() {
        // Disable all clickable views
        setViewAndChildrenEnabled(rootView, false);

        // Add a semi-transparent overlay to visually indicate disabled state
        addDisabledOverlay();

        // Disable the button itself
        btnDisableView.setEnabled(false);
    }

    private void enableScreen() {
        // Enable all views
        setViewAndChildrenEnabled(rootView, true);

        // Remove the overlay
        removeDisabledOverlay();

        // Reset button text and enable it
        btnDisableView.setEnabled(true);
        btnDisableView.setText("Disable View");
    }

    private void setViewAndChildrenEnabled(View view, boolean enabled) {
        view.setEnabled(enabled);

        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                setViewAndChildrenEnabled(child, enabled);
            }
        }
    }

    private void addDisabledOverlay() {
        // Remove existing overlay if any
        removeDisabledOverlay();

        // Create a semi-transparent overlay
//        View overlay = new View(this);
//        overlay.setId(R.id.disabled_overlay);
//        overlay.setBackgroundColor(Color.argb(128, 0, 0, 0)); // Semi-transparent black
//        overlay.setClickable(true); // Block touches
//
//        rootView.addView(overlay);
    }

    private void removeDisabledOverlay() {
//        View overlay = findViewById(R.id.disabled_overlay);
//        if (overlay != null && overlay.getParent() != null) {
//            ((ViewGroup) overlay.getParent()).removeView(overlay);
//        }
    }

    private void startCountdownTimer() {
        // Cancel existing timer if any
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(DISABLE_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update button text with countdown
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                btnDisableView.setText("Enabled in " + secondsRemaining + "s");
            }

            @Override
            public void onFinish() {
                // Re-enable the screen
                enableScreen();
            }
        };

        countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Clean up timer to prevent memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // endregion
}