# GPDefaultInputContainer

## 1. Introduction
`GPDefaultInputContainer` is the base layout for all input widgets. It wraps a `GPDefaultEditText` and adds optional label, helper text and error display. The component manages input states and basic animations.

## 2. Params definition
- **labelText** – text shown above the field
- **text** – initial text value
- **hintText** – hint displayed in the `GPDefaultEditText`
- **helperText** – helper/description below the input
- **errorText** – message shown when validation fails
- **inputType** – standard Android input type for the inner field

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPDefaultInputContainer
    android:id="@+id/myInput"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Label"
    app:hintText="Placeholder" />
```

## 4. How to use in Activity / Fragment
```java
GPDefaultInputContainer container = findViewById(R.id.myInput);
container.setLabel("Card Number");
container.setHintText("4111 1111 1111 1111");
```

## 5. How to interact with UI component
- Retrieve current text using `container.getInput()`
- Display an error with `container.setErrorMessage("Message")`
- Clear an error using `container.clearError()`
- Request focus via `container.setFocus()`
