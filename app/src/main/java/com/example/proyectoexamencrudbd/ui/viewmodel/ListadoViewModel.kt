package com.example.proyectoexamencrudbd.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoexamencrudbd.data.AppDatabase
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.relation.CriaturaConHabitat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ListadoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val criaturaDao = database.criaturaMagicaDao()

    val criaturas: Flow<List<CriaturaConHabitat>> = criaturaDao.getAllCriaturasConHabitat()

    fun deleteCriatura(criatura: CriaturaMagica) {
        viewModelScope.launch {
            criaturaDao.deleteCriatura(criatura)
        }
    }
} 