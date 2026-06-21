package org.example;

public class Alimentar implements Actividad {
    private Suministro suministro;

    public Alimentar(Suministro suministro){
        this.suministro = suministro;
    }
    @Override
    public void realizar(Mascotas mascota){
        if(suministro.getTipo() == mascota.getAlimentoPermitido()){
            mascota.setNivelHambre(
                    mascota.getNivelHambre() - suministro.getTipo().getEfecto());
            System.out.println("La mascota fue alimentaada");
        } else {
            System.out.println("La mascota no puede consumir esto");
        }
    }

}
