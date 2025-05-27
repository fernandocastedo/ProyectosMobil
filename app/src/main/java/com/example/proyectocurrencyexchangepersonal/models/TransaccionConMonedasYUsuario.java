package com.example.proyectocurrencyexchangepersonal.models;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TransaccionConMonedasYUsuario {
    @Embedded public TransaccionMoneda transaccion;

    @Relation(parentColumn = "usuarioID", entityColumn = "usuarioID")
    public Usuario usuario;

    @Relation(parentColumn = "monedaOrigenID", entityColumn = "monedaID")
    public Moneda monedaOrigen;

    @Relation(parentColumn = "monedaDestinoID", entityColumn = "monedaID")
    public Moneda monedaDestino;
} 