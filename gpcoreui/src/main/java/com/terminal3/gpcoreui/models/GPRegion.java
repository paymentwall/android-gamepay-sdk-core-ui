package com.terminal3.gpcoreui.models;

public class GPRegion {
    private int r_id;
    private int co_id;
    private String r_code;
    private String r_name;

    public GPRegion(int rId, int coId, String rCode, String rName) {
        this.r_id = rId;
        this.co_id = coId;
        this.r_code = rCode;
        this.r_name = rName;
    }

    public int getRId() {
        return r_id;
    }

    public void setRId(int rId) {
        this.r_id = rId;
    }

    public int getCoId() {
        return co_id;
    }

    public void setCoId(int coId) {
        this.co_id = coId;
    }

    public String getRCode() {
        return r_code;
    }

    public void setRCode(String rCode) {
        this.r_code = rCode;
    }

    public String getRName() {
        return r_name;
    }

    public void setRName(String rName) {
        this.r_name = rName;
    }
}