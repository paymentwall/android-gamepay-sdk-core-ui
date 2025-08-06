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

import com.terminal3.gpcoreui.views.GPPollingView;

public class GPPollingTestFragment extends Fragment {

    private GPPollingView pollingView;
    private Button startAnimationButton;
    private Button stopAnimationButton;
    private Button changeTitleButton;
    private Button changeSubtitleButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_polling_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        pollingView = view.findViewById(R.id.pollingView);
        startAnimationButton = view.findViewById(R.id.startAnimationButton);
        stopAnimationButton = view.findViewById(R.id.stopAnimationButton);
        changeTitleButton = view.findViewById(R.id.changeTitleButton);
        changeSubtitleButton = view.findViewById(R.id.changeSubtitleButton);

        setupClickListeners();
        
        // Start animation by default
        pollingView.startPollingAnimation();
    }

    private void setupClickListeners() {
        startAnimationButton.setOnClickListener(v -> {
            pollingView.startPollingAnimation();
            Toast.makeText(getContext(), "Animation started", Toast.LENGTH_SHORT).show();
        });

        stopAnimationButton.setOnClickListener(v -> {
            pollingView.stopPollingAnimation();
            Toast.makeText(getContext(), "Animation stopped", Toast.LENGTH_SHORT).show();
        });

        changeTitleButton.setOnClickListener(v -> {
            pollingView.setTitleText("Custom title text for testing");
            Toast.makeText(getContext(), "Title changed", Toast.LENGTH_SHORT).show();
        });

        changeSubtitleButton.setOnClickListener(v -> {
            pollingView.setSubtitleText("Custom subtitle text for testing purposes");
            Toast.makeText(getContext(), "Subtitle changed", Toast.LENGTH_SHORT).show();
        });

        // Set up Terms and Privacy click listeners
        pollingView.setOnTermsClickListener(v -> 
            Toast.makeText(getContext(), "Terms clicked", Toast.LENGTH_SHORT).show());

        pollingView.setOnPrivacyClickListener(v -> 
            Toast.makeText(getContext(), "Privacy clicked", Toast.LENGTH_SHORT).show());
    }
}