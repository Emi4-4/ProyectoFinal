package org.example.logica;
/**
 * Clase que hace extends de Gato y simula la raza Calico
 * Define el sonido que emite y agrupa algunas caracteristicas.
 *
 * @author Lenin
 */
public class Calico extends Gato {
    /**
     * Constructor para inicializar un gato Calico con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del gato
     */
    public Calico(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "ruu-miau";
    }

}

