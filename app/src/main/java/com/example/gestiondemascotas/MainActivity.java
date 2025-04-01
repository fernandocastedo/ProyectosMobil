// MainActivity.java
package com.example.gestiondemascotas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_AGREGAR_MASCOTA = 1;
    List<Mascota> listaMascotas = new ArrayList<>();
    TextView resultadoTextView;
    TextView estadisticasTextView;
    EditText searchEditText;
    private Button buscarMascotaButton;
    private Spinner criterioFiltroSpinner;
    private Spinner valorFiltroSpinner;
    private EditText valorFiltroEditText;
    private LinearLayout contenedorValorFiltro;
    private Button aplicarFiltroButton;

    private final String[] criteriosFiltro = {
        "Seleccione un criterio",
        "Estado de Salud",
        "Tipo de Mascota",
        "Rango de Edad",
        "Raza"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button crearMascotaButton = findViewById(R.id.crearMascotaButton);
        Button agregarMascotasButton = findViewById(R.id.agregarMascotasButton);
        Button ordenarMascotasButton = findViewById(R.id.ordenarMascotasButton);
        resultadoTextView = findViewById(R.id.resultadoTextView);
        estadisticasTextView = findViewById(R.id.estadisticasTextView);
        searchEditText = findViewById(R.id.searchEditText);
        buscarMascotaButton = findViewById(R.id.buscarMascotaButton);
        actualizarEstadisticas();
        inicializarComponentesFiltrado();

        crearMascotaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarMascotaActivity.class);
            startActivityForResult(intent, REQUEST_AGREGAR_MASCOTA);
        });
        agregarMascotasButton.setOnClickListener(v -> {
            if (listaMascotas.isEmpty()) {
                listaMascotas.add(new Perro("Thor", 5, "Labrador", 35.0, "Buena", 8, 9));
                listaMascotas.add(new Gato("Luna", 3, "Siames", 4.5, "Excelente", 7, 6));
                listaMascotas.add(new Ave("Kiwi", 2, "Canario", 0.15, "Buena", "Canario", true));
                listaMascotas.add(new AveExotica("Quetzal", 4, "Tucán", 0.8, "Regular", "Tucán", false, "tráfico ilegal"));
            }
            mostrarProbabilidadAdopcion(listaMascotas);
            actualizarEstadisticas();
        });
        buscarMascotaButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim().toLowerCase();
            if (TextUtils.isEmpty(query)) {
                resultadoTextView.setText("Por favor, ingrese un nombre o raza para buscar.");
                return;
            }
            buscarMascotas(query);
        });
        ordenarMascotasButton.setOnClickListener(v -> {
            List<Mascota> copia = new ArrayList<>(listaMascotas);
            Collections.sort(copia, Comparator.comparingInt(m -> m.edad));
            mostrarProbabilidadAdopcion(copia);
        });
    }

    private void actualizarEstadisticas() {
        int contadorPerros = 0;
        int contadorGatos = 0;
        int contadorAves = 0;
        int contadorAvesExoticas = 0;

        for (Mascota mascota : listaMascotas) {
            if (mascota instanceof Perro) {
                contadorPerros++;
            } else if (mascota instanceof AveExotica) { // Importante: verificar AveExotica antes que Ave
                contadorAvesExoticas++;
            } else if (mascota instanceof Ave) {
                contadorAves++;
            } else if (mascota instanceof Gato) {
                contadorGatos++;
            }
        }

        StringBuilder stats = new StringBuilder();
        stats.append("Gestión De Mascotas\n\n");
        stats.append("Perros: ").append(contadorPerros).append("\n");
        stats.append("Gatos: ").append(contadorGatos).append("\n");
        stats.append("Aves: ").append(contadorAves).append("\n");
        stats.append("Aves Exóticas: ").append(contadorAvesExoticas).append("\n");
        stats.append("\nTotal: ").append(listaMascotas.size());

        estadisticasTextView.setText(stats.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_AGREGAR_MASCOTA && resultCode == RESULT_OK && data != null) {
            Mascota nuevaMascota = (Mascota) data.getSerializableExtra("mascota");
            if (nuevaMascota != null) {
                listaMascotas.add(nuevaMascota);
                mostrarProbabilidadAdopcion(listaMascotas);
                actualizarEstadisticas();
            }
        }
    }

    private void mostrarProbabilidadAdopcion(List<Mascota> lista) {
        StringBuilder sb = new StringBuilder();
        for (Mascota mascota : lista) {
            sb.append(mascota.obtenerInformacion()).append("\n")
                    .append("Probabilidad de adopción: ").append(mascota.calcularProbabilidadAdopcion()).append("\n\n");
        }
        resultadoTextView.setText(sb.toString());
    }

    private void buscarMascotas(String query) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("Resultados de búsqueda para: ").append(query).append("\n\n");
        
        List<Mascota> mascotasEncontradas = new ArrayList<>();
        for (Mascota mascota : listaMascotas) {
            if (mascota.nombre.toLowerCase().contains(query) || 
                mascota.raza.toLowerCase().contains(query)) {
                mascotasEncontradas.add(mascota);
            }
        }

        if (!mascotasEncontradas.isEmpty()) {
            Collections.sort(mascotasEncontradas, Comparator.comparingInt(m -> m.edad));
            for (Mascota mascota : mascotasEncontradas) {
                resultado.append(mascota.obtenerInformacion())
                        .append("\nProbabilidad de adopción: ")
                        .append(mascota.calcularProbabilidadAdopcion())
                        .append("\n\n");
            }
        } else {
            resultado.append("No se encontraron mascotas que coincidan con la búsqueda.");
        }

        resultadoTextView.setText(resultado.toString());
    }

    private void inicializarComponentesFiltrado() {
        criterioFiltroSpinner = findViewById(R.id.criterioFiltroSpinner);
        valorFiltroSpinner = findViewById(R.id.valorFiltroSpinner);
        valorFiltroEditText = findViewById(R.id.valorFiltroEditText);
        contenedorValorFiltro = findViewById(R.id.contenedorValorFiltro);
        aplicarFiltroButton = findViewById(R.id.aplicarFiltroButton);
        ArrayAdapter<String> criteriosAdapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, criteriosFiltro);
        criteriosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        criterioFiltroSpinner.setAdapter(criteriosAdapter);

        criterioFiltroSpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                configurarInputFiltro(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
        aplicarFiltroButton.setOnClickListener(v -> aplicarFiltro());
    }

    private void configurarInputFiltro(int position) {
        valorFiltroEditText.setVisibility(View.GONE);
        valorFiltroSpinner.setVisibility(View.GONE);

        switch (criteriosFiltro[position]) {
            case "Estado de Salud":
                valorFiltroSpinner.setVisibility(View.VISIBLE);
                String[] estadosSalud = {"Excelente", "Buena", "Regular", "Mala"};
                setSpinnerOptions(estadosSalud);
                break;
            case "Tipo de Mascota":
                valorFiltroSpinner.setVisibility(View.VISIBLE);
                String[] tiposMascota = {"Perro", "Gato", "Ave", "Ave Exótica"};
                setSpinnerOptions(tiposMascota);
                break;
            case "Rango de Edad":
                valorFiltroEditText.setVisibility(View.VISIBLE);
                valorFiltroEditText.setHint("Ingrese edad máxima");
                valorFiltroEditText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                break;
            case "Raza":
                valorFiltroEditText.setVisibility(View.VISIBLE);
                valorFiltroEditText.setHint("Ingrese la raza");
                valorFiltroEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT);
                break;
        }
    }

    private void setSpinnerOptions(String[] opciones) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        valorFiltroSpinner.setAdapter(adapter);
    }

    private void aplicarFiltro() {
        String criterio = criterioFiltroSpinner.getSelectedItem().toString();
        if (criterio.equals("Seleccione un criterio")) {
            mostrarMensaje("Por favor, seleccione un criterio de filtrado");
            return;
        }

        List<Mascota> mascotasFiltradas = new ArrayList<>();
        
        switch (criterio) {
            case "Estado de Salud":
                String estadoSalud = valorFiltroSpinner.getSelectedItem().toString();
                for (Mascota m : listaMascotas) {
                    if (m.estadoSalud.equals(estadoSalud)) {
                        mascotasFiltradas.add(m);
                    }
                }
                break;

            case "Tipo de Mascota":
                String tipo = valorFiltroSpinner.getSelectedItem().toString();
                for (Mascota m : listaMascotas) {
                    if ((tipo.equals("Perro") && m instanceof Perro) ||
                        (tipo.equals("Gato") && m instanceof Gato) ||
                        (tipo.equals("Ave Exótica") && m instanceof AveExotica) ||
                        (tipo.equals("Ave") && m instanceof Ave && !(m instanceof AveExotica))) {
                        mascotasFiltradas.add(m);
                    }
                }
                break;

            case "Rango de Edad":
                try {
                    int edadMaxima = Integer.parseInt(valorFiltroEditText.getText().toString());
                    for (Mascota m : listaMascotas) {
                        if (m.edad <= edadMaxima) {
                            mascotasFiltradas.add(m);
                        }
                    }
                } catch (NumberFormatException e) {
                    mostrarMensaje("Por favor, ingrese una edad válida");
                    return;
                }
                break;

            case "Raza":
                String raza = valorFiltroEditText.getText().toString().toLowerCase();
                if (raza.isEmpty()) {
                    mostrarMensaje("Por favor, ingrese una raza");
                    return;
                }
                for (Mascota m : listaMascotas) {
                    if (m.raza.toLowerCase().contains(raza)) {
                        mascotasFiltradas.add(m);
                    }
                }
                break;
        }

        mostrarResultadosFiltrados(mascotasFiltradas, criterio);
    }

    private void mostrarResultadosFiltrados(List<Mascota> mascotasFiltradas, String criterio) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("Resultados del filtro por ").append(criterio).append(":\n\n");

        if (mascotasFiltradas.isEmpty()) {
            resultado.append("No se encontraron mascotas que cumplan con el criterio.");
        } else {
            for (Mascota m : mascotasFiltradas) {
                resultado.append(m.obtenerInformacion())
                        .append("\nProbabilidad de adopción: ")
                        .append(m.calcularProbabilidadAdopcion())
                        .append("\n\n");
            }
        }

        resultadoTextView.setText(resultado.toString());
    }

    private void mostrarMensaje(String mensaje) {
        resultadoTextView.setText(mensaje);
    }
}
