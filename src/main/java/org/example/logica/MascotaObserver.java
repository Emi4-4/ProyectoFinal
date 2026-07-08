package org.example.logica;

/**
 * Observador de cambios de estado de una {@link Mascotas}.
 * <p>
 * Parte del patrón de diseño <b>Observer</b>: los paneles de la interfaz
 * gráfica implementan esta interfaz para refrescar automáticamente los
 * datos mostrados (hambre, felicidad, higiene, salud) cada vez que la
 * mascota cambia, sin que {@link Mascotas} necesite conocer detalles de
 * la interfaz.
 */
public interface MascotaObserver {

    /**
     * Invocado cada vez que cambia algún atributo relevante de la mascota.
     *
     * @param mascota mascota cuyo estado fue actualizado
     */
    void onEstadoActualizado(Mascotas mascota);
}
