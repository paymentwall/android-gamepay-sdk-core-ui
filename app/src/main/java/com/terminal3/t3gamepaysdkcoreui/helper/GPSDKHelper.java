package com.terminal3.t3gamepaysdkcoreui.helper;

import android.content.Context;
import android.util.Log;

import com.terminal3.gpcoreui.models.GPCountry;
import com.terminal3.t3gamepaysdkcoreui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GPSDKHelper {

    public static List<GPCountry> loadCountriesFromJson(Context context) {
        List<GPCountry> countries = new ArrayList<>();

        try {
            // Read JSON file from raw resources
            InputStream inputStream = context.getResources().openRawResource(R.raw.countries);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String jsonString = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(jsonString);

            // Parse JSON and create GPCountry objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObj = jsonArray.getJSONObject(i);
                String name = countryObj.getString("name");
                String code = countryObj.getString("code");

                // Convert to uppercase for consistency with existing flag URLs
                countries.add(new GPCountry(name, code.toUpperCase()));
            }

        } catch (IOException | JSONException e) {
            Log.e("NoPaymentMethods", "Error loading countries from JSON: " + e.getMessage());
            // Fallback to sample countries if JSON loading fails
            countries = createFallbackCountries();
        }

        return countries;
    }

    private static List<GPCountry> createFallbackCountries() {
        List<GPCountry> countries = new ArrayList<>();
        countries.add(new GPCountry("United States", "US"));
        countries.add(new GPCountry("United Kingdom", "GB"));
        countries.add(new GPCountry("Germany", "DE"));
        countries.add(new GPCountry("France", "FR"));
        countries.add(new GPCountry("Canada", "CA"));
        countries.add(new GPCountry("Australia", "AU"));
        countries.add(new GPCountry("Japan", "JP"));
        countries.add(new GPCountry("South Korea", "KR"));
        return countries;
    }

}
