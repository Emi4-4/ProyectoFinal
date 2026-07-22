package org.example.logica;
/**
 * Clase que hace extends de Pajaro y simula la especie Colibri
 * Define el sonido que emite y agrupa algunas caracteristicas.
 *
 * @author Lenin
 */
public class Colibri extends Pajaro {
    /**
     * Constructor para inicializar un Pajaro Colibri con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del pajaro
     */
    public Colibri(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "pajaro.wav";
    }
}
