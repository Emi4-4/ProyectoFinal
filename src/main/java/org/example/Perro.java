package org.example;

abstract class Perro extends Mascotas{
    public Perro(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public void alimentar(Suministro suministro) {
        if (suministro.getTipo() == TipoSuministro.ALIMENTO_PERRO) {
            int nuevaHambre = getNivelHambre() - suministro.getTipo().getEfecto();
            setNivelHambre(nuevaHambre);
            System.out.println("¡El perro ha comido!");
        } else {
            System.out.println("A un perro no le puedes dar esto para comer :(");
        }
    }

}
