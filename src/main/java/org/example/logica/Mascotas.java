package org.example.logica;

import java.util.ArrayList;
import java.util.List;

// Esta clase será parecida a la de Producto en la tarea 1
public abstract class Mascotas {
    private final int id;
    private String nombre, tipo;
    private int nivelHambre, nivelFelicidad, nivelSalud, nivelHigiene;
    private final List<MascotaObserver> observadores = new ArrayList<>();
    public abstract TipoSuministro getAlimentoPermitido();
    public abstract String emitirSonido();

    public Mascotas(int id, String nombre, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivelFelicidad = 50;
        this.nivelHambre = 50;
        this.nivelSalud = 100;
        this.nivelHigiene = 100;
    }

    /**
     * Registra un observador que será notificado ante cualquier cambio
     * de estado de la mascota.
     *
     * @param observador observador a registrar
     */
    public void agregarObservador(MascotaObserver observador) {
        if (observador != null) {
            observadores.add(observador);
        }
    }

    /**
     * Elimina un observador previamente registrado.
     *
     * @param observador observador a remover
     */
    public void removerObservador(MascotaObserver observador) {
        observadores.remove(observador);
    }

    private void notificarObservadores() {
        for (MascotaObserver o : observadores) {
            o.onEstadoActualizado(this);
        }
    }

    public int getId(){ return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre(){ return nombre;
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

    @Override
    public String toString() {
        return String.format("ID: %-3d | %-10s (%-6s) | Hambre: %-3d | Fel: %-3d | Hig: %-3d | Sal: %-3d\n",
                id, nombre, tipo, nivelHambre, nivelFelicidad, nivelHigiene, nivelSalud);
    }
}
