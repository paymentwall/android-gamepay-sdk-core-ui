package com.terminal3.gpcoreui.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.VectorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
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

import java.util.ArrayList;
import java.util.List;

public class GPDropdown extends GPDefaultInputContainer {

    // Constants
    private static final int ICON_SIZE_DP = 24;
    private static final int DRAWABLE_PADDING_DP = 4;
    private static final int DRAWABLE_INSET_DP = 2;

    // UI Components
    private List<DropdownItem> items;
    private List<DropdownItem> originalItems;
    private List<DropdownItem> filteredItems;
    private BottomSheetDialog bottomSheetDialog;
    private DropdownItem selectedItem;
    private Drawable leftDrawable;
    private GPDefaultEditText searchEditText;
    private ImageView ivClose;
    private DropdownAdapter adapter;

    // Listeners
    private OnItemSelectedListener itemSelectedListener;
    private OnOptionValueChangeListener valueChangeListener;

    // GPOption integration
    private GPOption option;
    
    // Search functionality
    private boolean isSearchEnabled = false;

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
        getEditText().setHint(R.string.gp_select_option);

        setupDrawablePadding();

        setDropdownArrow();
        getEditText().setOnClickListener(v -> showBottomSheet());
    }

    // region Helper Methods

    private void setupDrawablePadding() {
        int padding = dpToPx(DRAWABLE_PADDING_DP);
        getEditText().setCompoundDrawablePadding(padding);
    }

    private void setDropdownArrow() {
        getEditText().setCompoundDrawablesWithIntrinsicBounds(
                0, 0, R.drawable.gp_ic_arrow_drop_down, 0
        );
    }

    private void rotateArrow(boolean show) {
        int arrowResource = show ? R.drawable.gp_ic_arrow_drop_down_up : R.drawable.gp_ic_arrow_drop_down;
        Drawable rightDrawable = ContextCompat.getDrawable(getContext(), arrowResource);
        
        getEditText().setCompoundDrawablesWithIntrinsicBounds(
                leftDrawable,
                null,
                rightDrawable,
                null
        );
    }

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
        getEditText().setText(item.getText());
        setState(GPInputState.FILLED_INACTIVE);
        
        if (drawable != null) {
            leftDrawable = scaleDrawableToIconSize(drawable.mutate());
        } else {
            leftDrawable = null;
        }
    }

    private void clearSelectedItem() {
        getEditText().setText("");
        setState(GPInputState.DEFAULT);
        leftDrawable = null;
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
        String hint = getEditText().getHint().toString();
        if (!hint.isBlank()) {
            title.setText(hint);
        }

        // Setup search functionality
        searchEditText = view.findViewById(R.id.gp_search_edit_text);
        ivClose = view.findViewById(R.id.ivDropdownClose);

        ivClose.setOnClickListener( v -> {
            hideKeyboard();
            bottomSheetDialog.dismiss();
        });

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

        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.gp_divider_default));
        recyclerView.addItemDecoration(divider);

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

    @Override
    public void setState(GPInputState state) {
        super.setState(state);
        // Additional state handling if needed
    }

    // region GPOptionView overrides

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

    // region Drawable Scaling Methods

    /**
     * Scales a drawable to the desired icon size while maintaining aspect ratio
     * @param originalDrawable The original drawable to scale
     * @return Scaled drawable with proper bounds set
     */
    private Drawable scaleDrawableToIconSize(Drawable originalDrawable) {
        int targetIconSize = dpToPx(ICON_SIZE_DP);

        ScaleInfo scaleInfo = calculateScaling(originalDrawable, targetIconSize);

        if (originalDrawable instanceof VectorDrawable) {
            return createScaledVectorDrawable(originalDrawable, scaleInfo);
        } else if (originalDrawable instanceof BitmapDrawable) {
            return createScaledBitmapDrawable((BitmapDrawable) originalDrawable, scaleInfo);
        }

        return originalDrawable;
    }

    private ScaleInfo calculateScaling(Drawable drawable, int targetSize) {
        int originalWidth = drawable.getIntrinsicWidth();
        int originalHeight = drawable.getIntrinsicHeight();
        
        float widthRatio = (float) targetSize / originalWidth;
        float heightRatio = (float) targetSize / originalHeight;
        float scaleFactor = Math.min(widthRatio, heightRatio);
        
        int scaledWidth = (int) (originalWidth * scaleFactor);
        int scaledHeight = (int) (originalHeight * scaleFactor);
        
        return new ScaleInfo(scaledWidth, scaledHeight);
    }

    private Drawable createScaledVectorDrawable(Drawable vectorDrawable, ScaleInfo scaleInfo) {
        Bitmap bitmap = Bitmap.createBitmap(scaleInfo.width, scaleInfo.height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        
        vectorDrawable.setBounds(0, 0, scaleInfo.width, scaleInfo.height);
        vectorDrawable.draw(canvas);
        
        BitmapDrawable scaledDrawable = new BitmapDrawable(getResources(), bitmap);
        return createInsetDrawable(scaledDrawable, scaleInfo);
    }

    private Drawable createScaledBitmapDrawable(BitmapDrawable bitmapDrawable, ScaleInfo scaleInfo) {
        Bitmap originalBitmap = bitmapDrawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, scaleInfo.width, scaleInfo.height, true);
        
        BitmapDrawable scaledDrawable = new BitmapDrawable(getResources(), scaledBitmap);
        return createInsetDrawable(scaledDrawable, scaleInfo);
    }

    private Drawable createInsetDrawable(Drawable drawable, ScaleInfo scaleInfo) {
        int inset = dpToPx(DRAWABLE_INSET_DP);
        Drawable insetDrawable = new InsetDrawable(drawable, 0, inset, 0, 0);
        insetDrawable.setBounds(0, 0, scaleInfo.width, scaleInfo.height);
        return insetDrawable;
    }

    private static class ScaleInfo {
        final int width;
        final int height;
        
        ScaleInfo(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    // endregion
}