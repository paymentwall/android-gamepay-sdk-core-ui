# GPDynamicForm

`GPDynamicForm` renders a list of input options provided at runtime. It creates the appropriate `GPOptionView` implementation (input field or dropdown) and exposes callbacks when values change.

## 1. Params definition
- Programmatically supply a list of `GPOption` objects via `setOptions`.
- Listen for value updates with `setOnFormValueChangedListener`.

## 2. Usage Example
```java
GPDynamicForm form = findViewById(R.id.dynamicForm);
form.setOptions(options);
form.setOnFormValueChangedListener((id, value) -> {
    // collect user choices
});
```

Use `getValues()` to retrieve all option values at once.
