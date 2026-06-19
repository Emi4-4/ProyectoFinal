package org.example;

abstract class Pajaro extends Mascotas{
    public Pajaro(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public void alimentar(Suministro suministro) {
        if (suministro.getTipo() == TipoSuministro.ALIMENTO_PAJARO) {
            int nuevaHambre = getNivelHambre() - suministro.getTipo().getEfecto();
            setNivelHambre(nuevaHambre);
            System.out.println("¡El pájaro ha comido!");
        } else {
            System.out.println("A un pájaro no le puedes dar esto para comer :(");
        }
    }
}
