package org.example;

// Esta clase será parecida a la de Producto en la tarea 1
public abstract class Mascotas {
    private int id;
    private String nombre, tipo;
    private int nivelHambre, nivelFelicidad, nivelSalud, nivelHigiene;
    public abstract TipoSuministro getAlimentoPermitido();

    public Mascotas(int id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivelFelicidad = 50;
        this.nivelHambre = 50;
        this.nivelSalud = 100;
        this.nivelHigiene = 100;
    }


    //Getter y Setter (Hambre):
    public int getNivelHambre() {
        return nivelHambre;
    }

    public void setNivelHambre(int nivelHambre) {
        if (nivelHambre < 0) {
            this.nivelHambre = 0; // El animal está completamente lleno
        } else if (nivelHambre > 100) {
            this.nivelHambre = 100; // El animal está hambriento límite
        } else {
            this.nivelHambre = nivelHambre; // Cualquier valor normal entre 0 y 100
        }
    }

    //Getter y Setter (Felicidad):
    public int getNivelFelicidad() {
        return nivelFelicidad;
    }

    public void setNivelFelicidad(int nivelFelicidad) {
        if (nivelFelicidad < 0) {
            this.nivelFelicidad = 0; // El animal está deprimido
        } else if (nivelFelicidad > 100) {
            this.nivelFelicidad = 100; // El animal es muy feliz
        } else {
            this.nivelFelicidad = nivelFelicidad; // Cualquier valor normal entre 0 y 100
        }
    }

    //Getter y Setter (Higiene):
    public int getNivelHigiene() {
        return nivelHigiene;
    }

    public void setNivelHigiene(int nivelHigiene) {
        if (nivelHigiene < 0) {
            this.nivelHigiene = 0; // El animal está sucio
        } else if (nivelHigiene > 100) {
            this.nivelHigiene = 100; // El animal esta impecable
        } else {
            this.nivelHigiene = nivelHigiene; // Cualquier valor normal entre 0 y 100
        }
    }

    //Getter y Setter (Salud):
    public int getNivelSalud() {
        return nivelSalud;
    }

    public void setNivelSalud(int nivelSalud) {
        if (nivelSalud < 0) {
            this.nivelSalud = 0; // El animal está sucio
        } else if (nivelSalud > 100) {
            this.nivelSalud = 100; // El animal esta impecable
        } else {
            this.nivelSalud = nivelSalud; // Cualquier valor normal entre 0 y 100
        }
    }
}
