# GamePay CoreUI SDK

`gpcoreui` is an Android library providing reusable payment UI components and validation helpers. The code is written in Java with all classes prefixed by `GP` under the package `com.terminal3.gpcoreui`.

## Modules
- **gpcoreui** – library module containing the UI widgets and utilities.
- **app** – sample application demonstrating usage of the components.

## Installation
Include the module in your `settings.gradle` and add a dependency in your app module:

```gradle
implementation project(":gpcoreui")
```

The library targets `minSdk 24` and uses `AppCompat` for compatibility.

## Usage
Add the desired components in your XML layout. Example card form:

```xml
<com.terminal3.gpcoreui.components.GPCardNumberField
    android:id="@+id/ip_card_number"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Card Number" />

<com.terminal3.gpcoreui.components.GPCardExpiryDateField
    android:id="@+id/ip_expiry_date"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="Exp. Date" />

<com.terminal3.gpcoreui.components.GPCardCVVField
    android:id="@+id/ip_cvv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:labelText="CVV" />
```

To build forms dynamically, use `GPDynamicForm`:

```java
GPDynamicForm form = findViewById(R.id.dynamicForm);
form.setOptions(serverOptions);
Map<String, String> values = form.getValues();
```

See [`docs/COMPONENTS.md`](docs/COMPONENTS.md) for an index of available widgets. Each component has its own document describing parameters and usage.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
