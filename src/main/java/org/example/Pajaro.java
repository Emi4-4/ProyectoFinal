package org.example;

abstract class Pajaro extends Mascotas {
    public Pajaro(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public TipoSuministro getAlimentoPermitido() {
        return TipoSuministro.ALIMENTO_PAJARO;
    }
}