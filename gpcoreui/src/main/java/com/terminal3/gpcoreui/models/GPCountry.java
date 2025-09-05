package com.terminal3.gpcoreui.models;

public class GPCountry {
    private String country_name;
    private String country_code;
    private static final String FLAG_BASE_URL = "https://feature-t3ts-4.wallapi.bamboo.stuffio.com/images/devrise/flags/4x3/";

    public GPCountry(String countryName, String countryCode) {
        this.country_name = countryName;
        this.country_code = countryCode;
    }

    public String getCountryName() {
        return country_name;
    }

    public void setCountryName(String countryName) {
        this.country_name = countryName;
    }

    public String getCountryCode() {
        return country_code;
    }

    public void setCountryCode(String countryCode) {
        this.country_code = countryCode;
    }

    public String getFlagUrl() {
        return FLAG_BASE_URL + country_code.toLowerCase() + ".svg";
    }
}