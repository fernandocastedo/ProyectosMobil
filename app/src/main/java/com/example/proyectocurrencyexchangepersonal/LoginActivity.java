package com.example.proyectocurrencyexchangepersonal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectocurrencyexchangepersonal.models.Usuario;
import com.example.proyectocurrencyexchangepersonal.viewmodels.UsuarioViewModel;

import java.util.ArrayList;
import java.util.List;

public class    LoginActivity extends AppCompatActivity {

    private Spinner spinnerUsuarios;
    private EditText etContrasena;
    private Button btnIniciarSesion;

    private UsuarioViewModel usuarioViewModel;
    private List<Usuario> usuariosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinnerUsuarios = findViewById(R.id.spinnerUsuarios);
        etContrasena = findViewById(R.id.etContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);

        usuarioViewModel = new ViewModelProvider(this).get(UsuarioViewModel.class);

        // Observamos la lista de usuarios
        usuarioViewModel.getAllUsuarios().observe(this, new Observer<List<Usuario>>() {
            @Override
            public void onChanged(List<Usuario> usuarios) {
                usuariosList = usuarios;
                List<String> nombres = new ArrayList<>();
                if (usuarios != null) {
                    for (Usuario u : usuarios) {
                        nombres.add(u.getNombre());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            LoginActivity.this,
                            android.R.layout.simple_spinner_item,
                            nombres);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerUsuarios.setAdapter(adapter);
                }
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPos = spinnerUsuarios.getSelectedItemPosition();
                if (selectedPos < 0 || usuariosList.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Seleccione un usuario", Toast.LENGTH_SHORT).show();
                    return;
                }

                Usuario usuarioSeleccionado = usuariosList.get(selectedPos);
                String contrasenaIngresada = etContrasena.getText().toString();

                if (usuarioSeleccionado.getContrasena().equals(contrasenaIngresada)) {
                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();  // Para no volver al login con back

                } else {
                    Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
