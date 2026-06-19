package org.example;

public class Tienda {
    private Deposito<Mascotas> inventarioMascotas;
    private int presupuesto;
    public Tienda(){
        inventarioMascotas=new Deposito<>();
        presupuesto=20000;
    }

    public void alimentarMascota(int id) {

    }
    public void agregarMascota(Mascotas m){

    }
    public Mascotas ventaMascota(){
        // debería incluir el cliente y la mascota que se va a vender
        return null;
    }
}
