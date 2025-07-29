# GPAgreementCheckboxView

## 1. Introduction
`GPAgreementCheckboxView` displays a checkbox with a rich text paragraph referencing Terms of Service, Privacy Policy and a support email. The links are clickable and open the provided URLs or email client.

## 2. Params definition
This view is configured programmatically using the `configure` method or the dynamic constructor.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPAgreementCheckboxView
    android:id="@+id/agreementView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## 4. How to use in Activity / Fragment
```java
GPAgreementCheckboxView view = findViewById(R.id.agreementView);
view.configure(
        "Terms of Service",
        "https://example.com/tos",
        "Privacy Policy",
        "https://example.com/privacy",
        "support@paymentwall.com",
        "Paymentwall"
);
```

## 5. How to interact with the UI component
- Use `isChecked()` to read the checkbox state.
- Links within the text open the supplied URLs or email client.
