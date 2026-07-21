package org.example.logica;
/**
 * Interfaz que define el comportamiento para las distintas actividades
 * que se pueden realizar sobre una mascota (alimentar, jugar, limpiar, curar).
 *
 * @author Lenin
 */

public interface Actividad {

    /**
     * Ejecuta la actividad sobre una mascota específica.
     *
     * @param mascota Mascota sobre la cual se aplicará la actividad
     */
    void realizar(Mascotas mascota);
}
