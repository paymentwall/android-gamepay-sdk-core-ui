package com.terminal3.gpcoreui.models;

public class DropdownItem {
    private String id;
    private String text;
    private int iconResId; // 0 means no icon, -1 means has photo
    private String photoUrl = "";

    public DropdownItem(String id, String text, String photo) {
        this.id = id;
        this.text = text;
        this.photoUrl = photo;
        this.iconResId = -1;
    }

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

    public String getPhotoUrl() {
        return photoUrl;
    }

}