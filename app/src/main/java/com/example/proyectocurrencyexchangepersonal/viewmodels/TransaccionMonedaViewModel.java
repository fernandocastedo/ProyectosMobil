package com.example.proyectocurrencyexchangepersonal.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;
import com.example.proyectocurrencyexchangepersonal.repositories.TransaccionMonedaRepository;

import java.util.List;

public class TransaccionMonedaViewModel extends AndroidViewModel {

    private TransaccionMonedaRepository repository;
    private LiveData<List<TransaccionMoneda>> allTransacciones;

    public TransaccionMonedaViewModel(@NonNull Application application) {
        super(application);
        repository = new TransaccionMonedaRepository(application);
        allTransacciones = repository.getAllTransacciones();
    }

    public LiveData<List<TransaccionMoneda>> getAllTransacciones() {
        return allTransacciones;
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
    public void inicializarUsuariosSiVacio() {
        repository.inicializarUsuariosSiVacio();
    }
}
