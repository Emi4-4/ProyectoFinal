package org.example;

public class Limpiar implements Actividad{
        @Override
        public void realizar(Mascotas mascota){
            mascota.setNivelHigiene(mascota.getNivelHigiene()+20);
    }
}
