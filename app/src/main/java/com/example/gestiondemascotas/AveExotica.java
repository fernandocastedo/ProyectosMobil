package com.example.gestiondemascotas;

import java.io.Serializable;

public class AveExotica extends Ave implements Serializable {
    private String estadoConservacion;

    public AveExotica(String nombre, int edad, String raza, double peso, String estadoSalud, 
                     String tipoAve, boolean puedeVolar, String estadoConservacion) {
        super(nombre, edad, raza, peso, estadoSalud, tipoAve, puedeVolar);
        this.estadoConservacion = estadoConservacion;
    }

    @Override
    public String obtenerInformacion() {
        return "Ave Exótica - Nombre: " + nombre + 
               "\nEdad: " + edad + " años" +
               "\nRaza: " + raza +
               "\nPeso: " + peso + " kg" +
               "\nEstado de Salud: " + estadoSalud +
               "\nTipo de Ave: " + tipoAve +
               "\n¿Puede volar?: " + (puedeVolar ? "Sí" : "No") +
               "\nEstado de Conservación: " + estadoConservacion;
    }

    @Override
    public double calcularProbabilidadAdopcion() {
        double probabilidad = super.calcularProbabilidadAdopcion();
        
        // Factores adicionales para aves exóticas
        if (estadoConservacion.equals("En peligro")) {
            probabilidad += 0.3;
        } else if (estadoConservacion.equals("Vulnerable")) {
            probabilidad += 0.2;
        } else if (estadoConservacion.equals("Casi amenazada")) {
            probabilidad += 0.1;
        }

        return Math.min(probabilidad, 1.0);
    }
}
