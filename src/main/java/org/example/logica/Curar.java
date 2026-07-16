package org.example.logica;

public class Curar implements Actividad {

    private Deposito<Suministro> inventario;

    public Curar(Deposito<Suministro> inventario){
        this.inventario = inventario;
    }

    @Override
    public void realizar(Mascotas mascota){

        Suministro medicina = inventario.buscarElemento(
                s -> s.getTipo() == TipoSuministro.MEDICINA
        );

        if(medicina == null){
            System.out.println("No quedan medicinas.");
            return;
        }

        int efecto = medicina.getTipo().getEfecto();
        mascota.setNivelSalud(mascota.getNivelSalud() + efecto);

        inventario.removerElemento(medicina);

        System.out.println(mascota.getNombre() + " ha sido curado.");
        System.out.println("Salud: " + mascota.getNivelSalud() + "/100");
    }
}