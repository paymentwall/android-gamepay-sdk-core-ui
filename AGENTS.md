# AGENTS.md - AI Coding Agent Instructions

## Project Overview

This is the **android-gamepay-sdk-core-ui** library - a reusable payment UI components library for Android applications. The library provides a design system and core UI elements for the GamePaySDK, written in Java with all classes prefixed by `GP` under the package `com.terminal3.gpcoreui`.

### Key Technologies
- **Language**: Java
- **Min SDK**: 24
- **Compatibility**: AppCompat
- **Build System**: Gradle
- **Package**: `com.terminal3.gpcoreui`

## Project Folder Structure

```
android-gamepay-sdk-core-ui/
├── gpcoreui/                           # Main library module
│   ├── src/main/java/com/terminal3/gpcoreui/
│   │   ├── components/                 # UI components (GPCardNumberField, etc.)
│   │   ├── utils/                      # Utility classes
│   │   ├── validation/                 # Validation helpers
│   │   └── themes/                     # Theme and styling
│   ├── src/main/res/
│   │   ├── values/attrs.xml            # Custom attributes
│   │   ├── values/styles.xml           # Component styles  
│   │   ├── values/colors.xml           # Color definitions
│   │   └── layout/                     # Component layouts
│   └── build.gradle                    # Library dependencies
├── app/                                # Sample application
│   ├── src/main/java/                  # Demo activities
│   ├── src/main/res/layout/            # Sample layouts
│   └── build.gradle                    # App dependencies
├── docs/                               # Documentation
│   ├── COMPONENTS.md                   # Component index
│   └── components/                     # Individual component docs
├── README.md                           # Main documentation
└── settings.gradle                     # Module configuration
```

## Key Components

### Core UI Components
- **GPCardNumberField** - Credit card number input with validation
- **GPCardExpiryDateField** - Expiry date input with MM/YY formatting  
- **GPCardCVVField** - CVV/CVC security code input
- **GPDynamicForm** - Dynamic form builder for server-driven UIs

### Utility Classes
- **ValidationHelpers** - Input validation utilities
- **FormatHelpers** - Text formatting utilities
- **ThemeUtils** - Theme and styling utilities

### Integration Points
- **XML Integration** - Components work in XML layouts with custom attributes
- **Programmatic Integration** - Components can be created and configured in code
- **Dynamic Forms** - Server-driven form generation support

## Branch Naming Convention

**CRITICAL**: All branches MUST follow this exact naming pattern:
```
YYMMMDD_Number_task_name
```

### Examples:
- `25Jul29_001_refactor_dropdown`
- `25Jul30_002_add_payment_form`
- `25Aug01_003_fix_validation_bug`
- `25Aug15_004_update_card_field`

### Rules:
- **YY**: 2-digit year (e.g., 25 for 2025)
- **MMM**: 3-letter month abbreviation (Jan, Feb, Mar, Apr, May, Jun, Jul, Aug, Sep, Oct, Nov, Dec)
- **DD**: 2-digit day with leading zero if needed
- **Number**: 3-digit sequential number with leading zeros (001, 002, etc.)
- **task_name**: 2-4 words describing the task, separated by underscores, all lowercase

## Development Guidelines

### Code Standards

1. **Class Naming**: All classes MUST be prefixed with `GP`
   - Examples: `GPCardNumberField`, `GPCardExpiryDateField`, `GPCardCVVField`, `GPDynamicForm`

2. **Package Structure**: All code under `com.terminal3.gpcoreui`
   - Components: `com.terminal3.gpcoreui.components`
   - Utilities: `com.terminal3.gpcoreui.utils`
   - Validation: `com.terminal3.gpcoreui.validation`

3. **Language**: Pure Java (no Kotlin)

4. **Compatibility**: Maintain AppCompat compatibility for broad device support

5. **Code Organization**: Group code using #region tags for better readability and navigation

### Requirements for New UI Element Development

1. **Naming & Structure**
   - **Class Name**: Must start with `GP` prefix (e.g., `GPPaymentButton`)
   - **Package**: Place in `com.terminal3.gpcoreui.components`
   - **Language**: Pure Java only (no Kotlin)

2. **Technical Requirements**
   - **Min SDK**: Support Android API 24+
   - **Compatibility**: Use AppCompat for backward compatibility
   - **Threading**: Main UI thread safe
   - **Memory**: Efficient resource usage

3. **Component Architecture**
   ```java
   public class GPNewComponent extends AppCompatEditText {
       // Custom attributes support
       // Validation integration
       // Theme support
       // Accessibility features
   }
   ```

4. **XML Attributes**
   - Define custom attributes in `attrs.xml`
   - Use `app:` namespace for custom properties
   - Support common attributes like `labelText`, `hintText`, `isRequired`

5. **Validation Integration**
   - Implement real-time or trigger validation
   - Use existing validation helpers
   - Provide clear error states and messages
   - Support both individual and form-wide validation

6. **Theming Support**
   - Support light and dark themes
   - Use color resources from `colors.xml`
   - Apply consistent styling patterns
   - Allow customization through attributes

7. **Accessibility**
   - Proper content descriptions
   - Screen reader support
   - Focus management
   - Touch target size compliance (48dp minimum)

8. **Documentation Requirements**
   - Create component documentation in `docs/components/GP[ComponentName].md`
   - Update main component index in `docs/COMPONENTS.md`
   - Include usage examples in sample app
   - Document all public methods and attributes

9. **Testing Integration**
   - Add component to sample app
   - Test on multiple screen sizes
   - Verify both orientations work
   - Test with different input methods
   - Validate accessibility features

10. **Code Organization**
    - **Group code using #region tags** for better readability and navigation
    - Follow existing code patterns
    - Include proper error handling
    - Write clear comments for complex logic
    - Maintain backward compatibility
    - Use consistent indentation and formatting

## Code Organization Templates

### #region Template for Custom UI Elements

```java
public class GPCustomComponent extends AppCompatEditText {

    //#region Fields and Constants
    private static final String TAG = "GPCustomComponent";
    private String labelText;
    private boolean isRequired;
    private ValidationHelper validationHelper;
    //#endregion

    //#region Constructors
    public GPCustomComponent(Context context) {
        super(context);
        init(context, null);
    }

    public GPCustomComponent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public GPCustomComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    //#endregion

    //#region Initialization
    private void init(Context context, AttributeSet attrs) {
        // Initialize component
        initAttributes(context, attrs);
        setupValidation();
        setupStyling();
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        // Parse custom attributes
    }

    private void setupValidation() {
        // Setup validation logic
    }

    private void setupStyling() {
        // Apply themes and styling
    }
    //#endregion

    //#region Public API Methods
    public void setLabelText(String labelText) {
        this.labelText = labelText;
        // Update UI
    }

    public String getLabelText() {
        return labelText;
    }

    public void setRequired(boolean required) {
        this.isRequired = required;
        // Update validation
    }

    public boolean isRequired() {
        return isRequired;
    }

    public boolean isValid() {
        return validationHelper.validate(getText().toString());
    }
    //#endregion

    //#region Validation Methods
    private boolean validateInput(String input) {
        // Validation logic
        return true;
    }

    private void showValidationError(String error) {
        // Display error state
    }

    private void clearValidationError() {
        // Clear error state
    }
    //#endregion

    //#region Event Handlers
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        // Handle text changes
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        // Handle focus changes
    }
    //#endregion

    //#region Styling and Theme Methods
    private void applyTheme() {
        // Apply current theme
    }

    private void updateColors() {
        // Update color scheme
    }
    //#endregion

    //#region Accessibility Methods
    @Override
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        // Setup accessibility
    }
    //#endregion

    //#region Utility Methods
    private String formatInput(String input) {
        // Format input text
        return input;
    }

    private void logDebug(String message) {
        // Debug logging
    }
    //#endregion
}
```

### #region Template for Fragments

```java
public class GPPaymentFragment extends Fragment {

    //#region Fields and Constants
    private static final String TAG = "GPPaymentFragment";
    private static final String ARG_PAYMENT_OPTIONS = "payment_options";
    
    private FragmentGpPaymentBinding binding;
    private GPDynamicForm paymentForm;
    private PaymentViewModel viewModel;
    //#endregion

    //#region Factory Methods
    public static GPPaymentFragment newInstance(PaymentOptions options) {
        GPPaymentFragment fragment = new GPPaymentFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PAYMENT_OPTIONS, options);
        fragment.setArguments(args);
        return fragment;
    }
    //#endregion

    //#region Lifecycle Methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        parseArguments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGpPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupObservers();
        setupListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    //#endregion

    //#region Initialization Methods
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
    }

    private void parseArguments() {
        if (getArguments() != null) {
            // Parse fragment arguments
        }
    }

    private void setupViews() {
        // Initialize UI components
    }

    private void setupObservers() {
        // Setup ViewModel observers
    }

    private void setupListeners() {
        // Setup click listeners and event handlers
    }
    //#endregion

    //#region Event Handlers
    private void onPaymentSubmit() {
        // Handle payment submission
    }

    private void onValidationError(String error) {
        // Handle validation errors
    }
    //#endregion

    //#region UI Update Methods
    private void updatePaymentForm(PaymentOptions options) {
        // Update form with new options
    }

    private void showLoading(boolean show) {
        // Show/hide loading state
    }

    private void showError(String error) {
        // Display error message
    }
    //#endregion

    //#region Utility Methods
    private boolean validateForm() {
        // Validate payment form
        return true;
    }

    private void clearForm() {
        // Clear form data
    }
    //#endregion
}
```

### #region Template for Activities

```java
public class GPPaymentActivity extends AppCompatActivity {

    //#region Fields and Constants
    private static final String TAG = "GPPaymentActivity";
    private static final String EXTRA_PAYMENT_CONFIG = "payment_config";
    private static final int REQUEST_CODE_PAYMENT = 1001;

    private ActivityGpPaymentBinding binding;
    private GPPaymentFragment paymentFragment;
    private PaymentConfig paymentConfig;
    //#endregion

    //#region Intent Factory Methods
    public static Intent createIntent(Context context, PaymentConfig config) {
        Intent intent = new Intent(context, GPPaymentActivity.class);
        intent.putExtra(EXTRA_PAYMENT_CONFIG, config);
        return intent;
    }
    //#endregion

    //#region Lifecycle Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGpPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        parseIntent();
        setupToolbar();
        setupFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
    //#endregion

    //#region Initialization Methods
    private void parseIntent() {
        if (getIntent() != null) {
            paymentConfig = getIntent().getParcelableExtra(EXTRA_PAYMENT_CONFIG);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFragment() {
        paymentFragment = GPPaymentFragment.newInstance(paymentConfig.getPaymentOptions());
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, paymentFragment)
            .commit();
    }
    //#endregion

    //#region Menu and Navigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Handle back press
        super.onBackPressed();
    }
    //#endregion

    //#region Result Handling
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            handlePaymentResult(resultCode, data);
        }
    }

    private void handlePaymentResult(int resultCode, Intent data) {
        // Handle payment result
    }
    //#endregion

    //#region Utility Methods
    private void finishWithResult(PaymentResult result) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("payment_result", result);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    //#endregion
}
```

### #region Template for Utility Classes

```java
public class GPValidationHelper {

    //#region Constants
    private static final String TAG = "GPValidationHelper";
    private static final String CARD_NUMBER_PATTERN = "^[0-9]{13,19}$";
    private static final String CVV_PATTERN = "^[0-9]{3,4}$";
    //#endregion

    //#region Static Instance
    private static GPValidationHelper instance;

    public static GPValidationHelper getInstance() {
        if (instance == null) {
            instance = new GPValidationHelper();
        }
        return instance;
    }
    //#endregion

    //#region Card Validation Methods
    public boolean isValidCardNumber(String cardNumber) {
        // Validate card number
        return true;
    }

    public boolean isValidExpiryDate(String expiryDate) {
        // Validate expiry date
        return true;
    }

    public boolean isValidCVV(String cvv) {
        // Validate CVV
        return true;
    }
    //#endregion

    //#region Format Methods
    public String formatCardNumber(String cardNumber) {
        // Format card number with spaces
        return cardNumber;
    }

    public String formatExpiryDate(String expiryDate) {
        // Format expiry date as MM/YY
        return expiryDate;
    }
    //#endregion

    //#region Utility Methods
    private boolean matchesPattern(String input, String pattern) {
        return input != null && input.matches(pattern);
    }

    private void logValidationError(String field, String error) {
        // Log validation errors
    }
    //#endregion
}
```

## Documentation Requirements

### MANDATORY: Update Documentation for All New UI Elements

**Every new UI component MUST include comprehensive documentation:**

1. **Component Documentation** (`docs/components/GP[ComponentName].md`):
   ```markdown
   # GP[ComponentName]
   
   ## Overview
   Brief description of the component's purpose
   
   ## Usage
   XML and programmatic usage examples
   
   ## Attributes
   Table of all custom attributes with descriptions
   
   ## Methods
   Public API methods and their descriptions
   
   ## Examples
   Complete working examples
   
   ## Styling
   Theming and customization options
   ```

2. **Update Main Component Index** (`docs/COMPONENTS.md`):
   - Add new component to the index
   - Include brief description and link to detailed documentation

3. **Update README.md**:
   - Add new components to usage examples if applicable
   - Update any relevant sections

4. **Sample App Updates**:
   - Add new components to the sample app (`app` module)
   - Demonstrate proper usage and configuration
   - Include in the main demo activity

### Documentation Standards

- Use clear, concise language
- Include code examples for all public APIs
- Provide both XML and programmatic usage examples
- Document all custom attributes with data types and default values
- Include screenshots or GIFs for visual components
- Maintain consistent formatting across all documentation

## Integration Guidelines

### XML Integration
Components should be easily integrated in XML layouts:
```xml
<com.terminal3.gpcoreui.components.GPCardNumberField
    android:id="@+id/ip_card_number"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Card Number"
    app:isRequired="true" />
```

### Programmatic Integration
Support programmatic creation and configuration:
```java
GPCardNumberField cardField = new GPCardNumberField(context);
cardField.setLabelText("Card Number");
cardField.setRequired(true);
```

### Dynamic Forms
Ensure compatibility with `GPDynamicForm` for server-driven UIs:
```java
GPDynamicForm form = findViewById(R.id.dynamicForm);
form.setOptions(serverOptions);
Map<String, String> values = form.getValues();
```

## Development Workflow

### Before Starting Work
1. Create branch using the naming convention
2. Review existing component patterns and architecture
3. Check `docs/COMPONENTS.md` for existing similar components

### During Development
1. Follow existing code patterns and architecture
2. Write clean, well-commented Java code with proper #region organization
3. Implement proper error handling and validation
4. Test on multiple screen sizes and orientations
5. Ensure accessibility compliance

### Before Submitting
1. **CRITICAL**: Create/update all required documentation
2. Update the sample app with new component usage
3. Test thoroughly on different Android versions (min SDK 24+)
4. Verify component works in both light and dark themes
5. Run all existing tests and add new ones as needed
6. Update `docs/COMPONENTS.md` index

### Testing Requirements

- Test on minimum SDK version (24)
- Test on various screen sizes and densities
- Test both portrait and landscape orientations
- Verify accessibility features work correctly
- Test with different input methods and keyboards
- Validate proper error handling and edge cases

## Common Tasks

### Adding New UI Components
1. Create component class in `com.terminal3.gpcoreui.components`
2. Implement custom attributes in `attrs.xml`
3. Add proper validation and error handling
4. Create comprehensive documentation
5. Update sample app
6. Update component index

### Fixing Bugs
1. Reproduce the issue in the sample app
2. Write test cases that demonstrate the bug
3. Fix the issue while maintaining backward compatibility
4. Update documentation if API changes
5. Verify fix works across different configurations

### Refactoring
1. Maintain backward compatibility
2. Update all affected documentation
3. Test thoroughly to ensure no regressions
4. Update sample app if usage patterns change

## Quality Checklist

Before considering any task complete, verify:

- [ ] Branch name follows `YYMMMDD_Number_task_name` format
- [ ] All classes properly prefixed with `GP`
- [ ] **Code organized with meaningful #region tags**
- [ ] Code follows existing patterns and architecture
- [ ] Component works on min SDK 24+
- [ ] Both light and dark themes supported
- [ ] Accessibility features implemented
- [ ] **Documentation created/updated for all new UI elements**
- [ ] `docs/COMPONENTS.md` index updated
- [ ] Sample app demonstrates new functionality
- [ ] All tests pass
- [ ] No breaking changes to existing APIs

## Resources

- **Main Repository**: https://github.com/paymentwall/android-gamepay-sdk-core-ui
- **Component Documentation**: `docs/COMPONENTS.md`
- **Sample App**: `app` module for usage examples
- **Package Documentation**: All classes under `com.terminal3.gpcoreui`

## Support

For questions about the codebase architecture or component patterns, refer to existing implementations in the `gpcoreui` module and their corresponding documentation in the `docs/` directory.
