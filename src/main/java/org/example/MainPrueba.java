package org.example;

public class MainPrueba {

    public static void main(String[] args) {

        // Crear mascota concreta
        Mascotas gato = new Siames(1, "GatoPrueba", "Gato");

        // Crear suministro
        Suministro comidaGato =
                new Suministro(TipoSuministro.ALIMENTO_GATO);

        // Crear actividad
        Actividad alimentar =
                new Alimentar(comidaGato);

        // Ver estado inicial
        System.out.println("Hambre inicial: " + gato.getNivelHambre());

        // Ejecutar actividad
        alimentar.realizar(gato);

        // Ver resultado
        System.out.println("Hambre final: " + gato.getNivelHambre());
    }
}