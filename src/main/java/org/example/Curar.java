package org.example;

public class Curar implements Actividad {
    @Override
    public void realizar(Mascotas mascota){
        mascota.setNivelSalud(mascota.getNivelFelicidad()+10);
        mascota.setNivelHigiene(mascota.getNivelSalud()+20);
    }
}
