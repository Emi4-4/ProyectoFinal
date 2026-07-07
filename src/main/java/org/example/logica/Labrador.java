package org.example.logica;


public class Labrador extends Perro {

    public Labrador(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "guau";
    }

}

