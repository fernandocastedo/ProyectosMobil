package com.example.proyectocurrencyexchangepersonal.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.proyectocurrencyexchangepersonal.daos.UsuarioDao;
import com.example.proyectocurrencyexchangepersonal.database.AppDatabase;
import com.example.proyectocurrencyexchangepersonal.models.Usuario;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsuarioRepository {
    private UsuarioDao usuarioDao;
    private LiveData<List<Usuario>> allUsuarios;
    private ExecutorService executorService;

    public UsuarioRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        usuarioDao = db.usuarioDao();
        allUsuarios = usuarioDao.getAllUsuarios();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Usuario>> getAllUsuarios() {
        return allUsuarios;
    }
    public void inicializarUsuariosSiVacio() {
        executorService.execute(() -> {
            List<Usuario> usuarios = usuarioDao.getAllUsuariosSync();
            if (usuarios == null || usuarios.isEmpty()) {
                usuarioDao.insert(new Usuario("fernando", "fernando@mail.com", "123", "Bolivia", "admin"));
                usuarioDao.insert(new Usuario("pedro", "pedro@mail.com", "abc", "Bolivia", "usuario"));
                usuarioDao.insert(new Usuario("maria", "maria@mail.com", "1234", "Argentina", "usuario"));
            }
        });
    }

    public void insert(Usuario usuario) {
        executorService.execute(() -> usuarioDao.insert(usuario));
    }

    public void update(Usuario usuario) {
        executorService.execute(() -> usuarioDao.update(usuario));
    }

    public void delete(Usuario usuario) {
        executorService.execute(() -> usuarioDao.delete(usuario));
    }

    public Usuario getUsuarioByNombre(String nombre) {
        // Esta función no es LiveData, por lo que debe ejecutarse fuera del hilo principal.
        // Aquí deberías manejarla con Future o hacer llamada desde ViewModel con AsyncTask o corutinas.
        // Para simplificar, no implementamos aquí.
        return null;
    }
}
