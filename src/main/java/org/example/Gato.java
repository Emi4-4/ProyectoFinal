package org.example;

abstract class Gato extends Mascotas{
    public Gato(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }

    @Override
    public void alimentar(Suministro suministro) {
        if (suministro.getTipo() == TipoSuministro.ALIMENTO_GATO) {
            // Usamos el set que ya viene de Mascotas para bajar el hambre
            int nuevaHambre = getNivelHambre() - suministro.getTipo().getEfecto();
            setNivelHambre(nuevaHambre);
            System.out.println("¡El gato ha comido!");
        } else {
            System.out.println("A un gato no le puedes dar esto para comer :(");
        }
    }
}
