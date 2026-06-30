package org.example;

public class Tienda {
    private Deposito<Mascotas> inventarioMascotas;
    private Deposito<Suministro> inventarioSuministros;
    private int presupuesto;

    public Tienda(){
        inventarioMascotas=new Deposito<>();
        presupuesto=20000;
    }

    public void agregarMascota(Mascotas m){
        this.inventarioMascotas.addProducto(m);
        System.out.println(m.getNombre() + " ha sido ingresado a la tienda.");
    }
    public Mascotas ventaMascota(){
        // debería incluir el cliente y la mascota que se va a vender
        return null;
    }

    public void ejecutarActividadEnMascota(int id, Actividad actividad) {
        Mascotas mascota = inventarioMascotas.buscarElemento(m -> m.getId() == id);
        if (mascota != null) {
            actividad.realizar(mascota);
        } else {
            System.out.println("Error: No se encontró una mascota con el ID: " + id);
        }
    }
    //getter y setter
    public Deposito<Suministro> getInventarioSuministros() {
        return this.inventarioSuministros;
    }

}
