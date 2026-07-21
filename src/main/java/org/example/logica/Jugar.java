package org.example.logica;
/**
 * Representa la actividad de jugar con una mascota.
 * Aumenta la felicidad de la mascota pero reduce levemente su higiene.
 *
 * @author Lenin
 */


public class Jugar implements Actividad {

    /**
     * Realiza la acción de jugar, modificando los niveles de felicidad e higiene.
     *
     * @param mascota Mascota que realiza la actividad de juego
     */
    @Override
    public void realizar(Mascotas mascota){
        mascota.setNivelFelicidad(mascota.getNivelFelicidad() + 20);
        mascota.setNivelHigiene(mascota.getNivelHigiene() - 10);

        System.out.println(mascota.getNombre() + " ha jugado.");
        System.out.println("Felicidad: " + mascota.getNivelFelicidad() + "/100");
        System.out.println("Higiene: " + mascota.getNivelHigiene() + "/100");
    }
}
