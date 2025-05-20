package com.example.proyectoexamencrudbd.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.entity.Habitat

data class CriaturaConHabitat(
    @Embedded val criatura: CriaturaMagica,
    @Relation(
        parentColumn = "habitatId",
        entityColumn = "id"
    )
    val habitat: Habitat
) 