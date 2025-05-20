package com.example.proyectoexamencrudbd.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "fichas_cuidado",
    foreignKeys = [
        ForeignKey(
            entity = CriaturaMagica::class,
            parentColumns = ["id"],
            childColumns = ["criaturaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FichaCuidado(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val criaturaId: Int,
    val alimentoFavorito: String,
    val frecuenciaAlimentacion: String,
    val requiereSupervision: Boolean
) 