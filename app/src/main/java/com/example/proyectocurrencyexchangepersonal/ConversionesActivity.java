package com.example.proyectocurrencyexchangepersonal;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocurrencyexchangepersonal.Adapters.TransaccionMonedaAdapter;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;
import com.example.proyectocurrencyexchangepersonal.viewmodels.TransaccionMonedaViewModel;

import java.util.ArrayList;
import java.util.List;

public class ConversionesActivity extends AppCompatActivity {

    private Spinner spinnerMonedaOrigen, spinnerMonedaDestino;
    private EditText etMonto, etFecha;
    private Button btnConvertir;
    private TextView tvResultados;
    private RecyclerView recyclerViewResultados;
    private TransaccionMonedaViewModel transaccionMonedaViewModel;
    private TransaccionMonedaAdapter adapter;
    private List<String> listaMonedas;

    private void inicializarMonedas() {
        listaMonedas = new ArrayList<>();
        listaMonedas.add("USD - Dólar");
        listaMonedas.add("BOB - Boliviano");
        listaMonedas.add("EUR - Euro");
        listaMonedas.add("GBP - Libra esterlina");
        listaMonedas.add("JPY - Yen japonés");
        // Agrega más monedas según sea necesario
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversiones);

        // Inicializamos los elementos
        spinnerMonedaOrigen = findViewById(R.id.spinnerMonedaOrigen);
        spinnerMonedaDestino = findViewById(R.id.spinnerMonedaDestino);
        etMonto = findViewById(R.id.etMonto);
        etFecha = findViewById(R.id.etFecha);
        btnConvertir = findViewById(R.id.btnConvertir);
        tvResultados = findViewById(R.id.tvResultados);
        recyclerViewResultados = findViewById(R.id.recyclerViewResultados);

        inicializarMonedas();

        // Configuramos el Spinner para Moneda Origen
        ArrayAdapter<String> adapterMonedaOrigen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMonedas);
        adapterMonedaOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonedaOrigen.setAdapter(adapterMonedaOrigen);

        // Configuramos el Spinner para Moneda Destino
        ArrayAdapter<String> adapterMonedaDestino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaMonedas);
        adapterMonedaDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonedaDestino.setAdapter(adapterMonedaDestino);

        // Configurar RecyclerView
        recyclerViewResultados.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransaccionMonedaAdapter();
        recyclerViewResultados.setAdapter(adapter);

        // ViewModel
        transaccionMonedaViewModel = new ViewModelProvider(this).get(TransaccionMonedaViewModel.class);

        // Convertir botón
        btnConvertir.setOnClickListener(v -> {
            String monto = etMonto.getText().toString();
            String fecha = etFecha.getText().toString();

            if (monto.isEmpty() || fecha.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                // Lógica para calcular conversión (ejemplo simplificado)
                // Aquí deberíamos consultar el tipo de cambio y realizar el cálculo
                double montoConvertido = Double.parseDouble(monto) * 1.2; // ejemplo de tasa de cambio
                TransaccionMoneda transaccion = new TransaccionMoneda(
                        1, // usuarioID (esto debería ser dinámico)
                        1, // monedaOrigenID (esto debería ser dinámico)
                        2, // monedaDestinoID (esto debería ser dinámico)
                        Double.parseDouble(monto),
                        montoConvertido,
                        System.currentTimeMillis()
                );
                transaccionMonedaViewModel.insert(transaccion);
                Toast.makeText(this, "Conversión realizada", Toast.LENGTH_SHORT).show();
            }
        });

        // Observar transacciones
        transaccionMonedaViewModel.getAllTransacciones().observe(this, transacciones -> {
            if (transacciones != null) {
                adapter.setTransacciones(transacciones);
            }
        });
    }
}
