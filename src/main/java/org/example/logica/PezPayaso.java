package org.example.logica;
/**
 * Clase que hace extends de Pez y simula la especie Pez Payaso
 * Define el sonido que emite y agrupa algunas caracteristicas.
 *
 * @author Lenin
 */
public class PezPayaso extends Pez {
    /**
     * Constructor para inicializar un Pez payaso con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del Pez
     */
    public PezPayaso(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "gluglu";
    }
}