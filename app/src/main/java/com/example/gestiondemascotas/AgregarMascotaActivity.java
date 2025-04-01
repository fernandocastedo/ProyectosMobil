package com.example.gestiondemascotas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarMascotaActivity extends AppCompatActivity {
    private Spinner tipoMascotaSpinner;
    private EditText nombreEditText, edadEditText, razaEditText, pesoEditText;
    private Spinner estadoSaludSpinner;
    private LinearLayout perroGatoLayout, aveLayout, aveExoticaLayout;
    private SeekBar nivelEnergiaSeekBar, sociabilidadSeekBar;
    private Switch puedeVolarSwitch, puedeVolarExoticaSwitch;
    private EditText tipoAveEditText, tipoAveExoticaEditText, estadoConservacionEditText;
    private Button guardarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);
        inicializarVistas();
        configurarSpinners();
        configurarListeners();
    }

    private void inicializarVistas() {
        tipoMascotaSpinner = findViewById(R.id.tipoMascotaSpinner);
        nombreEditText = findViewById(R.id.nombreEditText);
        edadEditText = findViewById(R.id.edadEditText);
        razaEditText = findViewById(R.id.razaEditText);
        pesoEditText = findViewById(R.id.pesoEditText);
        estadoSaludSpinner = findViewById(R.id.estadoSaludSpinner);
        perroGatoLayout = findViewById(R.id.perroGatoLayout);
        aveLayout = findViewById(R.id.aveLayout);
        aveExoticaLayout = findViewById(R.id.aveExoticaLayout);
        nivelEnergiaSeekBar = findViewById(R.id.nivelEnergiaSeekBar);
        sociabilidadSeekBar = findViewById(R.id.sociabilidadSeekBar);
        puedeVolarSwitch = findViewById(R.id.puedeVolarSwitch);
        puedeVolarExoticaSwitch = findViewById(R.id.puedeVolarExoticaSwitch);
        tipoAveEditText = findViewById(R.id.tipoAveEditText);
        tipoAveExoticaEditText = findViewById(R.id.tipoAveExoticaEditText);
        estadoConservacionEditText = findViewById(R.id.estadoConservacionEditText);
        guardarButton = findViewById(R.id.guardarButton);
    }

    private void configurarSpinners() {
        // Configurar spinner de tipo de mascota
        String[] tiposMascota = {"Perro", "Gato", "Ave", "Ave Exótica"};
        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, tiposMascota);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoMascotaSpinner.setAdapter(adapterTipo);

        // Configurar spinner de estado de salud
        String[] estadosSalud = {"Excelente", "Buena", "Regular", "Mala"};
        ArrayAdapter<String> adapterSalud = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, estadosSalud);
        adapterSalud.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estadoSaludSpinner.setAdapter(adapterSalud);
    }

    private void configurarListeners() {
        tipoMascotaSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                mostrarCamposEspecificos(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        guardarButton.setOnClickListener(v -> guardarMascota());
    }

    private void mostrarCamposEspecificos(int tipoSeleccionado) {
        // Ocultar todos los layouts específicos
        perroGatoLayout.setVisibility(View.GONE);
        aveLayout.setVisibility(View.GONE);
        aveExoticaLayout.setVisibility(View.GONE);

        // Mostrar el layout correspondiente
        switch (tipoSeleccionado) {
            case 0: // Perro
            case 1: // Gato
                perroGatoLayout.setVisibility(View.VISIBLE);
                break;
            case 2: // Ave
                aveLayout.setVisibility(View.VISIBLE);
                break;
            case 3: // Ave Exótica
                aveExoticaLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void guardarMascota() {
        try {
            String nombre = nombreEditText.getText().toString();
            int edad = Integer.parseInt(edadEditText.getText().toString());
            String raza = razaEditText.getText().toString();
            double peso = Double.parseDouble(pesoEditText.getText().toString());
            String estadoSalud = estadoSaludSpinner.getSelectedItem().toString();

            Mascota nuevaMascota = null;
            String tipoSeleccionado = tipoMascotaSpinner.getSelectedItem().toString();

            switch (tipoSeleccionado) {
                case "Perro":
                    nuevaMascota = new Perro(nombre, edad, raza, peso, estadoSalud,
                        nivelEnergiaSeekBar.getProgress(), sociabilidadSeekBar.getProgress());
                    break;
                case "Gato":
                    nuevaMascota = new Gato(nombre, edad, raza, peso, estadoSalud,
                        nivelEnergiaSeekBar.getProgress(), sociabilidadSeekBar.getProgress());
                    break;
                case "Ave":
                    nuevaMascota = new Ave(nombre, edad, raza, peso, estadoSalud,
                        tipoAveEditText.getText().toString(), puedeVolarSwitch.isChecked());
                    break;
                case "Ave Exótica":
                    nuevaMascota = new AveExotica(nombre, edad, raza, peso, estadoSalud,
                        tipoAveExoticaEditText.getText().toString(), puedeVolarExoticaSwitch.isChecked(),
                        estadoConservacionEditText.getText().toString());
                    break;
            }

            if (nuevaMascota != null) {
                Intent intent = new Intent();
                intent.putExtra("mascota", nuevaMascota);
                setResult(RESULT_OK, intent);
                finish();
                Toast.makeText(this, "Mascota agregada exitosamente", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, complete todos los campos correctamente", Toast.LENGTH_SHORT).show();
        }
    }
} 