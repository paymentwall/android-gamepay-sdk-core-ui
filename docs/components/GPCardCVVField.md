# GPCardCVVField

## 1. Introduction
`GPCardCVVField` is a numeric input limited to four digits. It displays a CVV icon inside the field.

## 2. Params definition
Inherits all parameters from `GPDefaultInputContainer`.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPCardCVVField
    android:id="@+id/cvvField"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="CVV" />
```

## 4. How to use in Activity / Fragment
```java
GPCardCVVField cvvField = findViewById(R.id.cvvField);
String cvv = cvvField.getInput();
```

## 5. How to interact with UI component
- Input filtered to maximum of four digits
- `getInput()` returns the numeric value
