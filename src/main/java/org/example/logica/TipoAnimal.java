package org.example.logica;
/**
 * Enumeración que define las categorías o especies principales de animales
 * que pueden gestionarse en la tienda.
 *
 * @author Emiliano
 */
public enum TipoAnimal {
    PEZ("Pez"),
    GATO("Gato"),
    PERRO("Perro"),
    PAJARO("Pájaro");

    private final String nombre;

    /**
     * Constructor para asociar un nombre descriptivo a cada tipo de animal.
     *
     * @param nombre Nombre legible de la categoría de animal
     */
    TipoAnimal(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene el nombre descriptivo del tipo de animal.
     *
     * @return El nombre en formato de cadena de texto
     */
    public String getNombre() {
        return nombre;
    }
}