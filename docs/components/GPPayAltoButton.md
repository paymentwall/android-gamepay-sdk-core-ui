# GPPayAltoButton

## 1. Introduction
`GPPayAltoButton` displays a payment option with logo and title, commonly used for PayAlto payment selections.

## 2. Params definition
- `app:title` *(string)* – text shown on the button.
- `app:imageSrc` *(reference)* – drawable resource used as the button icon.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPPayAltoButton
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:title="PayPal"
    app:imageSrc="@drawable/ic_pw_logo"/>
```

## 4. How to use in Activity / Fragment
```java
GPPayAltoButton button = new GPPayAltoButton(context);
button.setText("PayPal");
button.setLogo("https://example.com/paypal.png");
```

## 5. How to interact with the UI component
- Update the title using `setText(CharSequence)`
- Set a local drawable icon via `setImageResource(int)`
- Load a remote logo with `setLogo(String)`
