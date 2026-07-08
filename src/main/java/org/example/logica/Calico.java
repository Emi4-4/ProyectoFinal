package org.example.logica;

public class Calico extends Gato {
    public Calico(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "ruu-miau";
    }

}

