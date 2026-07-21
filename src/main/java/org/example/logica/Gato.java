package org.example.logica;

/**
 * Clase abstracta que agrupa las características comunes de los gatos.
 * Define el tipo de alimento permitido y el tipo de animal correspondiente.
 *
 * @author Emiliano
 * @author Lenin
 */

abstract class Gato extends Mascotas{
    /**
     * Constructor para inicializar un gato con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del gato
     */
    public Gato(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public TipoSuministro getAlimentoPermitido() {
        return TipoSuministro.ALIMENTO_GATO;
    }

    @Override
    public TipoAnimal getTipoAnimal() {
        return TipoAnimal.GATO;
    }
}
