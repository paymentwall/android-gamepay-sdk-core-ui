# AGENTS.md - AI Coding Agent Instructions

## Project Overview

This is the **android-gamepay-sdk-core-ui** library - a reusable payment UI components library for Android applications. The library provides a design system and core UI elements for the GamePaySDK, written in Java with all classes prefixed by `GP` under the package `com.terminal3.gpcoreui`.

### Project Structure
- **gpcoreui** - Main library module containing UI widgets and utilities
- **app** - Sample application demonstrating component usage
- **docs/** - Documentation directory containing component specifications

### Key Technologies
- **Language**: Java
- **Min SDK**: 24
- **Compatibility**: AppCompat
- **Build System**: Gradle
- **Package**: `com.terminal3.gpcoreui`

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

### UI Component Development

1. **Custom Attributes**: Use `app:` namespace for custom attributes
   ```xml
   app:labelText="Card Number"
   app:hintText="Enter card number"
   app:isRequired="true"
   ```

2. **Layout Parameters**: Support `match_parent` and `wrap_content` appropriately

3. **Accessibility**: Implement proper content descriptions and accessibility features

4. **Theming**: Support both light and dark themes

### Available Components (Extend as needed)

Current components include:
- `GPCardNumberField` - Credit card number input with validation
- `GPCardExpiryDateField` - Expiry date input with formatting
- `GPCardCVVField` - CVV/CVC security code input
- `GPDynamicForm` - Dynamic form builder for server-driven UIs

### Validation Framework

- Implement real-time validation for payment fields
- Use validation helpers in the utilities package
- Provide clear error messaging
- Support both individual field validation and form-wide validation

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

## Development Workflow

### Before Starting Work
1. Create branch using the naming convention
2. Review existing component patterns and architecture
3. Check `docs/COMPONENTS.md` for existing similar components

### During Development
1. Follow existing code patterns and architecture
2. Write clean, well-commented Java code
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
