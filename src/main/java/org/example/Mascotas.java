package org.example;

// Esta clase será parecida a la de Producto en la tarea 1
public abstract class Mascotas {
    private int id;
    private String nombre, tipo;
    private int nivelHambre, nivelFelicidad, nivelSalud;

    public Mascotas(int id, String nombre, String tipo){
        this.id=id;
        this.nombre=nombre;
        this.tipo=tipo;
        this.nivelFelicidad=50;
        this.nivelHambre=50;
        this.nivelSalud=100;
    }



    public abstract String alimentar();

    public abstract void jugar();

    public abstract void limpiar();

    public abstract void mejorarSalud();
    // de este metodo tengo dudas de si dependerá más de los 3 anteriores que es
    // lo que comun mente se ve en simuladores de mascotas

}
