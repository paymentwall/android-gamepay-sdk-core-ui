# GPCardNumberField

## 1. Introduction
`GPCardNumberField` extends `GPDefaultInputContainer` to provide credit card number input with automatic spacing and card brand detection.

## 2. Params definition
Inherits all parameters from `GPDefaultInputContainer`.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPCardNumberField
    android:id="@+id/cardNumber"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Card Number" />
```

## 4. How to use in Activity / Fragment
```java
GPCardNumberField numberField = findViewById(R.id.cardNumber);
String digits = numberField.getCardNumber();
```

## 5. How to interact with UI component
- `getCardNumber()` returns digits without spaces
- Card brand icon updates automatically as the user types
