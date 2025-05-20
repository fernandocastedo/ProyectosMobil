package com.example.proyectoexamencrudbd.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoexamencrudbd.data.AppDatabase
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.entity.FichaCuidado
import com.example.proyectoexamencrudbd.data.entity.Habitat
import com.example.proyectoexamencrudbd.data.relation.CriaturaConFicha
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FormularioViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val habitatDao = database.habitatDao()
    private val criaturaDao = database.criaturaMagicaDao()
    private val fichaDao = database.fichaCuidadoDao()

    private val _criaturaConFicha = MutableStateFlow<CriaturaConFicha?>(null)
    val criaturaConFicha: StateFlow<CriaturaConFicha?> = _criaturaConFicha

    private val _saveResult = MutableStateFlow(false)
    val saveResult: StateFlow<Boolean> = _saveResult

    fun loadCriatura(criaturaId: Int) {
        viewModelScope.launch {
            criaturaDao.getCriaturaConFichaById(criaturaId)?.let { criaturaConFicha ->
                _criaturaConFicha.value = criaturaConFicha
            }
        }
    }

    fun saveCriatura(habitat: Habitat, criatura: CriaturaMagica, ficha: FichaCuidado) {
        viewModelScope.launch {
            try {
                // Primero guardamos el hábitat
                val habitatId = if (habitat.id == 0) {
                    habitatDao.insertHabitat(habitat).toInt()
                } else {
                    habitatDao.updateHabitat(habitat)
                    habitat.id
                }

                // Luego guardamos la criatura
                val criaturaConHabitatId = criatura.copy(habitatId = habitatId)
                val criaturaId = if (criatura.id == 0) {
                    criaturaDao.insertCriatura(criaturaConHabitatId).toInt()
                } else {
                    criaturaDao.updateCriatura(criaturaConHabitatId)
                    criatura.id
                }

                // Finalmente guardamos la ficha
                val fichaConCriaturaId = ficha.copy(criaturaId = criaturaId)
                if (ficha.id == 0) {
                    fichaDao.insertFicha(fichaConCriaturaId)
                } else {
                    fichaDao.updateFicha(fichaConCriaturaId)
                }

                _saveResult.value = true
            } catch (e: Exception) {
                _saveResult.value = false
            }
        }
    }
} 