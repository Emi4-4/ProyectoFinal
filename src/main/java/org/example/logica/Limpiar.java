package org.example.logica;

public class Limpiar implements Actividad {

    private Deposito<Suministro> inventarioSuministros;

    public Limpiar(Deposito<Suministro> inventarioSuministros) {
        this.inventarioSuministros = inventarioSuministros;
    }

    @Override
    public void realizar(Mascotas mascota) {

        Suministro shampoo = inventarioSuministros.buscarElemento(
                suministro -> suministro.getTipo() == TipoSuministro.SHAMPOO
        );

        if (shampoo == null) {
            System.out.println("No queda shampoo en el inventario.");
            return;
        }

        int efecto = shampoo.getTipo().getEfecto();
        mascota.setNivelHigiene(mascota.getNivelHigiene() + efecto);

        inventarioSuministros.removerElemento(shampoo);

        System.out.println(mascota.getNombre() + " ha sido limpiado.");
        System.out.println("Higiene actual: " + mascota.getNivelHigiene() + "/100");
    }
}