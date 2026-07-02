package org.example.logica;

public class Cliente {
    private String sonidoMascota;
    private int vuelto=0;
    private int id;
    private String nombre;
    private int presupuesto;
    private Mascotas mascotaComprada;

    public Cliente(int id, String nombre, int presupuesto){
        this.id=id;
        this.nombre=nombre;
        this.presupuesto=presupuesto;
        this.sonidoMascota="";
        //
    }

    public String sonidomascotaComprada(Mascotas mascota) {
        // hay que agregar
        return "sonidoMascota";
    }

    public void comprarMascota(Mascotas mascota, int precio) {
        if(precio <= presupuesto){
            this.mascotaComprada=mascota;
            this.presupuesto -= precio;
            this.vuelto=this.presupuesto;
            this.sonidoMascota=sonidomascotaComprada(mascota);
            System.out.println("Has adoptado a "+mascota.getNombre());
        }
    }
    public int getPresupuesto(){
        return this.vuelto;
    }

}
