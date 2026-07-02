package org.example;

abstract class Gato extends Mascotas{
    public Gato(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public TipoSuministro getAlimentoPermitido() {
        return TipoSuministro.ALIMENTO_GATO;
    }
}
