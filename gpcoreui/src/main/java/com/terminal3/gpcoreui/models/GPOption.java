package com.terminal3.gpcoreui.models;

import com.terminal3.gpcoreui.enums.GPOptionType;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.models.GPOptionValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

/**
 * Represents an option returned from the server describing which input
 * component should be displayed.
 */
public class GPOption {
    private final String id;
    private final GPOptionType type;
    private final String label;
    private final String hint;
    private final String value;
    private final List<DropdownItem> dropdownItems;
    private final List<GPOptionValidation> validations;

    public GPOption(String id, GPOptionType type, String label, String hint) {
        this(id, type, label, hint, "", null, Collections.emptyList());
    }

    public GPOption(String id,
                    GPOptionType type,
                    String label,
                    String hint,
                    String value,
                    List<DropdownItem> dropdownItems,
                    List<GPOptionValidation> validations) {
        this.id = id;
        this.type = type;
        this.label = label;
        this.hint = hint;
        this.value = value;
        this.dropdownItems = dropdownItems;
        this.validations = validations == null ? Collections.emptyList() : validations;
    }

    /**
     * Creates a {@link GPOption} instance from a JSON object returned by the server.
     */
    public static GPOption fromJson(JSONObject obj) throws JSONException {
        String id = obj.optString("name");
        String label = obj.optString("label");
        String hint = obj.optString("hint");
        String value = obj.optString("value");

        String typeStr = obj.optString("type");
        GPOptionType type;
        if ("select".equalsIgnoreCase(typeStr)) {
            type = GPOptionType.DROPDOWN;
        } else if ("redirect".equalsIgnoreCase(typeStr)) {
            type = GPOptionType.REDIRECT;
        } else {
            type = GPOptionType.INPUT_FIELD;
        }

        List<DropdownItem> dropdownItems = null;
        if (obj.has("options")) {
            dropdownItems = new java.util.ArrayList<>();
            JSONArray arr = obj.optJSONArray("options");
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject item = arr.getJSONObject(i);
                    String itemId = item.optString("value");
                    String text = item.optString("name");
                    String logo = item.optString("logo");
                    dropdownItems.add(new DropdownItem(itemId, text, logo));
                }
            }
        }

        List<GPOptionValidation> validationRules = new java.util.ArrayList<>();
        JSONArray validationArray = obj.optJSONArray("validation");
        if (validationArray != null) {
            for (int i = 0; i < validationArray.length(); i++) {
                JSONObject rule = validationArray.getJSONObject(i);
                validationRules.add(new GPOptionValidation(rule.optString("regex"), rule.optString("message")));
            }
        }

        return new GPOption(id, type, label, hint, value, dropdownItems, validationRules);
    }

    public String getId() {
        return id;
    }

    public GPOptionType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public String getHint() {
        return hint;
    }

    public String getValue() {
        return value;
    }

    public List<DropdownItem> getDropdownItems() {
        return dropdownItems;
    }

    public List<GPOptionValidation> getValidations() {
        return validations;
    }
}
