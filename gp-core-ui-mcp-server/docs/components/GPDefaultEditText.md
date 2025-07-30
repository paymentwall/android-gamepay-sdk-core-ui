# GPDefaultEditText

## 1. Introduction
`GPDefaultEditText` extends `AppCompatEditText` and implements state management for payment inputs. It is automatically used by `GPDefaultInputContainer` but can also be used on its own.

## 2. Params definition
This view exposes no custom XML attributes. Standard `EditText` attributes can be applied.

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPDefaultEditText
    android:id="@+id/myEditText"
    android:layout_width="match_parent"
    android:layout_height="48dp" />
```

## 4. How to use in Activity / Fragment
```java
GPDefaultEditText editText = findViewById(R.id.myEditText);
editText.setState(GPInputState.ACTIVE);
```

## 5. How to interact with UI component
- Update state via `setState(GPInputState)`
- Show an error with `setErrorMessage(String)`
- Clear an error with `clearError()`
- Retrieve current state with `getState()`
