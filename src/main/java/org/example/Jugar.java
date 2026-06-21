package org.example;

//Subclase actividad

public class Jugar implements Actividad {
    @Override
    public void realizar(Mascotas mascota){
        mascota.setNivelFelicidad(mascota.getNivelFelicidad()+20);
        mascota.setNivelHigiene(mascota.getNivelHigiene()-10);
    }
}
