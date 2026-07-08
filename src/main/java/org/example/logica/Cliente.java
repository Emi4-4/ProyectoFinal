package org.example.logica;

/**
 * Representa a un cliente virtual interesado en adoptar una mascota de
 * la tienda. Cada cliente cuenta con un presupuesto propio, independiente
 * del presupuesto de la {@link Tienda}.
 */
public class Cliente {
    private String sonidoMascota;
    private final int id;
    private String nombre;
    private int presupuesto;
    private Mascotas mascotaComprada;

    /**
     * @param id          identificador único del cliente
     * @param nombre      nombre del cliente
     * @param presupuesto presupuesto disponible para comprar
     */
    public Cliente(int id, String nombre, int presupuesto){
        this.id=id;
        this.nombre=nombre;
        this.presupuesto=presupuesto;
        this.sonidoMascota="";
        //
    }

    /**
     * Intenta comprar la mascota indicada al precio dado. La compra solo
     * se concreta si el precio no supera el presupuesto disponible.
     *
     * @param mascota mascota que el cliente desea adoptar
     * @param precio  precio de venta acordado
     * @return {@code true} si la compra se concretó, {@code false} si el presupuesto era insuficiente
     */
    public boolean comprarMascota(Mascotas mascota, int precio) {
        if (precio > presupuesto) {
            return false;
        }
        this.mascotaComprada = mascota;
        this.presupuesto -= precio;
        this.sonidoMascota = mascota.emitirSonido();
        System.out.println("Has adoptado a " + mascota.getNombre());
        return true;
    }

    /** @return identificador único del cliente */
    public int getId() {
        return id;
    }

    /** @return nombre del cliente */
    public String getNombre() {
        return nombre;
    }

    /** @return la mascota comprada por este cliente, o {@code null} si aún no compra ninguna */
    public Mascotas getMascotaComprada() {
        return mascotaComprada;
    }

    /** @return el sonido de la última mascota adoptada por este cliente */
    public String getSonidoMascotaComprada() {
        return sonidoMascota;
    }

    /** @return presupuesto restante del cliente */
    public int getPresupuesto() {
        return presupuesto;
    }
}
