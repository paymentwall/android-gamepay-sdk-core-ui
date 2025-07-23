package com.terminal3.sampleapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.terminal3.gamepayui.components.GPPaymentInputContainer;

public class GPMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GPPaymentInputContainer container = new GPPaymentInputContainer(this);
        container.setLabel("Sample Input");
        setContentView(container);
    }
}
