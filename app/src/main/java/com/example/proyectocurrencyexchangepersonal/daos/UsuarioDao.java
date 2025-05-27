package com.example.proyectocurrencyexchangepersonal.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.example.proyectocurrencyexchangepersonal.models.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM usuario ORDER BY nombre ASC")
    LiveData<List<Usuario>> getAllUsuarios();

    @Query("SELECT * FROM usuario WHERE usuarioID = :id LIMIT 1")
    LiveData<Usuario> getUsuarioById(int id);

    @Query("SELECT * FROM usuario WHERE nombre = :nombre LIMIT 1")
    Usuario getUsuarioByNombre(String nombre);  // No LiveData para uso interno (ej: login)
    @Query("SELECT * FROM usuario ORDER BY nombre ASC")
    List<Usuario> getAllUsuariosSync();

}
