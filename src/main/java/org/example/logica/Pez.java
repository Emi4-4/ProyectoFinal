package org.example.logica;

abstract class Pez extends Mascotas{
    public Pez(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public TipoSuministro getAlimentoPermitido() {
        return TipoSuministro.ALIMENTO_PEZ;
    }
}
