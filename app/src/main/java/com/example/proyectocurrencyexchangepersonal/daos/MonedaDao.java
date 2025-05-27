package com.example.proyectocurrencyexchangepersonal.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.proyectocurrencyexchangepersonal.models.Moneda;

import java.util.List;

@Dao
public interface MonedaDao {

    @Insert
    void insert(Moneda moneda);

    @Update
    void update(Moneda moneda);

    @Delete
    void delete(Moneda moneda);

    @Query("SELECT * FROM moneda")
    LiveData<List<Moneda>> getAllMonedas();

    @Query("SELECT * FROM moneda WHERE monedaID = :id LIMIT 1")
    LiveData<Moneda> getMonedaById(int id);

    @Query("SELECT COUNT(*) FROM moneda")
    int countMonedas();
}
