package org.example.logica;
/**
 * Clase que hace extends de Gato y simula la raza Siames
 * Define el sonido que emite y agrupa algunas caracteristicas.
 *
 * @author Lenin
 * @author Valentina
 */
public class Siames extends Gato {
    /**
     * Constructor para inicializar un gato Siames con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del gato
     */
    public Siames(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "gato.wav";
    }

}
