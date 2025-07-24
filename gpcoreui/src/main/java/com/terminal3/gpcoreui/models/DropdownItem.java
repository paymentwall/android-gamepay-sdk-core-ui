package com.terminal3.gpcoreui.models;

public class DropdownItem {
    private String id;
    private String text;
    private int iconResId;

    public DropdownItem(String id, String text, int iconResId) {
        this.id = id;
        this.text = text;
        this.iconResId = iconResId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getIconResId() {
        return iconResId;
    }
}