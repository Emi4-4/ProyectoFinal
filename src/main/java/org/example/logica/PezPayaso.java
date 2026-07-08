package org.example.logica;

public class PezPayaso extends Pez {
    public PezPayaso(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "gluglu";
    }
}