package com.example.proyectoexamencrudbd

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectoexamencrudbd.databinding.ActivityMainBinding
import com.example.proyectoexamencrudbd.ui.FormularioActivity
import com.example.proyectoexamencrudbd.ui.ListadoActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnListado.setOnClickListener {
            startActivity(Intent(this, ListadoActivity::class.java))
        }

        binding.btnFormulario.setOnClickListener {
            startActivity(Intent(this, FormularioActivity::class.java))
        }
    }
} 