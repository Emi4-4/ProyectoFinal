package org.example.logica;

public class Alimentar implements Actividad {
    private Deposito<Suministro> inventarioSuministros;

    public Alimentar(Deposito<Suministro> inventarioSuministros){
        this.inventarioSuministros=inventarioSuministros;
    }
    @Override
    public void realizar(Mascotas mascota){
        Suministro comidaAdecuada = inventarioSuministros.buscarElemento(
                suministro -> suministro.getTipo() == mascota.getAlimentoPermitido()
        );
        if (comidaAdecuada == null) {
            throw new IllegalStateException("No quedan existencias de " +
                    mascota.getAlimentoPermitido() + " en el inventario.");
        }

        int hambreActual = mascota.getNivelHambre();
        int efectoComida = comidaAdecuada.getTipo().getEfecto();
        mascota.setNivelHambre(hambreActual - efectoComida);

        inventarioSuministros.removerElemento(comidaAdecuada);
        System.out.println(mascota.getNombre() + " ha sido alimentado.");
        System.out.println("Hambre actual: " + mascota.getNivelHambre() + "/100");
    }

}
