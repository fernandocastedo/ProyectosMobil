package com.example.proyectoexamencrudbd.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectoexamencrudbd.R
import com.example.proyectoexamencrudbd.data.AppDatabase
import com.example.proyectoexamencrudbd.data.relation.CriaturaConHabitat
import com.example.proyectoexamencrudbd.databinding.ActivityListadoBinding
import com.example.proyectoexamencrudbd.ui.adapter.CriaturaAdapter
import com.example.proyectoexamencrudbd.ui.viewmodel.ListadoViewModel
import kotlinx.coroutines.launch

class ListadoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListadoBinding
    private val viewModel: ListadoViewModel by viewModels()
    private lateinit var adapter: CriaturaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupRecyclerView() {
        adapter = CriaturaAdapter(
            onEditClick = { criatura ->
                val intent = Intent(this, FormularioActivity::class.java).apply {
                    putExtra(FormularioActivity.EXTRA_CRIATURA_ID, criatura.criatura.id)
                }
                startActivity(intent)
            },
            onDeleteClick = { criatura ->
                showDeleteConfirmationDialog(criatura)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ListadoActivity)
            adapter = this@ListadoActivity.adapter
        }
    }

    private fun setupClickListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, FormularioActivity::class.java))
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.criaturas.collect { criaturas ->
                    adapter.submitList(criaturas)
                }
            }
        }
    }

    private fun showDeleteConfirmationDialog(criatura: CriaturaConHabitat) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Criatura")
            .setMessage("¿Estás seguro de que deseas eliminar a ${criatura.criatura.nombre}?")
            .setPositiveButton("Eliminar") { _, _ ->
                viewModel.deleteCriatura(criatura.criatura)
            }
            .setNegativeButton("Cancelar", null)
            .show()
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