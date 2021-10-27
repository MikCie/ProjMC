package com.example.projpoprmc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class PompowniaCheck extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pompownia_check);

        String pompownia = getIntent().getStringExtra("Pompownia");

        Log.d("Pompownia", "Pompownia: "+pompownia);
    }
}