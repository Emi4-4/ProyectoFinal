package org.example.logica;

/**
 * Clase para actividad concreta que permite alimentar a una mascota.
 * Consume un suministro del tipo de alimento adecuado para la mascota
 * y reduce su nivel de hambre según el efecto del suministro.
 *
 * @author Emiliano
 */
public class Alimentar implements Actividad {
    private Deposito<Suministro> inventarioSuministros;
    /**
    * Constructor que recibe el inventario de suministros de la tienda.
    * @param inventarioSuministros Inventario donde se buscará la comida
     */
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
