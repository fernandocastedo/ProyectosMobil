package com.example.proyectocurrencyexchangepersonal.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "moneda")
public class Moneda {

    @PrimaryKey(autoGenerate = true)
    private int monedaID;

    private String nombre;
    private String codigo;
    private String paisOrigen;
    private double valorReferencia;

    public Moneda(String nombre, String codigo, String paisOrigen, double valorReferencia) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.paisOrigen = paisOrigen;
        this.valorReferencia = valorReferencia;
    }

    // Getters y setters
    public int getMonedaID() { return monedaID; }
    public void setMonedaID(int monedaID) { this.monedaID = monedaID; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }
    public double getValorReferencia() { return valorReferencia; }
    public void setValorReferencia(double valorReferencia) { this.valorReferencia = valorReferencia; }
}
