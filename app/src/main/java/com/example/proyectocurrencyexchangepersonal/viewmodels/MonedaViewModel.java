package com.example.proyectocurrencyexchangepersonal.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.models.Moneda;
import com.example.proyectocurrencyexchangepersonal.repositories.MonedaRepository;

import java.util.List;

public class MonedaViewModel extends AndroidViewModel {

    private MonedaRepository repository;
    private LiveData<List<Moneda>> allMonedas;

    public MonedaViewModel(@NonNull Application application) {
        super(application);
        repository = new MonedaRepository(application);
        allMonedas = repository.getAllMonedas();
    }

    public LiveData<List<Moneda>> getAllMonedas() {
        return allMonedas;
    }

    public void insert(Moneda moneda) {
        repository.insert(moneda);
    }

    public void update(Moneda moneda) {
        repository.update(moneda);
    }

    public void delete(Moneda moneda) {
        repository.delete(moneda);
    }
}
