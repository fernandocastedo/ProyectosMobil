package com.example.proyectocurrencyexchangepersonal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyectocurrencyexchangepersonal.daos.UsuarioDao;
import com.example.proyectocurrencyexchangepersonal.daos.MonedaDao;
import com.example.proyectocurrencyexchangepersonal.daos.TransaccionMonedaDao;
import com.example.proyectocurrencyexchangepersonal.models.Usuario;
import com.example.proyectocurrencyexchangepersonal.models.Moneda;
import com.example.proyectocurrencyexchangepersonal.models.TransaccionMoneda;

@Database(entities = {Usuario.class, Moneda.class, TransaccionMoneda.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UsuarioDao usuarioDao();
    public abstract MonedaDao monedaDao();
    public abstract TransaccionMonedaDao transaccionMonedaDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "currency_exchange_db")
                    .fallbackToDestructiveMigration() // Opcional, destruye y crea si cambia versión
                    .build();
        }
        return INSTANCE;
    }
}
