# GPOutlinedButton

## 1. Introduction
`GPOutlinedButton` is a secondary action button with a light background and colored border. It shares the same API as `GPPrimaryButton`.

## 2. Params definition
No custom XML attributes are provided. Use `android:text` for the label.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPOutlinedButton
    android:id="@+id/btnCancel"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Cancel" />
```

## 4. How to use in Activity / Fragment
```java
GPOutlinedButton button = findViewById(R.id.btnCancel);
button.setState(GPButtonState.INACTIVE);
```

## 5. How to interact with the UI component
- Update state via `setState(GPButtonState)`
- Retrieve text with `getText()`
- Set text using `setText(CharSequence)`
