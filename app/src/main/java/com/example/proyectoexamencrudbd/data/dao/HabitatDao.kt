package com.example.proyectoexamencrudbd.data.dao

import androidx.room.*
import com.example.proyectoexamencrudbd.data.entity.Habitat
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitatDao {
    @Query("SELECT * FROM habitats")
    fun getAllHabitats(): Flow<List<Habitat>>

    @Query("SELECT * FROM habitats WHERE id = :id")
    suspend fun getHabitatById(id: Int): Habitat?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitat(habitat: Habitat): Long

    @Update
    suspend fun updateHabitat(habitat: Habitat)

    @Delete
    suspend fun deleteHabitat(habitat: Habitat)

    @Query("DELETE FROM habitats WHERE id = :id")
    suspend fun deleteHabitatById(id: Int)
} 