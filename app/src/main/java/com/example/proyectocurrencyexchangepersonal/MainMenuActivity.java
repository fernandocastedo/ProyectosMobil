package com.example.proyectocurrencyexchangepersonal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {

    private Button btnConversiones, btnCrearMonedas, btnDatabaseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnConversiones = findViewById(R.id.btnConversiones);
        btnCrearMonedas = findViewById(R.id.btnCrearMonedas);
        btnDatabaseInfo = findViewById(R.id.btnDatabaseInfo);

        btnConversiones.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, ConversionesActivity.class);
            startActivity(intent);
        });

        btnCrearMonedas.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, CrearMonedasActivity.class);
            startActivity(intent);
        });

        btnDatabaseInfo.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, DatabaseInfoActivity.class);
            startActivity(intent);
        });
    }
}
