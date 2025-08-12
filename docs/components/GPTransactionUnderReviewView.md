# GPTransactionUnderReviewView

## 1. Introduction
`GPTransactionUnderReviewView` displays a centered progress indicator with title and subtitle text and includes a close button in the top-right corner.

## 2. Params definition
This view exposes no custom XML attributes. Use setters to change texts or handle the close action.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.views.GPTransactionUnderReviewView
    android:id="@+id/reviewView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## 4. How to use in Activity / Fragment
```java
GPTransactionUnderReviewView view = findViewById(R.id.reviewView);
view.setOnCloseClickListener(v -> finish());
```

## 5. How to interact with the UI component
Use `setTitleText`, `setSubtitleText`, and `setOnCloseClickListener` to customize the view.
