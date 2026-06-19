package org.example;

abstract class Pez extends Mascotas{
    public Pez(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public void alimentar(Suministro suministro) {
        if (suministro.getTipo() == TipoSuministro.ALIMENTO_PEZ) {
            int nuevaHambre = getNivelHambre() - suministro.getTipo().getEfecto();
            setNivelHambre(nuevaHambre);
            System.out.println("¡El pez ha comido!");
        } else {
            System.out.println("A un pez no le puedes dar esto para comer :(");
        }
    }
}
