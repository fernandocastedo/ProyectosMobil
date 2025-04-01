package com.example.gestiondemascotas;

import java.io.Serializable;

public class Ave extends Mascota implements Serializable {
    protected String tipoAve;
    protected boolean puedeVolar;

    public Ave(String nombre, int edad, String raza, double peso, String estadoSalud, String tipoAve, boolean puedeVolar) {
        super(nombre, edad, raza, peso, estadoSalud);
        this.tipoAve = tipoAve;
        this.puedeVolar = puedeVolar;
    }

    @Override
    public String obtenerInformacion() {
        return "Ave - Nombre: " + nombre + 
               "\nEdad: " + edad + " años" +
               "\nRaza: " + raza +
               "\nPeso: " + peso + " kg" +
               "\nEstado de Salud: " + estadoSalud +
               "\nTipo de Ave: " + tipoAve +
               "\n¿Puede volar?: " + (puedeVolar ? "Sí" : "No");
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

        if (puedeVolar) probabilidad += 0.2;

        return Math.min(probabilidad, 1.0);
    }
}
