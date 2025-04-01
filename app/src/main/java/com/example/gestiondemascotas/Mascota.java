package com.example.gestiondemascotas;

import java.io.Serializable;

public abstract class Mascota implements Serializable {
    protected String nombre;
    protected int edad;
    protected String raza;
    protected double peso;
    protected String estadoSalud;

    public Mascota(String nombre, int edad, String raza, double peso, String estadoSalud) {
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.peso = peso;
        this.estadoSalud = estadoSalud;
    }

    public abstract String obtenerInformacion();
    public abstract double calcularProbabilidadAdopcion();
}
