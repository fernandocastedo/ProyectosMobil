package com.example.proyectocurrencyexchangepersonal.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.models.Usuario;
import com.example.proyectocurrencyexchangepersonal.repositories.UsuarioRepository;

import java.util.List;

public class UsuarioViewModel extends AndroidViewModel {

    private UsuarioRepository repository;
    private LiveData<List<Usuario>> allUsuarios;

    public UsuarioViewModel(@NonNull Application application) {
        super(application);
        repository = new UsuarioRepository(application);
        allUsuarios = repository.getAllUsuarios();
    }

    public LiveData<List<Usuario>> getAllUsuarios() {
        return allUsuarios;
    }

    public void insert(Usuario usuario) {
        repository.insert(usuario);
    }

    public void update(Usuario usuario) {
        repository.update(usuario);
    }

    public void delete(Usuario usuario) {
        repository.delete(usuario);
    }
    public void inicializarUsuariosSiVacio() {
        repository.inicializarUsuariosSiVacio();
    }

}
