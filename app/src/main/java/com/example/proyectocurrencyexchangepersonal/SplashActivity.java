package com.example.proyectocurrencyexchangepersonal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectocurrencyexchangepersonal.viewmodels.UsuarioViewModel;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 2000; // 2 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UsuarioViewModel usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);
        usuarioViewModel.inicializarUsuariosSiVacio();


        // Después de 2 segundos, iniciar LoginActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finaliza Splash para que no se regrese con back
        }, SPLASH_TIME_OUT);
    }
}
