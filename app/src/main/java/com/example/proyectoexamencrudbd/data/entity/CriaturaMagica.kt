package com.example.proyectoexamencrudbd.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "criaturas_magicas",
    foreignKeys = [
        ForeignKey(
            entity = Habitat::class,
            parentColumns = ["id"],
            childColumns = ["habitatId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CriaturaMagica(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val especie: String,
    val nivelMagia: Int,
    val peligrosidad: String,
    val habitatId: Int
) {

}