package com.example.proyectocurrencyexchangepersonal.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import androidx.room.Transaction;

import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionConMonedasYUsuario;

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

    @Transaction
    @Query("SELECT T.*, U.nombre as usuarioNombre, MO.nombre as monedaOrigenNombre, MO.codigo as monedaOrigenCodigo, MD.nombre as monedaDestinoNombre, MD.codigo as monedaDestinoCodigo FROM transaccionmoneda AS T INNER JOIN usuario AS U ON T.usuarioID = U.usuarioID INNER JOIN moneda AS MO ON T.monedaOrigenID = MO.monedaID INNER JOIN moneda AS MD ON T.monedaDestinoID = MD.monedaID ORDER BY T.fecha DESC")
    LiveData<List<TransaccionConMonedasYUsuario>> getAllTransaccionesConDetalles();
}
