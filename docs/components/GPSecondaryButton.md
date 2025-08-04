# GPSecondaryButton

## 1. Introduction
`GPSecondaryButton` provides a lighter style for secondary actions. It inherits all behaviour from `GPPrimaryButton` and supports the same states.

## 2. Params definition
This component exposes no custom XML attributes. Use `android:text` to set the label.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPSecondaryButton
    android:id="@+id/btnSecondary"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Secondary" />
```

## 4. How to use in Activity / Fragment
```java
GPSecondaryButton button = findViewById(R.id.btnSecondary);
button.setState(GPButtonState.INACTIVE);
```

## 5. How to interact with the UI component
- Update state via `setState(GPButtonState)`
- Get current state with `getState()`
- Change text using `setText(CharSequence)`
