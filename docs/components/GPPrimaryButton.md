# GPPrimaryButton

## 1. Introduction
`GPPrimaryButton` is a basic action button with dark background intended for primary actions. It supports loading and disabled states.

## 2. Params definition
This component exposes no custom XML attributes. The button text can be specified via the standard `android:text` attribute.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPPrimaryButton
    android:id="@+id/btnSubmit"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Submit" />
```

## 4. How to use in Activity / Fragment
```java
GPPrimaryButton button = findViewById(R.id.btnSubmit);
button.setState(GPButtonState.LOADING);
```

## 5. How to interact with the UI component
- Change state via `setState(GPButtonState)`
- Get current state with `getState()`
- Update text using `setText(CharSequence)`
