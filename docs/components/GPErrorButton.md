# GPErrorButton

## 1. Introduction
`GPErrorButton` is a warning-styled action button used for error or destructive operations. It extends `GPPrimaryButton` and supports the same API.

## 2. Params definition
No custom XML attributes. Use `android:text` for the label.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPErrorButton
    android:id="@+id/btnError"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Error" />
```

## 4. How to use in Activity / Fragment
```java
GPErrorButton button = findViewById(R.id.btnError);
button.setState(GPButtonState.LOADING);
```

## 5. How to interact with the UI component
- Change state via `setState(GPButtonState)`
- Check current state with `getState()`
- Update text using `setText(CharSequence)`
