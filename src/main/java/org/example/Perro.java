package org.example;

abstract class Perro extends Mascotas{
    public Perro(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public TipoSuministro getAlimentoPermitido() {
        return TipoSuministro.ALIMENTO_PERRO;
    }
}
