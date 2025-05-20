package com.example.proyectoexamencrudbd.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectoexamencrudbd.data.dao.CriaturaMagicaDao
import com.example.proyectoexamencrudbd.data.dao.FichaCuidadoDao
import com.example.proyectoexamencrudbd.data.dao.HabitatDao
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.entity.FichaCuidado
import com.example.proyectoexamencrudbd.data.entity.Habitat

@Database(
    entities = [
        Habitat::class,
        CriaturaMagica::class,
        FichaCuidado::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun habitatDao(): HabitatDao
    abstract fun criaturaMagicaDao(): CriaturaMagicaDao
    abstract fun fichaCuidadoDao(): FichaCuidadoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "criaturas_magicas_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 