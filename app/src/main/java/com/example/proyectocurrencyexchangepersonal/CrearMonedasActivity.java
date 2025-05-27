package com.example.proyectocurrencyexchangepersonal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectocurrencyexchangepersonal.Adapters.MonedaAdapter;
import com.example.proyectocurrencyexchangepersonal.models.Moneda;
import com.example.proyectocurrencyexchangepersonal.viewmodels.MonedaViewModel;

import java.util.List;

public class CrearMonedasActivity extends AppCompatActivity {

    private EditText etNombreMoneda, etCodigoMoneda, etPaisOrigen, etValorReferencia;
    private Button btnCrearMoneda;
    private TextView tvResultadoCreacion;
    private RecyclerView recyclerViewMonedas;
    private MonedaViewModel monedaViewModel;
    private MonedaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_monedas);

        etNombreMoneda = findViewById(R.id.etNombreMoneda);
        etCodigoMoneda = findViewById(R.id.etCodigoMoneda);
        etPaisOrigen = findViewById(R.id.etPaisOrigen);
        etValorReferencia = findViewById(R.id.etValorReferencia);
        btnCrearMoneda = findViewById(R.id.btnCrearMoneda);
        tvResultadoCreacion = findViewById(R.id.tvResultadoCreacion);
        recyclerViewMonedas = findViewById(R.id.recyclerViewMonedas);

        // Configurar RecyclerView
        recyclerViewMonedas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MonedaAdapter();
        recyclerViewMonedas.setAdapter(adapter);

        // ViewModel
        monedaViewModel = new ViewModelProvider(this).get(MonedaViewModel.class);

        // Observar las monedas
        monedaViewModel.getAllMonedas().observe(this, monedas -> {
            if (monedas != null) {
                adapter.setMonedas(monedas);
            }
        });

        // Crear moneda
        btnCrearMoneda.setOnClickListener(v -> {
            String nombre = etNombreMoneda.getText().toString();
            String codigo = etCodigoMoneda.getText().toString();
            String paisOrigen = etPaisOrigen.getText().toString();
            String valorReferenciaStr = etValorReferencia.getText().toString();

            if (nombre.isEmpty() || codigo.isEmpty() || paisOrigen.isEmpty() || valorReferenciaStr.isEmpty()) {
                Toast.makeText(CrearMonedasActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                double valorReferencia = Double.parseDouble(valorReferenciaStr);
                Moneda nuevaMoneda = new Moneda(nombre, codigo, paisOrigen, valorReferencia);
                monedaViewModel.insert(nuevaMoneda);
                Toast.makeText(CrearMonedasActivity.this, "Moneda creada exitosamente", Toast.LENGTH_SHORT).show();
                tvResultadoCreacion.setText("Moneda " + nombre + " agregada");
            }
        });
    }
}
