package com.example.proyectocurrencyexchangepersonal.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.daos.TransaccionMonedaDao;
import com.example.proyectocurrencyexchangepersonal.database.AppDatabase;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionConMonedasYUsuario;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransaccionMonedaRepository {
    private TransaccionMonedaDao transaccionDao;
    private LiveData<List<TransaccionMoneda>> allTransacciones;
    private LiveData<List<TransaccionConMonedasYUsuario>> allTransaccionesConDetalles;
    private ExecutorService executorService;

    public TransaccionMonedaRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        transaccionDao = db.transaccionMonedaDao();
        allTransacciones = transaccionDao.getAllTransacciones();
        allTransaccionesConDetalles = transaccionDao.getAllTransaccionesConDetalles();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<TransaccionMoneda>> getAllTransacciones() {
        return allTransacciones;
    }

    public LiveData<List<TransaccionConMonedasYUsuario>> getAllTransaccionesConDetalles() {
        return allTransaccionesConDetalles;
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
