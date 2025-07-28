package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.adapter.DropdownAdapter;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.models.DropdownItem;

import java.util.List;

public class GPDropdown extends GPDefaultInputContainer {

    private List<DropdownItem> items;
    private BottomSheetDialog bottomSheetDialog;
    private OnItemSelectedListener itemSelectedListener;
    private DropdownItem selectedItem;

    public GPDropdown(Context context) {
        super(context);
        init();
    }

    public GPDropdown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GPDropdown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Disable text input
        getEditText().setFocusable(false);
        getEditText().setClickable(true);
        getEditText().setLongClickable(false);
        getEditText().setHint(R.string.select_option);

//        // Set dropdown arrow
        getEditText().setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.ic_arrow_drop_down, 0
        );

        // Handle click to show bottom sheet
        getEditText().setOnClickListener(v -> showBottomSheet());
    }

    private void rotateArrow(boolean show) {
        if (show) {
            getEditText().setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.ic_arrow_drop_down_up, 0
            );
        }
        else {
            getEditText().setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, R.drawable.ic_arrow_drop_down, 0
            );
        }

    }


    public void setItems(List<DropdownItem> items) {
        this.items = items;
    }

    public void setSelectedItem(DropdownItem item) {
        this.selectedItem = item;
        if (item != null) {
            getEditText().setText(item.getText());
            setState(GPInputState.FILLED_INACTIVE);
        } else {
            getEditText().setText("");
            setState(GPInputState.DEFAULT);
        }
    }

    public DropdownItem getSelectedItem() {
        return selectedItem;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.itemSelectedListener = listener;
    }

    private void showBottomSheet() {
        if (items == null || items.isEmpty()) return;

        rotateArrow(true);

        Context context = getContext();
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);

        View view = LayoutInflater.from(context).inflate(
                R.layout.bottom_sheet_dropdown,
                new FrameLayout(context),
                false
        );

        TextView title = view.findViewById(R.id.gp_bottom_sheet_title);
        String hint = getEditText().getHint().toString();
        if (!hint.isBlank()) {
            title.setText(hint);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new DropdownAdapter(items, item -> {
            setSelectedItem(item);
            if (itemSelectedListener != null) {
                itemSelectedListener.onItemSelected(item);
            }
            bottomSheetDialog.dismiss();
        }));

        bottomSheetDialog.setContentView(view);

        // Expand the bottom sheet fully
        bottomSheetDialog.setOnShowListener(dialog -> {
            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        bottomSheetDialog.setOnDismissListener(dialog -> {
            rotateArrow(false);
        });

        bottomSheetDialog.show();
    }

    @Override
    public void setState(GPInputState state) {
        super.setState(state);
        // Additional state handling if needed
    }

    public interface OnItemSelectedListener {
        void onItemSelected(DropdownItem item);
    }
}