package com.example.gestiondemascotas;

import java.io.Serializable;

public class Perro extends Mascota implements Serializable {
    private int nivelEnergia;
    private int sociabilidad;

    public Perro(String nombre, int edad, String raza, double peso, String estadoSalud, int nivelEnergia, int sociabilidad) {
        super(nombre, edad, raza, peso, estadoSalud);
        this.nivelEnergia = nivelEnergia;
        this.sociabilidad = sociabilidad;
    }

    @Override
    public String obtenerInformacion() {
        return "Perro - Nombre: " + nombre + 
               "\nEdad: " + edad + " años" +
               "\nRaza: " + raza +
               "\nPeso: " + peso + " kg" +
               "\nEstado de Salud: " + estadoSalud +
               "\nNivel de Energía: " + nivelEnergia +
               "\nSociabilidad: " + sociabilidad;
    }

    @Override
    public double calcularProbabilidadAdopcion() {
        double probabilidad = 0.0;
        
        // Factores que influyen en la probabilidad
        if (edad < 3) probabilidad += 0.3;
        else if (edad < 7) probabilidad += 0.2;
        else probabilidad += 0.1;

        if (estadoSalud.equals("Excelente")) probabilidad += 0.3;
        else if (estadoSalud.equals("Buena")) probabilidad += 0.2;
        else if (estadoSalud.equals("Regular")) probabilidad += 0.1;

        if (nivelEnergia > 7) probabilidad += 0.2;
        else if (nivelEnergia > 4) probabilidad += 0.1;

        if (sociabilidad > 7) probabilidad += 0.2;
        else if (sociabilidad > 4) probabilidad += 0.1;

        return Math.min(probabilidad, 1.0);
    }
}
