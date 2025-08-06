# GPRedirectionView

## 1. Introduction
`GPRedirectionView` shows a message explaining that the user will be redirected to complete payment and provides a **Continue** button to trigger the action.

## 2. Params definition
This component exposes no custom XML attributes. The redirect URL is supplied via `bindOption`.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPRedirectionView
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## 4. How to use in Activity / Fragment
```java
GPRedirectionView view = findViewById(R.id.redirectView);
view.bindOption(option);
view.setOnOptionValueChangeListener((id, value) -> {
    // handle redirect action
});
```

## 5. How to interact with the UI component
- Press the **Continue** button to trigger `OnOptionValueChangeListener` with the redirect URL.
