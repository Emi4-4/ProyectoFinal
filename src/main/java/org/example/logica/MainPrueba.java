package org.example.logica;

public class MainPrueba {

    public static void main(String[] args) {

        // Crear mascota concreta
        Mascotas gato = new Siames(1, "GatoPrueba", "Gato");
        // Crear inventario de suministros
        Deposito<Suministro> inventario = new Deposito<>();
        //Prueba alimentar
        // Agregar comida para gato al inventario
        inventario.addProducto(new Suministro(TipoSuministro.ALIMENTO_GATO));
        // Crear actividad
        Actividad alimentar = new Alimentar(inventario);
        // Ver estado inicial
        System.out.println("Hambre inicial: " + gato.getNivelHambre());
        // Ejecutar actividad
        alimentar.realizar(gato);
        // Ver resultado
        System.out.println("Hambre final: " + gato.getNivelHambre());

        //Prueba Limpiar (Primero se ensucia al gato)
        gato.setNivelHigiene(20);
        inventario.addProducto(new Suministro(TipoSuministro.SHAMPOO));
        Actividad limpiar = new Limpiar(inventario);
        System.out.println("Higiene inicial " + gato.getNivelHigiene());
        limpiar.realizar(gato);
        System.out.println("Higiene final: " + gato.getNivelHigiene());

        //Prueba Curar
        gato.setNivelSalud(20);
        inventario.addProducto(new Suministro(TipoSuministro.MEDICINA));
        Actividad curar = new Curar(inventario);
        System.out.println("Salud inicial " + gato.getNivelSalud());
        curar.realizar(gato);
        System.out.println("Salud final: " + gato.getNivelSalud());

        //Prueba jugar (Jugar aumenta la felicidad pero reduce la higiene)
        gato.setNivelFelicidad(20);
        Actividad jugar = new Jugar();
        System.out.println("Felicidad inicial: " + gato.getNivelFelicidad());
        jugar.realizar(gato);


    }
}