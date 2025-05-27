package com.example.proyectocurrencyexchangepersonal.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.daos.MonedaDao;
import com.example.proyectocurrencyexchangepersonal.database.AppDatabase;
import com.example.proyectocurrencyexchangepersonal.models.Moneda;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MonedaRepository {
    private MonedaDao monedaDao;
    private LiveData<List<Moneda>> allMonedas;
    private ExecutorService executorService;

    public MonedaRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        monedaDao = db.monedaDao();
        allMonedas = monedaDao.getAllMonedas();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Moneda>> getAllMonedas() {
        return allMonedas;
    }

    public void insert(Moneda moneda) {
        executorService.execute(() -> monedaDao.insert(moneda));
    }

    public void update(Moneda moneda) {
        executorService.execute(() -> monedaDao.update(moneda));
    }

    public void delete(Moneda moneda) {
        executorService.execute(() -> monedaDao.delete(moneda));
    }
}
