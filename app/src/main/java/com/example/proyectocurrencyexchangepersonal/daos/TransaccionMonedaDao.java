package com.example.proyectocurrencyexchangepersonal.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;

import java.util.List;

@Dao
public interface TransaccionMonedaDao {

    @Insert
    void insert(TransaccionMoneda transaccion);

    @Update
    void update(TransaccionMoneda transaccion);

    @Delete
    void delete(TransaccionMoneda transaccion);

    @Query("SELECT * FROM transaccionmoneda ORDER BY fecha DESC")
    LiveData<List<TransaccionMoneda>> getAllTransacciones();

    @Query("SELECT * FROM transaccionmoneda WHERE transaccionID = :id LIMIT 1")
    LiveData<TransaccionMoneda> getTransaccionById(int id);

    @Query("SELECT * FROM transaccionmoneda WHERE usuarioID = :usuarioID ORDER BY fecha DESC")
    LiveData<List<TransaccionMoneda>> getTransaccionesPorUsuario(int usuarioID);

    @Query("SELECT COUNT(*) FROM transaccionmoneda")
    int countTransaccionesMoneda();
}
