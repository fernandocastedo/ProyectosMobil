package com.example.proyectoexamencrudbd.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.entity.FichaCuidado

data class CriaturaConFicha(
    @Embedded val criatura: CriaturaMagica,
    @Relation(
        parentColumn = "id",
        entityColumn = "criaturaId"
    )
    val ficha: FichaCuidado
) 