package org.example.logica;
/**
 * Clase que hace extends de Perro y simula la raza Labrador
 * Define el sonido que emite y agrupa algunas caracteristicas.
 *
 * @author Lenin
 */

public class Labrador extends Perro {
    /**
     * Constructor para inicializar un Perro Labrador con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del Perro
     */
    public Labrador(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "guau";
    }

}

