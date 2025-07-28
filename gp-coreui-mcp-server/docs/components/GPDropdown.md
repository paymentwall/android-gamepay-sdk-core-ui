# GPDropdown

## 1. Introduction
`GPDropdown` presents a read‑only field that opens a bottom sheet list of options when tapped.

## 2. Params definition
- **items** – reference to an array resource defining dropdown entries (optional)
- **hint** – text displayed as the hint/title

## 3. How to use in XML
```xml
<com.terminal3.gpcoreui.components.GPDropdown
    android:id="@+id/countryDropdown"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:hint="Select country" />
```

## 4. How to use in Activity / Fragment
```java
GPDropdown dropdown = findViewById(R.id.countryDropdown);
List<DropdownItem> items = ... // create items
dropdown.setItems(items);
dropdown.setOnItemSelectedListener(item -> {
    // handle selection
});
```

## 5. How to interact with UI component
- Call `setItems(List<DropdownItem>)` to provide options
- Listen for selection changes via `setOnItemSelectedListener`
- Use `getSelectedItem()` to retrieve the chosen item
