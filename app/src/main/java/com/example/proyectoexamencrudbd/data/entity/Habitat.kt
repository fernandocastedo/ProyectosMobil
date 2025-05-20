package com.example.proyectoexamencrudbd.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "habitats")
data class Habitat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val region: String,
    val temperaturaPromedio: String,
    val tipoEntorno: String
) 