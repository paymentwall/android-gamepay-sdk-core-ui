package com.terminal3.gpcoreui.models;

import com.terminal3.gpcoreui.enums.GPOptionType;
import com.terminal3.gpcoreui.models.DropdownItem;
import com.terminal3.gpcoreui.models.GPOptionValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    private final Map<String, List<GPOption>> groups;
    public CharSequence customLabel = ""; // used for custom labels for redirect options

    public GPOption(String id, GPOptionType type, String label, String hint) {
        this(id, type, label, hint, "", null, Collections.emptyList(), Collections.emptyMap());
    }

    public GPOption(String id,
                    GPOptionType type,
                    String label,
                    String hint,
                    String value,
                    List<DropdownItem> dropdownItems,
                    List<GPOptionValidation> validations,
                    Map<String, List<GPOption>> groups) {
        this.id = id;
        this.type = type;
        this.label = label;
        this.hint = hint;
        this.value = value;
        this.dropdownItems = dropdownItems;
        this.validations = validations == null ? Collections.emptyList() : validations;
        this.groups = groups == null ? Collections.emptyMap() : groups;
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
        if ("epin".equalsIgnoreCase(typeStr)) {
            type = GPOptionType.EPIN;
        } else if ("select".equalsIgnoreCase(typeStr)) {
            type = GPOptionType.DROPDOWN;
        } else if ("redirect".equalsIgnoreCase(typeStr)) {
            type = GPOptionType.REDIRECT;
        } else if ("group".equalsIgnoreCase(typeStr)) {
            type = GPOptionType.GROUP;
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

        Map<String, List<GPOption>> groups = null;
        if (obj.has("groups")) {
            groups = new java.util.HashMap<>();
            JSONObject groupsObj = obj.optJSONObject("groups");
            if (groupsObj != null) {
                java.util.Iterator<String> keys = groupsObj.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    JSONArray arr = groupsObj.optJSONArray(key);
                    if (arr != null) {
                        List<GPOption> groupOptions = new java.util.ArrayList<>();
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject child = arr.getJSONObject(i);
                            groupOptions.add(GPOption.fromJson(child));
                        }
                        groups.put(key, groupOptions);
                    }
                }
            }
        }

        return new GPOption(id, type, label, hint, value, dropdownItems, validationRules, groups);
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

    public Map<String, List<GPOption>> getGroups() {
        return groups;
    }
}
