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
        
        // Setup sample countries
        List<GPCountry> countries = createSampleCountries();
        noPaymentMethodsView.setSupportedCountries(countries);
        
        // Setup listeners
        noPaymentMethodsView.setOnCountrySelectedListener(country -> {
            Log.d("NoPaymentMethods", "Country selected: " + country.getCountryName() + " (" + country.getCountryCode() + ")");
            Toast.makeText(requireContext(), 
                "Selected: " + country.getCountryName(), 
                Toast.LENGTH_SHORT).show();
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

    private List<GPCountry> createSampleCountries() {
        List<GPCountry> countries = new ArrayList<>();
        countries.add(new GPCountry("United States", "us"));
        countries.add(new GPCountry("United Kingdom", "gb"));
        countries.add(new GPCountry("Germany", "de"));
        countries.add(new GPCountry("France", "fr"));
        countries.add(new GPCountry("Canada", "ca"));
        countries.add(new GPCountry("Australia", "au"));
        countries.add(new GPCountry("Japan", "jp"));
        countries.add(new GPCountry("South Korea", "kr"));
        return countries;
    }
}