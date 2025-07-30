# GPCardExpiryDateField

## 1. Introduction
`GPCardExpiryDateField` formats and validates expiry dates using a `GPExpiryDateTextWatcher`.

## 2. Params definition
Inherits all parameters from `GPDefaultInputContainer`.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPCardExpiryDateField
    android:id="@+id/expiryDate"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Exp. Date" />
```

## 4. How to use in Activity / Fragment
```java
GPCardExpiryDateField expiryField = findViewById(R.id.expiryDate);
String raw = expiryField.getInput();
```

## 5. How to interact with UI component
- Text watcher automatically formats as `MM/YY`
- Use `getInput()` to retrieve digits without the slash
