package org.example.logica;


public class Chihuahua extends Perro {

    public Chihuahua(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "guau";
    }
}
