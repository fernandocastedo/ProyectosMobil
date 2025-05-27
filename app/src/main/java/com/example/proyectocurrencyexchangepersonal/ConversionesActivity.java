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
import com.example.proyectocurrencyexchangepersonal.daos.MonedaDao;
import com.example.proyectocurrencyexchangepersonal.database.AppDatabase;
import com.example.proyectocurrencyexchangepersonal.models.Moneda;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConversionesActivity extends AppCompatActivity {

    private Spinner spinnerMonedaOrigen, spinnerMonedaDestino;
    private EditText etMonto;
    private Button btnConvertir;
    private TextView tvResultados;
    private RecyclerView recyclerViewResultados;
    private TransaccionMonedaViewModel transaccionMonedaViewModel;
    private TransaccionMonedaAdapter adapter;
    private List<Moneda> listaMonedas;
    private int currentUserId;
    private MonedaDao monedaDao;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversiones);

        // Get user ID from Intent
        currentUserId = getIntent().getIntExtra("USER_ID", -1);
        if (currentUserId == -1) {
            Toast.makeText(this, "User ID not received", Toast.LENGTH_SHORT).show();
            finish(); // Close activity if user ID is not available
            return;
        }

        // Initialize elements
        spinnerMonedaOrigen = findViewById(R.id.spinnerMonedaOrigen);
        spinnerMonedaDestino = findViewById(R.id.spinnerMonedaDestino);
        etMonto = findViewById(R.id.etMonto);
        // etFecha = findViewById(R.id.etFecha); // Fecha se obtiene automáticamente
        btnConvertir = findViewById(R.id.btnConvertir);
        tvResultados = findViewById(R.id.tvResultados);
        recyclerViewResultados = findViewById(R.id.recyclerViewResultados);

        // Database and DAO
        AppDatabase db = AppDatabase.getInstance(this);
        monedaDao = db.monedaDao();

        // Executor Service for background tasks
        executorService = Executors.newSingleThreadExecutor();

        // Load currencies and populate spinners
        loadCurrencies();

        // Configurar RecyclerView
        recyclerViewResultados.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransaccionMonedaAdapter();
        recyclerViewResultados.setAdapter(adapter);

        // ViewModel
        transaccionMonedaViewModel = new ViewModelProvider(this).get(TransaccionMonedaViewModel.class);

        // Convertir botón
        btnConvertir.setOnClickListener(v -> {
            convertCurrency();
        });

        // Observe transacciones
        transaccionMonedaViewModel.getAllTransaccionesConDetalles().observe(this, transaccionesWithDetails -> {
            if (transaccionesWithDetails != null) {
                adapter.setTransaccionesConDetalles(transaccionesWithDetails);
            }
        });
    }

    private void loadCurrencies() {
        executorService.execute(() -> {
            listaMonedas = monedaDao.getAllMonedas().getValue(); // Simplified, proper way is observing LiveData
            runOnUiThread(() -> {
                if (listaMonedas != null) {
                    List<String> monedaNames = new ArrayList<>();
                    for (Moneda moneda : listaMonedas) {
                        monedaNames.add(moneda.getNombre() + " - " + moneda.getCodigo());
                    }
                    ArrayAdapter<String> adapterMonedaOrigen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monedaNames);
                    adapterMonedaOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMonedaOrigen.setAdapter(adapterMonedaOrigen);

                    ArrayAdapter<String> adapterMonedaDestino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, monedaNames);
                    adapterMonedaDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMonedaDestino.setAdapter(adapterMonedaDestino);
                } else {
                    Toast.makeText(this, "Error loading currencies", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void convertCurrency() {
        String montoString = etMonto.getText().toString();
        if (montoString.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un monto", Toast.LENGTH_SHORT).show();
            return;
        }

        double monto;
        try {
            monto = Double.parseDouble(montoString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Monto inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedOrigenPosition = spinnerMonedaOrigen.getSelectedItemPosition();
        int selectedDestinoPosition = spinnerMonedaDestino.getSelectedItemPosition();

        if (selectedOrigenPosition == Spinner.INVALID_POSITION || selectedDestinoPosition == Spinner.INVALID_POSITION || listaMonedas == null || listaMonedas.size() <= selectedOrigenPosition || listaMonedas.size() <= selectedDestinoPosition) {
            Toast.makeText(this, "Seleccione monedas válidas", Toast.LENGTH_SHORT).show();
            return;
        }

        Moneda monedaOrigen = listaMonedas.get(selectedOrigenPosition);
        Moneda monedaDestino = listaMonedas.get(selectedDestinoPosition);

        if (monedaOrigen.getMonedaID() == monedaDestino.getMonedaID()) {
            Toast.makeText(this, "Seleccione monedas diferentes", Toast.LENGTH_SHORT).show();
            return;
        }

        // Calculate exchange rate and converted amount
        double tasaCambio = monedaDestino.getValorReferencia() / monedaOrigen.getValorReferencia();
        double montoConvertido = monto * tasaCambio;

        // Get current timestamp
        long timestamp = System.currentTimeMillis();

        // Create and insert TransaccionMoneda
        TransaccionMoneda transaccion = new TransaccionMoneda(
                currentUserId,
                monedaOrigen.getMonedaID(),
                monedaDestino.getMonedaID(),
                monto,
                tasaCambio,
                montoConvertido,
                timestamp
        );

        transaccionMonedaViewModel.insert(transaccion);

        Toast.makeText(this, "Conversión realizada", Toast.LENGTH_SHORT).show();

        // Optionally update UI or clear fields
        etMonto.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
