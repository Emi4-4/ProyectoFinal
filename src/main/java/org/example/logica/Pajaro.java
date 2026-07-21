package org.example.logica;
/**
 * Clase abstracta que agrupa las características comunes de los Pajaros.
 * Define el tipo de alimento permitido y el tipo de animal correspondiente.
 *
 * @author Emiliano
 * @author Lenin
 */
abstract class Pajaro extends Mascotas {
    /**
     * Constructor para inicializar un pajaro con sus atributos base.
     *
     * @param id Identificador único de la mascota
     * @param nombre Nombre de la mascota
     * @param tipo Raza o subtipo del pajaro
     */
    public Pajaro(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public TipoSuministro getAlimentoPermitido() {
        return TipoSuministro.ALIMENTO_PAJARO;
    }

    @Override
    public TipoAnimal getTipoAnimal() {
        return TipoAnimal.PAJARO;
    }
}