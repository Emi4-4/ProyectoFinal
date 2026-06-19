package org.example;

public class Cliente {
    private String sonidoMascota;
    private int vuelto=0;

    public Cliente(int id, Tienda tienda){
        Mascotas mascota = tienda.ventaMascota();
        //
    }

    public String mascotaComprada() {
        return sonidoMascota;
    }

}
