package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.terminal3.gpcoreui.models.GPCountry;
import com.terminal3.gpcoreui.views.GPNoPaymentMethodsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GPNoPaymentMethodsTestFragment extends Fragment {

    private GPNoPaymentMethodsView noPaymentMethodsView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_payment_methods_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        noPaymentMethodsView = view.findViewById(R.id.gp_no_payment_methods_view);
        
        // Setup countries from JSON
        List<GPCountry> countries = loadCountriesFromJson();
        noPaymentMethodsView.setSupportedCountries(countries);
        
        // Demo: Set US as pre-selected country
        noPaymentMethodsView.setSelectedCountryByCode("US");
        
        // Setup listeners
        noPaymentMethodsView.setOnCountrySelectedListener(country -> {
            Log.d("NoPaymentMethods", "Country selected: " + country.getCountryName() + " (" + country.getCountryCode() + ")");
            Toast.makeText(requireContext(), 
                "Selected: " + country.getCountryName(), 
                Toast.LENGTH_SHORT).show();

            if (country.getCountryCode().equals("VN")) {
                noPaymentMethodsView.displaySecondView();
            }
            else {
                noPaymentMethodsView.displayFirstView();
            }
        });
        
        noPaymentMethodsView.setOnContinueClickListener(() -> {
            GPCountry selectedCountry = noPaymentMethodsView.getSelectedCountry();
            if (selectedCountry != null) {
                Log.d("NoPaymentMethods", "Continue clicked with country: " + selectedCountry.getCountryName());
                Toast.makeText(requireContext(), 
                    "Continue with " + selectedCountry.getCountryName(), 
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), 
                    "Please select a country first", 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<GPCountry> loadCountriesFromJson() {
        List<GPCountry> countries = new ArrayList<>();
        
        try {
            // Read JSON file from raw resources
            InputStream inputStream = getResources().openRawResource(R.raw.countries);
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
    
    private List<GPCountry> createFallbackCountries() {
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