package com.example.proyectocurrencyexchangepersonal.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;
import com.example.proyectocurrencyexchangepersonal.repositories.TransaccionMonedaRepository;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionConMonedasYUsuario;

import java.util.List;

public class TransaccionMonedaViewModel extends AndroidViewModel {

    private TransaccionMonedaRepository repository;
    private LiveData<List<TransaccionMoneda>> allTransacciones;
    private LiveData<List<TransaccionConMonedasYUsuario>> allTransaccionesConDetalles;

    public TransaccionMonedaViewModel(@NonNull Application application) {
        super(application);
        repository = new TransaccionMonedaRepository(application);
        allTransacciones = repository.getAllTransacciones();
        allTransaccionesConDetalles = repository.getAllTransaccionesConDetalles();
    }

    public LiveData<List<TransaccionMoneda>> getAllTransacciones() {
        return allTransacciones;
    }

    public LiveData<List<TransaccionConMonedasYUsuario>> getAllTransaccionesConDetalles() {
        return allTransaccionesConDetalles;
    }

    public void insert(TransaccionMoneda transaccion) {
        repository.insert(transaccion);
    }

    public void update(TransaccionMoneda transaccion) {
        repository.update(transaccion);
    }

    public void delete(TransaccionMoneda transaccion) {
        repository.delete(transaccion);
    }



    public LiveData<List<TransaccionMoneda>> getTransaccionesPorUsuario(int usuarioID) {
        return repository.getTransaccionesPorUsuario(usuarioID);
    }
}
