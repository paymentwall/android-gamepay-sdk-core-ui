# GPCardNumberField

## 1. Introduction
`GPCardNumberField` extends `GPDefaultInputContainer` to provide credit card number input with automatic spacing and card brand detection. Features dual card brand display system with both input field drawable and dedicated brand container.

## 2. Layout Structure
The component uses a custom layout (`gp_payment_card_number_input_container.xml`) with the following structure:
- Label text
- Input field with right drawable for detected card brand
- **Card brand container** positioned below the input field showing supported card brands
- Error message container
- Helper text

## 3. Params definition
Inherits all parameters from `GPDefaultInputContainer`.

## 4. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPCardNumberField
    android:id="@+id/cardNumber"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Card Number" />
```

## 5. How to use in Activity / Fragment
```java
GPCardNumberField numberField = findViewById(R.id.cardNumber);
String digits = numberField.getCardNumber();
```

## 6. How to interact with UI component
- `getCardNumber()` returns digits without spaces
- **Dual card brand display system:**
  - **Right drawable**: Shows detected card brand icon in the input field
  - **Brand container**: Shows all supported card brands below the input field
- **Brand highlighting behavior:**
  - When field is empty: All supported brands shown with full opacity
  - When card is detected: Detected brand highlighted, others dimmed (30% opacity)
  - Unsupported cards show "unknown" icon in right drawable

## 7. Card Brand Detection
Supports detection of: Visa, MasterCard, Amex, Discover, Diners Club, and JCB cards. The brand container currently displays Visa and MasterCard icons as the primary supported brands.
