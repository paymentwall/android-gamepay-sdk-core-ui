package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

import com.terminal3.gpcoreui.enums.SavedCardState;

import com.terminal3.gpcoreui.components.GPConfirmationBottomSheetFragment;
import com.terminal3.gpcoreui.components.GPSavedCardView;

public class GPSavedCardFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GPSavedCardView card1 = view.findViewById(R.id.savedCard1);
        GPSavedCardView card2 = view.findViewById(R.id.savedCard2);
        GPSavedCardView card3 = view.findViewById(R.id.savedCard3);

        setupCard(card1, "Mastercard Debit", ".... 8217", com.terminal3.gpcoreui.R.drawable.ic_card_brand_master);
        setupCard(card2, "Visa Credit", ".... 4242", com.terminal3.gpcoreui.R.drawable.ic_card_brand_visa);
        setupCard(card3, "Amex", ".... 1005", com.terminal3.gpcoreui.R.drawable.ic_card_brand_amex);

        List<GPSavedCardView> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        for (GPSavedCardView card : cards) {
            card.setOnClickListener(v -> selectCard(card, cards));
            card.setOnMenuClickListener(v -> showConfirmation(card));
        }
    }

    private void setupCard(GPSavedCardView card, String name, String number, int iconRes) {
        card.setCardName(name);
        card.setMaskedCardNumber(number);
        card.setCardBrandIcon(ContextCompat.getDrawable(requireContext(), iconRes));
        card.setState(SavedCardState.DEFAULT);
        card.setTag(name + " " + number);
    }

    private void showConfirmation(GPSavedCardView card) {
        String msg = "Remove " + card.getTag() + "?";
        GPConfirmationBottomSheetFragment sheet = new GPConfirmationBottomSheetFragment();
        sheet.setMessage(msg);
        sheet.setOnDecisionListener(new GPConfirmationBottomSheetFragment.OnDecisionListener() {
            @Override public void onRemove() {
                // Example action
            }

            @Override public void onCancel() {}
        });
        sheet.show(requireActivity().getSupportFragmentManager(), "confirm");
    }

    private void selectCard(GPSavedCardView selected, List<GPSavedCardView> allCards) {
        for (GPSavedCardView card : allCards) {
            if (card == selected) {
                card.setState(SavedCardState.SELECTED);
            } else {
                card.setState(SavedCardState.DEFAULT);
            }
        }
    }
}
