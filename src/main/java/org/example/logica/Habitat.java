package org.example.logica;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un hábitat donde viven las mascotas.
 * Cada hábitat tiene una capacidad máxima de mascotas.
 *
 * @author Emiliano Allen
 */
public class Habitat {
    private final int id;
    private final int capacidad;
    private final List<Mascotas> mascotas;
    private final int costo;
    private final TipoHabitat tipo;

    public Habitat(int id, TipoHabitat tipo) {
        this.id = id;
        this.tipo = tipo;
        this.capacidad = tipo.getCapacidad();
        this.costo = tipo.getPrecio();
        this.mascotas = new ArrayList<>();
    }

    /**
     * Intenta agregar una mascota al hábitat
     * @param mascota mascota a agregar
     * @return true si se agregó, false si el hábitat está lleno
     */
    public boolean agregarMascota(Mascotas mascota) {
        // Validar que sea del tipo correcto
        if (mascota.getTipoAnimal() != tipo.getTipoAnimal()) {
            System.out.println("❌ Error: " + mascota.getNombre() + " no es compatible con " + tipo.getNombre());
            return false;
        }

        // Validar que haya espacio
        if (estaLleno()) {
            throw new IllegalStateException("Error: " + tipo.getNombre() + " está lleno");
        }

        mascotas.add(mascota);
        mascota.setHabitat(this);
        return true;
    }



    /**
     * Remueve una mascota del hábitat
     * @param mascota mascota a remover
     * @return true si se removió, false si no estaba
     */

    public boolean removerMascota(Mascotas mascota) {
        boolean removida = mascotas.remove(mascota);
        if (removida) {
            mascota.setHabitat(null);
        }
        return removida;
    }

    /**
     * @return cantidad de espacios disponibles
     */
    public int obtenerEspaciosDisponibles() {
        return capacidad - mascotas.size();
    }

    /**
     * @return true si el hábitat está lleno
     */
    public boolean estaLleno() {
        return mascotas.size() >= capacidad;
    }

    /**
     * @return lista de mascotas en el hábitat
     */
    public List<Mascotas> obtenerMascotas() {
        return new ArrayList<>(mascotas);
    }

    public int getId() {
        return id;
    }

    public TipoHabitat getTipo() {
        return tipo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCosto() {
        return costo;
    }

    public int getOcupados() {
        return mascotas.size();
    }

    @Override
    public String toString() {
        return String.format("Habitat #%d | Capacidad: %d/%d | Costo: $%d",
                id, mascotas.size(), capacidad, costo);
    }

}
