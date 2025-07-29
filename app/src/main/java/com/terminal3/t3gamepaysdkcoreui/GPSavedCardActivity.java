package com.terminal3.t3gamepaysdkcoreui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GPSavedCardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_host);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new GPSavedCardFragment())
                    .commit();
        }
    }
}
