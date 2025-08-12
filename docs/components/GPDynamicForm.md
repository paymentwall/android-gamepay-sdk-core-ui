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

## 3. Grouped Fields

`GPDynamicForm` can render fields conditionally based on a dropdown selection.  
Include an option with `"type":"group"` that shares the same `name` as a `select` option:

```json
{
  "label": "Doc Type",
  "name": "doc_type",
  "type": "select",
  "options": [{"value":"personal","name":"Personal"},{"value":"business","name":"Business"}]
},
{
  "type": "group",
  "name": "doc_type",
  "groups": {
    "personal": [{"label":"CPF","name":"cpf","type":"text"}],
    "business": [{"label":"CNPJ","name":"cnpj","type":"text"}]
  }
}
```

The group remains hidden until the user selects a value from the dropdown.
Once selected, the matching group of options is inserted directly below the dropdown.

For a full example JSON payload, see `app/src/main/res/raw/form_step5.json` in the sample app.
