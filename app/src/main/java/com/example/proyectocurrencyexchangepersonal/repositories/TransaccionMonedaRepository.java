package com.example.proyectocurrencyexchangepersonal.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.daos.TransaccionMonedaDao;
import com.example.proyectocurrencyexchangepersonal.database.AppDatabase;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransaccionMonedaRepository {
    private TransaccionMonedaDao transaccionDao;
    private LiveData<List<TransaccionMoneda>> allTransacciones;
    private ExecutorService executorService;

    public TransaccionMonedaRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        transaccionDao = db.transaccionMonedaDao();
        allTransacciones = transaccionDao.getAllTransacciones();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<TransaccionMoneda>> getAllTransacciones() {
        return allTransacciones;
    }

    public void insert(TransaccionMoneda transaccion) {
        executorService.execute(() -> transaccionDao.insert(transaccion));
    }

    public void update(TransaccionMoneda transaccion) {
        executorService.execute(() -> transaccionDao.update(transaccion));
    }

    public void delete(TransaccionMoneda transaccion) {
        executorService.execute(() -> transaccionDao.delete(transaccion));
    }

    public LiveData<List<TransaccionMoneda>> getTransaccionesPorUsuario(int usuarioID) {
        return transaccionDao.getTransaccionesPorUsuario(usuarioID);
    }
}
