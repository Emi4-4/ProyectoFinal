package org.example;

// Esta clase será parecida a la de Producto en la tarea 1
public abstract class Mascotas {
    private int id;
    private String nombre, tipo;
    private int nivelHambre, nivelFelicidad, nivelSalud, nivelHigiene;

    public Mascotas(int id, String nombre, String tipo){
        this.id=id;
        this.nombre=nombre;
        this.tipo=tipo;
        this.nivelFelicidad=50;
        this.nivelHambre=50;
        this.nivelSalud=100;
        this.nivelHigiene=100;
    }



    public abstract void alimentar(Suministro suministro);

    public abstract void jugar();

    public abstract void limpiar();

    public abstract void mejorarSalud();
    // de este metodo tengo dudas de si dependerá más de los 3 anteriores que es
    // lo que comun mente se ve en simuladores de mascotas

    //Getter y Setter:
    public int getNivelHambre(){return nivelHambre;}
    public void setNivelHambre(int nivelHambre) {
        if (nivelHambre < 0) {
            this.nivelHambre = 0; // El animal está completamente lleno
        } else if (nivelHambre > 100) {
            this.nivelHambre = 100; // El animal está hambriento límite
        } else {
            this.nivelHambre = nivelHambre; // Cualquier valor normal entre 0 y 100
        }
    }



}
