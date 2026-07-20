package org.example.logica;

/**
 * Clase para curar una mascota.
 * Consume una medicina del inventario y aumenta nivel de salud.
 *
 * @author Lenin Díaz
 */
public class Curar implements Actividad {
    private Deposito<Suministro> inventario;

    /**
     * Constructor que recibe el inventario de suministros.
     *
     * @param inventario Inventario de la tienda
     */
    public Curar(Deposito<Suministro> inventario){
        this.inventario = inventario;
    }

    @Override
    public void realizar(Mascotas mascota){

        Suministro medicina = inventario.buscarElemento(
                s -> s.getTipo() == TipoSuministro.MEDICINA
        );

        if(medicina == null){
            throw new IllegalStateException("No quedan medicinas en el inventario.");
        }


        int efecto = medicina.getTipo().getEfecto();
        mascota.setNivelSalud(mascota.getNivelSalud() + efecto);

        inventario.removerElemento(medicina);

        System.out.println(mascota.getNombre() + " ha sido curado.");
        System.out.println("Salud: " + mascota.getNivelSalud() + "/100");
    }
}