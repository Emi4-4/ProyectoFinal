package org.example.logica;

public enum TipoAnimal {
    PEZ("Pez"),
    GATO("Gato"),
    PERRO("Perro"),
    PAJARO("Pájaro");

    private final String nombre;

    TipoAnimal(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}