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

import com.terminal3.gpcoreui.components.GPConfirmationBottomSheetFragment;

public class GPCardRemovalFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_removal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        Button testButton = view.findViewById(R.id.btnTestCardRemoval);
        testButton.setOnClickListener(v -> showCardRemovalConfirmation());
    }

    private void showCardRemovalConfirmation() {
        GPConfirmationBottomSheetFragment sheet = new GPConfirmationBottomSheetFragment();
        sheet.setupCardRemovalConfirmation("Mastercard", "Credit", "8217");
        sheet.setOnDecisionListener(new GPConfirmationBottomSheetFragment.OnDecisionListener() {
            @Override
            public void onPositiveClick() {
                // Not used in this case
            }

            @Override
            public void onDestructiveClick() {
                Toast.makeText(getContext(), "Card removed successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Card removal cancelled", Toast.LENGTH_SHORT).show();
            }
        });
        sheet.show(getParentFragmentManager(), "card_removal_confirm");
    }
}