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

Use `getValues()` to retrieve values for currently displayed options.
Call `getAllValues()` to include hidden options with empty values.

## 3. Grouped Fields

`GPDynamicForm` can render fields conditionally based on a dropdown selection.
Include a `groups` object inside the parent option:

```json
{
  "label": "Doc Type",
  "name": "doc_type",
  "type": "select",
  "options": [
    {"value": "personal", "name": "Personal"},
    {"value": "business", "name": "Business"}
  ],
  "groups": {
    "personal": [{"label": "CPF", "name": "cpf", "type": "text"}],
    "business": [{"label": "CNPJ", "name": "cnpj", "type": "text"}]
  }
}
```

The group remains hidden until the user selects a value from the dropdown.
Once selected, the matching group of options is inserted directly below the dropdown.
Nested groups are supported by providing `groups` on any option.

For full example JSON payloads, see `app/src/main/res/raw/form_step5.json` and `app/src/main/res/raw/form_step6.json` in the sample app.
