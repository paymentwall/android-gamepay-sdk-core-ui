package com.terminal3.gpcoreui.components;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.terminal3.gpcoreui.R;

public class GPConfirmationBottomSheetFragment extends BottomSheetDialogFragment {

    private OnDecisionListener decisionListener;
    private String message;

    public GPConfirmationBottomSheetFragment() {}

    public void setMessage(String message) {
        this.message = message;
    }

    public void setOnDecisionListener(OnDecisionListener listener) {
        this.decisionListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.gp_bottom_sheet_confirmation, container, false);

        TextView msgView = root.findViewById(R.id.gp_confirmation_message);
        TextView removeBtn = root.findViewById(R.id.gp_remove_button);
        TextView cancelBtn = root.findViewById(R.id.gp_cancel_button);

        if (message != null) {
            msgView.setText(message);
        }

        removeBtn.setOnClickListener(v -> {
            if (decisionListener != null) decisionListener.onRemove();
            dismiss();
        });

        cancelBtn.setOnClickListener(v -> {
            if (decisionListener != null) decisionListener.onCancel();
            dismiss();
        });

        return root;
    }

    public interface OnDecisionListener {
        void onRemove();
        void onCancel();
    }
}
