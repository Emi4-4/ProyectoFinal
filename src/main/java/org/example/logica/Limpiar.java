package org.example.logica;

/**
 * Representa la actividad de limpiar a una mascota utilizando shampoo del inventario.
 * Aumenta el nivel de higiene de la mascota consumiendo un suministro válido.
 *
 * @author Emiliano
 * @author Lenin
 */

public class Limpiar implements Actividad {

    private Deposito<Suministro> inventarioSuministros;

    /**
     * Constructor que vincula el inventario de suministros para poder consumir el shampoo.
     *
     * @param inventarioSuministros Depósito actual de suministros de la tienda
     */

    public Limpiar(Deposito<Suministro> inventarioSuministros) {
        this.inventarioSuministros = inventarioSuministros;
    }

    /**
     * Ejecuta la limpieza de la mascota buscando y consumiendo un shampoo del inventario.
     *
     * @param mascota Mascota a la que se le aplicará la limpieza
     * @throws IllegalStateException si no hay shampoo disponible en el inventario
     */
    @Override
    public void realizar(Mascotas mascota) {

        Suministro shampoo = inventarioSuministros.buscarElemento(
                suministro -> suministro.getTipo() == TipoSuministro.SHAMPOO
        );

        if (shampoo == null) {
            throw new IllegalStateException("No queda shampoo en el inventario.");
        }

        int efecto = shampoo.getTipo().getEfecto();
        mascota.setNivelHigiene(mascota.getNivelHigiene() + efecto);

        inventarioSuministros.removerElemento(shampoo);

        System.out.println(mascota.getNombre() + " ha sido limpiado.");
        System.out.println("Higiene actual: " + mascota.getNivelHigiene() + "/100");
    }
}