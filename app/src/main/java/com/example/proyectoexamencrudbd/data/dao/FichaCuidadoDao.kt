package com.example.proyectoexamencrudbd.data.dao

import androidx.room.*
import com.example.proyectoexamencrudbd.data.entity.FichaCuidado
import com.example.proyectoexamencrudbd.data.relation.CriaturaConFicha
import kotlinx.coroutines.flow.Flow

@Dao
interface FichaCuidadoDao {
    @Transaction
    @Query("SELECT * FROM criaturas_magicas")
    fun getAllCriaturasConFicha(): Flow<List<CriaturaConFicha>>

    @Query("SELECT * FROM fichas_cuidado")
    fun getAllFichas(): Flow<List<FichaCuidado>>

    @Query("SELECT * FROM fichas_cuidado WHERE criaturaId = :criaturaId")
    suspend fun getFichaByCriaturaId(criaturaId: Int): FichaCuidado?

    @Transaction
    @Query("SELECT * FROM criaturas_magicas WHERE id = :id")
    suspend fun getCriaturaConFichaById(id: Int): CriaturaConFicha?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFicha(ficha: FichaCuidado): Long

    @Update
    suspend fun updateFicha(ficha: FichaCuidado)

    @Delete
    suspend fun deleteFicha(ficha: FichaCuidado)

    @Query("DELETE FROM fichas_cuidado WHERE criaturaId = :criaturaId")
    suspend fun deleteFichaByCriaturaId(criaturaId: Int)
} 