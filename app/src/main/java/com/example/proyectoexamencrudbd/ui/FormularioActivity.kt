package com.example.proyectoexamencrudbd.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.proyectoexamencrudbd.R
import com.example.proyectoexamencrudbd.data.entity.CriaturaMagica
import com.example.proyectoexamencrudbd.data.entity.FichaCuidado
import com.example.proyectoexamencrudbd.data.entity.Habitat
import com.example.proyectoexamencrudbd.data.relation.CriaturaConFicha
import com.example.proyectoexamencrudbd.databinding.ActivityFormularioBinding
import com.example.proyectoexamencrudbd.ui.viewmodel.FormularioViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class FormularioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormularioBinding
    private val viewModel: FormularioViewModel by viewModels()
    private var criaturaId: Int? = null

    companion object {
        const val EXTRA_CRIATURA_ID = "extra_criatura_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormularioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        criaturaId = intent.getIntExtra(EXTRA_CRIATURA_ID, -1).takeIf { it != -1 }

        setupToolbar()
        setupPeligrosidadDropdown()
        setupClickListeners()
        observeViewModel()

        if (criaturaId != null) {
            viewModel.loadCriatura(criaturaId!!)
            binding.toolbar.title = "Editar Criatura"
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupPeligrosidadDropdown() {
        val peligrosidades = arrayOf("Baja", "Media", "Alta", "Extrema")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, peligrosidades)
        binding.peligrosidadAutoComplete.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        binding.fabSave.setOnClickListener {
            if (validateForm()) {
                saveCriatura()
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.criaturaConFicha.collect { criaturaConFicha ->
                    criaturaConFicha?.let { populateForm(it) }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.saveResult.collect { success ->
                    if (success) {
                        Snackbar.make(binding.root, "Criatura guardada exitosamente", Snackbar.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
    }

    private fun populateForm(criaturaConFicha: CriaturaConFicha) {
        with(binding) {
            nombreEditText.setText(criaturaConFicha.criatura.nombre)
            especieEditText.setText(criaturaConFicha.criatura.especie)
            nivelMagiaEditText.setText(criaturaConFicha.criatura.nivelMagia.toString())
            peligrosidadAutoComplete.setText(criaturaConFicha.criatura.peligrosidad, false)

            habitatNombreEditText.setText(criaturaConFicha.criatura.habitat.nombre)
            habitatRegionEditText.setText(criaturaConFicha.criatura.habitat.region)
            habitatTemperaturaEditText.setText(criaturaConFicha.criatura.habitat.temperaturaPromedio)
            habitatTipoEditText.setText(criaturaConFicha.criatura.habitat.tipoEntorno)

            criaturaConFicha.ficha?.let { ficha ->
                alimentoEditText.setText(ficha.alimentoFavorito)
                frecuenciaEditText.setText(ficha.frecuenciaAlimentacion)
                supervisionSwitch.isChecked = ficha.requiereSupervision
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true
        with(binding) {
            if (nombreEditText.text.isNullOrBlank()) {
                nombreLayout.error = "El nombre es requerido"
                isValid = false
            } else {
                nombreLayout.error = null
            }

            if (especieEditText.text.isNullOrBlank()) {
                especieLayout.error = "La especie es requerida"
                isValid = false
            } else {
                especieLayout.error = null
            }

            if (nivelMagiaEditText.text.isNullOrBlank()) {
                nivelMagiaLayout.error = "El nivel de magia es requerido"
                isValid = false
            } else {
                nivelMagiaLayout.error = null
            }

            if (peligrosidadAutoComplete.text.isNullOrBlank()) {
                peligrosidadLayout.error = "La peligrosidad es requerida"
                isValid = false
            } else {
                peligrosidadLayout.error = null
            }

            if (habitatNombreEditText.text.isNullOrBlank()) {
                habitatNombreLayout.error = "El nombre del hábitat es requerido"
                isValid = false
            } else {
                habitatNombreLayout.error = null
            }

            if (habitatRegionEditText.text.isNullOrBlank()) {
                habitatRegionLayout.error = "La región es requerida"
                isValid = false
            } else {
                habitatRegionLayout.error = null
            }

            if (habitatTemperaturaEditText.text.isNullOrBlank()) {
                habitatTemperaturaLayout.error = "La temperatura es requerida"
                isValid = false
            } else {
                habitatTemperaturaLayout.error = null
            }

            if (habitatTipoEditText.text.isNullOrBlank()) {
                habitatTipoLayout.error = "El tipo de entorno es requerido"
                isValid = false
            } else {
                habitatTipoLayout.error = null
            }

            if (alimentoEditText.text.isNullOrBlank()) {
                alimentoLayout.error = "El alimento favorito es requerido"
                isValid = false
            } else {
                alimentoLayout.error = null
            }

            if (frecuenciaEditText.text.isNullOrBlank()) {
                frecuenciaLayout.error = "La frecuencia de alimentación es requerida"
                isValid = false
            } else {
                frecuenciaLayout.error = null
            }
        }
        return isValid
    }

    private fun saveCriatura() {
        with(binding) {
            val habitat = Habitat(
                id = viewModel.criaturaConFicha.value?.criatura?.habitat?.id ?: 0,
                nombre = habitatNombreEditText.text.toString(),
                region = habitatRegionEditText.text.toString(),
                temperaturaPromedio = habitatTemperaturaEditText.text.toString(),
                tipoEntorno = habitatTipoEditText.text.toString()
            )

            val criatura = CriaturaMagica(
                id = criaturaId ?: 0,
                nombre = nombreEditText.text.toString(),
                especie = especieEditText.text.toString(),
                nivelMagia = nivelMagiaEditText.text.toString().toInt(),
                peligrosidad = peligrosidadAutoComplete.text.toString(),
                habitatId = habitat.id
            )

            val ficha = FichaCuidado(
                id = viewModel.criaturaConFicha.value?.ficha?.id ?: 0,
                criaturaId = criatura.id,
                alimentoFavorito = alimentoEditText.text.toString(),
                frecuenciaAlimentacion = frecuenciaEditText.text.toString(),
                requiereSupervision = supervisionSwitch.isChecked
            )

            viewModel.saveCriatura(habitat, criatura, ficha)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
} 