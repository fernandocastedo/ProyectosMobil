package com.example.proyectoexamencrudbd.data.dao

import androidx.room.*
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.relation.CriaturaConHabitat
import kotlinx.coroutines.flow.Flow

@Dao
interface CriaturaMagicaDao {
    @Transaction
    @Query("SELECT * FROM criaturas_magicas")
    fun getAllCriaturasConHabitat(): Flow<List<CriaturaConHabitat>>

    @Query("SELECT * FROM criaturas_magicas")
    fun getAllCriaturas(): Flow<List<CriaturaMagica>>

    @Query("SELECT * FROM criaturas_magicas WHERE id = :id")
    suspend fun getCriaturaById(id: Int): CriaturaMagica?

    @Transaction
    @Query("SELECT * FROM criaturas_magicas WHERE id = :id")
    suspend fun getCriaturaConHabitatById(id: Int): CriaturaConHabitat?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCriatura(criatura: CriaturaMagica): Long

    @Update
    suspend fun updateCriatura(criatura: CriaturaMagica)

    @Delete
    suspend fun deleteCriatura(criatura: CriaturaMagica)

    @Query("DELETE FROM criaturas_magicas WHERE id = :id")
    suspend fun deleteCriaturaById(id: Int)

    @Query("SELECT * FROM criaturas_magicas WHERE habitatId = :habitatId")
    fun getCriaturasByHabitatId(habitatId: Int): Flow<List<CriaturaMagica>>
} 