package org.example.logica;

public class PezDorado extends Pez {
    public PezDorado(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "gluglu";
    }
}