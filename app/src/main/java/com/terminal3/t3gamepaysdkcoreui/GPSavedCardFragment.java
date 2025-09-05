package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.terminal3.gpcoreui.enums.GPSavedCardState;

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
        GPSavedCardView card4 = view.findViewById(R.id.savedCard4);

        setupCard(card1, "Mastercard Debit (Menu + CVV)", ".... 8217", com.terminal3.gpcoreui.R.drawable.gp_ic_card_brand_master, true, true);
        setupCard(card2, "Visa Credit (Menu only)", ".... 4242", com.terminal3.gpcoreui.R.drawable.gp_ic_card_brand_visa, true, false);
        setupCard(card3, "Amex (CVV only)", ".... 1005", com.terminal3.gpcoreui.R.drawable.gp_ic_card_brand_amex, false, true);
        setupCard(card4, "Discover (No menu, No CVV)", ".... 6011", com.terminal3.gpcoreui.R.drawable.gp_ic_card_brand_discover, false, false);

        List<GPSavedCardView> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);

        for (GPSavedCardView card : cards) {
            card.setOnClickListener(v -> selectCard(card, cards));
            card.setOnMenuClickListener(v -> showConfirmation(card));
        }
    }

    private void setupCard(GPSavedCardView card, String name, String number, int iconRes) {
        card.setCardName(name);
        card.setMaskedCardNumber(number);
        card.setCardBrandIcon(ContextCompat.getDrawable(requireContext(), iconRes));
        card.setState(GPSavedCardState.DEFAULT);
        card.setTag(name + " " + number);
    }

    private void setupCard(GPSavedCardView card, String name, String number, int iconRes, boolean canDeleteCard, boolean isRequireCVV) {
        setupCard(card, name, number, iconRes);
        card.updateConfig(canDeleteCard, isRequireCVV);
    }

    private void showConfirmation(GPSavedCardView card) {
        String title = "Are you sure you want to remove " + card.getTag() + "?";
        String message = "This payment method will no longer be available on websites that use Terminal3.";
        GPConfirmationBottomSheetFragment sheet = new GPConfirmationBottomSheetFragment();
        sheet.setTitle(title);
        sheet.setMessage(message);
        sheet.setShowDestructiveButton(true, "Remove");
        sheet.setShowCancelButton(true, "Cancel");
        sheet.setOnDecisionListener(new GPConfirmationBottomSheetFragment.OnDecisionListener() {
            @Override
            public void onPositiveClick() {
                Log.d("GPSavedCardFragment", "onPositiveClick - " + card.getTag());
            }

            @Override public void onDestructiveClick() {
                Log.d("GPSavedCardFragment", "onDestructiveClick - Card removed: " + card.getTag());
            }

            @Override public void onCancel() {
                Log.d("GPSavedCardFragment", "onCancel - Card not removed: " + card.getTag());
            }
        });
        sheet.show(requireActivity().getSupportFragmentManager(), "confirm");
    }

    private void selectCard(GPSavedCardView selected, List<GPSavedCardView> allCards) {
        for (GPSavedCardView card : allCards) {
            if (card == selected) {
                card.setState(GPSavedCardState.SELECTED);
            } else {
                card.setState(GPSavedCardState.DEFAULT);
            }
        }
    }
}
