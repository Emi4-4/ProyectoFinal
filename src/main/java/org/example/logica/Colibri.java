package org.example.logica;

public class Colibri extends Pajaro {
    public Colibri(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "sonidos de colibri";
    }
}
