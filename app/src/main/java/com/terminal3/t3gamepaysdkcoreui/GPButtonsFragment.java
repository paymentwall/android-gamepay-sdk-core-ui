package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.components.GPOutlinedButton;
import com.terminal3.gpcoreui.components.GPPayAltoButton;
import com.terminal3.gpcoreui.components.GPPrimaryButton;
import com.terminal3.gpcoreui.enums.GPButtonState;

public class GPButtonsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buttons, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GPPrimaryButton primary = view.findViewById(R.id.demoPrimaryButton);
        GPOutlinedButton outlined = view.findViewById(R.id.demoOutlinedButton);
        GPPayAltoButton payAlto = view.findViewById(R.id.demoPayAltoButton);

        primary.setOnClickListener(v -> {
            primary.setState(GPButtonState.LOADING);
            v.postDelayed(() -> primary.setState(GPButtonState.DEFAULT), 1000);
        });

        outlined.setOnClickListener(v -> {
            if (outlined.getState() == GPButtonState.INACTIVE) {
                outlined.setState(GPButtonState.DEFAULT);
            } else {
                outlined.setState(GPButtonState.INACTIVE);
            }
        });

        payAlto.setOnClickListener( v -> {
            Log.d("GPButtonsFragment", "PayAlto button clicked");
        });
    }
}
