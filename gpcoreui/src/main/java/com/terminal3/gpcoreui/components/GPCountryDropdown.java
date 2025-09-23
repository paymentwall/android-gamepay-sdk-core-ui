package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.terminal3.gpcoreui.R;
import com.terminal3.gpcoreui.adapter.DropdownAdapter;
import com.terminal3.gpcoreui.enums.GPInputState;
import com.terminal3.gpcoreui.enums.GPOptionType;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.models.GPOption;
import com.terminal3.gpcoreui.utils.validator.GPErrorDisplayable;

import java.util.ArrayList;
import java.util.List;

public class GPCountryDropdown extends LinearLayout implements GPOptionView, GPErrorDisplayable {

    // UI Components
    private TextView labelView;
    private LinearLayout countryContainer;
    private ImageView countryFlag;
    private TextView countryName;
    private ImageView dropdownArrow;
    private View errorView;
    private TextView errorTextView;
    private TextView helperView;

    // Dropdown functionality
    private List<DropdownItem> items;
    private List<DropdownItem> originalItems;
    private List<DropdownItem> filteredItems;
    private BottomSheetDialog bottomSheetDialog;
    private DropdownItem selectedItem;
    private DropdownAdapter adapter;
    private GPDefaultEditText searchEditText;

    // Listeners
    private OnItemSelectedListener itemSelectedListener;
    private OnOptionValueChangeListener valueChangeListener;

    // GPOption integration
    private GPOption option;

    // Search functionality
    private boolean isSearchEnabled = false;

    // State management
    private GPInputState currentState = GPInputState.DEFAULT;

    public GPCountryDropdown(Context context) {
        super(context);
        init();
    }

    public GPCountryDropdown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GPCountryDropdown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.gp_country_dropdown_display, this, true);
        
        // Initialize views
        labelView = findViewById(R.id.gp_label);
        countryContainer = findViewById(R.id.gp_country_container);
        countryFlag = findViewById(R.id.gp_country_flag);
        countryName = findViewById(R.id.gp_country_name);
        dropdownArrow = findViewById(R.id.gp_dropdown_arrow);
        errorView = findViewById(R.id.gp_error);
        errorTextView = findViewById(R.id.gp_error_text);
        helperView = findViewById(R.id.gp_helper);

        // Setup click listener
        countryContainer.setOnClickListener(v -> showBottomSheet());
    }

    public void setEnable(boolean isEnable) {
        if (isEnable) {
            dropdownArrow.setVisibility(VISIBLE);
            countryContainer.setOnClickListener(v -> showBottomSheet());
        }
        else {
            dropdownArrow.setVisibility(GONE);
            countryContainer.setOnClickListener(null);
        }
    }

    // region Helper Methods

    private int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getContext().getResources().getDisplayMetrics()
        );
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            View currentFocus = null;
            if (searchEditText != null && searchEditText.hasFocus()) {
                currentFocus = searchEditText;
            }
            if (currentFocus != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                currentFocus.clearFocus();
            }
        }
    }

    private void rotateArrow(boolean show) {
        int arrowResource = show ? R.drawable.gp_ic_arrow_drop_down_up : R.drawable.gp_ic_arrow_drop_down;
        dropdownArrow.setImageResource(arrowResource);
    }

    private void setState(GPInputState state) {
        this.currentState = state;
        updateContainerBackground();
    }

    private void updateContainerBackground() {
        int backgroundRes;
        switch (currentState) {
            case ACTIVE:
                backgroundRes = R.drawable.gp_input_bg_active;
                break;
            case ERROR:
                backgroundRes = R.drawable.gp_input_bg_error;
                break;
//            case FILLED_INACTIVE:
//                backgroundRes = R.drawable.gp_input_bg_filled_inactive;
//                break;
            default:
                backgroundRes = R.drawable.gp_input_bg_default;
                break;
        }
        countryContainer.setBackground(ContextCompat.getDrawable(getContext(), backgroundRes));
    }

    private void animateVisibility(View view, boolean show) {
        AlphaAnimation anim = new AlphaAnimation(show ? 0f : 1f, show ? 1f : 0f);
        anim.setDuration(200);
        anim.setFillAfter(true);
        view.startAnimation(anim);
        view.setVisibility(show ? VISIBLE : GONE);
    }

    // endregion

    // region Public API

    /**
     * Set the dropdown items to display
     * @param items List of dropdown items
     */
    public void setItems(List<DropdownItem> items) {
        this.items = items;
        this.originalItems = items != null ? new ArrayList<>(items) : null;
        this.filteredItems = items != null ? new ArrayList<>(items) : null;
    }

    /**
     * Get the currently selected item
     * @return Selected dropdown item or null if none selected
     */
    public DropdownItem getSelectedItem() {
        return selectedItem;
    }

    /**
     * Set listener for item selection events
     * @param listener Callback for when an item is selected
     */
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.itemSelectedListener = listener;
    }

    /**
     * Enable or disable search functionality in the dropdown
     * @param enabled true to enable search, false to disable
     */
    public void setSearchEnabled(boolean enabled) {
        this.isSearchEnabled = enabled;
    }

    /**
     * Check if search functionality is enabled
     * @return true if search is enabled, false otherwise
     */
    public boolean isSearchEnabled() {
        return isSearchEnabled;
    }

    /**
     * Set the label text
     * @param text Label text
     */
    public void setLabel(CharSequence text) {
        labelView.setText(text);
    }

    /**
     * Set the hint text
     * @param text Hint text
     */
    public void setHintText(CharSequence text) {
        countryName.setHint(text);
    }

    /**
     * Set helper text
     * @param text Helper text
     */
    public void setHelperText(CharSequence text) {
        helperView.setText(text);
    }

    @Override
    public String getInput() {
        return selectedItem.getText();
    }

    /**
     * Interface for dropdown item selection callbacks
     */
    public interface OnItemSelectedListener {
        void onItemSelected(DropdownItem item);
    }

    // endregion

    // region Private Methods

    private DropdownItem findItemById(String id) {
        if (items == null) return null;
        for (DropdownItem item : items) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Set the selected item with optional icon
     * @param item The dropdown item to select
     * @param drawable Optional icon drawable for the item
     */
    public void setSelectedItem(DropdownItem item, @Nullable Drawable drawable) {
        this.selectedItem = item;
        
        if (item != null) {
            updateSelectedItemUI(item, drawable);
            notifyValueChange(item.getId());
        } else {
            clearSelectedItem();
            notifyValueChange("");
        }
    }

    private void updateSelectedItemUI(DropdownItem item, @Nullable Drawable drawable) {
        countryName.setText(item.getText());
        setState(GPInputState.FILLED_INACTIVE);
        
        if (drawable != null) {
            countryFlag.setImageDrawable(drawable);
        } else {
            countryFlag.setImageResource(R.drawable.gp_flag_placeholder);
        }
    }

    private void clearSelectedItem() {
        countryName.setText("");
        setState(GPInputState.DEFAULT);
        countryFlag.setImageResource(R.drawable.gp_flag_placeholder);
    }

    private void notifyValueChange(String value) {
        if (valueChangeListener != null && option != null) {
            valueChangeListener.onOptionValueChanged(option.getId(), value);
        }
    }

    private void showBottomSheet() {
        if (items == null || items.isEmpty()) return;

        rotateArrow(true);

        Context context = getContext();
        bottomSheetDialog = new BottomSheetDialog(context, R.style.BottomSheetDialogTheme);

        View view = LayoutInflater.from(context).inflate(
                R.layout.gp_bottom_sheet_dropdown,
                new FrameLayout(context),
                false
        );

        TextView title = view.findViewById(R.id.gp_bottom_sheet_title);
        String hint = countryName.getHint().toString();
        if (!hint.isBlank()) {
            title.setText(hint);
        }

        // Setup search functionality
        searchEditText = view.findViewById(R.id.gp_search_edit_text);
        if (isSearchEnabled) {
            searchEditText.setVisibility(View.VISIBLE);
            setupSearchFunctionality();
        } else {
            searchEditText.setVisibility(View.GONE);
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        
        // Use filteredItems for the adapter
        List<DropdownItem> itemsToShow = isSearchEnabled ? filteredItems : items;
        adapter = new DropdownAdapter(itemsToShow, (item, drawable) -> {
            // Hide keyboard if it's open
            hideKeyboard();
            
            setSelectedItem(item, drawable);
            if (itemSelectedListener != null) {
                itemSelectedListener.onItemSelected(item);
            }
            bottomSheetDialog.dismiss();
        }, selectedItem);
        recyclerView.setAdapter(adapter);

        bottomSheetDialog.setContentView(view);

        // Configure dialog behavior based on search enabled state
        if (isSearchEnabled) {
            setupFullScreenDialog();
        } else {
            // Expand the bottom sheet fully for non-search mode
            bottomSheetDialog.setOnShowListener(dialog -> {
                View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            });
        }

        bottomSheetDialog.setOnDismissListener(dialog -> {
            rotateArrow(false);
            // Hide keyboard and clear focus when dialog is dismissed
            hideKeyboard();
            // Clear search when dialog is dismissed
            if (isSearchEnabled && searchEditText != null) {
                searchEditText.setText("");
                resetFilteredItems();
            }
        });

        bottomSheetDialog.show();
    }

    private void setupFullScreenDialog() {
        if (bottomSheetDialog == null) return;
        
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            View bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                
                // Set the bottom sheet to full screen height
                ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                bottomSheet.setLayoutParams(layoutParams);
                
                // Configure behavior for full screen
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setSkipCollapsed(true);
                behavior.setPeekHeight(0);
            }
        });

        // Enable soft input adjustment for better keyboard handling
        if (bottomSheetDialog.getWindow() != null) {
            bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    private void setupSearchFunctionality() {
        if (searchEditText == null) return;
        
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterItems(s.toString());
            }
        });
    }

    private void filterItems(String query) {
        if (originalItems == null || filteredItems == null) return;
        
        filteredItems.clear();
        
        if (query == null || query.trim().isEmpty()) {
            filteredItems.addAll(originalItems);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (DropdownItem item : originalItems) {
                if (item.getText().toLowerCase().contains(lowerCaseQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        
        if (adapter != null) {
            adapter.updateItems(filteredItems);
        }
    }

    private void resetFilteredItems() {
        if (originalItems != null && filteredItems != null) {
            filteredItems.clear();
            filteredItems.addAll(originalItems);
        }
    }

    // endregion

    // region GPOptionView implementation

    @Override
    public void bindOption(GPOption option) {
        this.option = option;
        if (option.getLabel() != null) {
            setLabel(option.getLabel());
        }
        if (option.getHint() != null) {
            setHintText(option.getHint());
        }
        if (option.getType() == GPOptionType.DROPDOWN && option.getDropdownItems() != null) {
            setItems(option.getDropdownItems());
        }
        if (option.getValue() != null && !option.getValue().isEmpty()) {
            DropdownItem item = findItemById(option.getValue());
            if (item != null) {
                setSelectedItem(item, null);
            }
        }
    }

    @Override
    public String getOptionId() {
        return option != null ? option.getId() : null;
    }

    @Override
    public String getOptionValue() {
        return selectedItem != null ? selectedItem.getId() : "";
    }

    @Override
    public void setOnOptionValueChangeListener(OnOptionValueChangeListener listener) {
        this.valueChangeListener = listener;
    }

    // endregion

    // region GPErrorDisplayable implementation

    @Override
    public void setErrorMessage(CharSequence errorMessage) {
        errorTextView.setText(errorMessage);
        animateVisibility(errorView, true);
        setState(GPInputState.ERROR);
    }

    @Override
    public void clearError() {
        animateVisibility(errorView, false);
        if (selectedItem != null) {
            setState(GPInputState.FILLED_INACTIVE);
        } else {
            setState(GPInputState.DEFAULT);
        }
    }

    // endregion
}