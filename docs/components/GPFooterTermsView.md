# GPFooterTermsView

## 1. Introduction
`GPFooterTermsView` shows the Paymentwall logo along with buttons linking to the Terms and Privacy pages.

## 2. Params definition
This view exposes no custom XML attributes. Use listeners to handle button clicks.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.views.GPFooterTermsView
    android:id="@+id/footerView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## 4. How to use in Activity / Fragment
```java
GPFooterTermsView footer = findViewById(R.id.footerView);
footer.setOnTermsClickListener(v -> openTerms());
footer.setOnPrivacyClickListener(v -> openPrivacy());
```

## 5. How to interact with the UI component
Use `setOnTermsClickListener` and `setOnPrivacyClickListener` to respond to button taps.
