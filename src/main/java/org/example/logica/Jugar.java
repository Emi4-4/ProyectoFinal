package org.example.logica;

//Subclase actividad

public class Jugar implements Actividad {
    @Override
    public void realizar(Mascotas mascota){
        mascota.setNivelFelicidad(mascota.getNivelFelicidad() + 20);
        mascota.setNivelHigiene(mascota.getNivelHigiene() - 10);

        System.out.println(mascota.getNombre() + " ha jugado.");
        System.out.println("Felicidad: " + mascota.getNivelFelicidad() + "/100");
        System.out.println("Higiene: " + mascota.getNivelHigiene() + "/100");
    }
}
