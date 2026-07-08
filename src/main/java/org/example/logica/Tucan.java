package org.example.logica;

public class Tucan extends Pajaro {
    public Tucan(int id, String nombre, String tipo) {
        super(id, nombre, tipo);
    }
    @Override
    public String emitirSonido() {
        return "sonidos de tucan";
    }
}