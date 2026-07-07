package org.example.logica;

public class pruebaproveedor {

    public static void main(String[] args) {

        Tienda tienda = new Tienda(20000);
        System.out.println("Presupuesto inicial: " + tienda.getPresupuesto());
        Proveedor proveedor = new Proveedor();

        proveedor.venderMascota(tienda, 1);

        System.out.println("Presupuesto: " + tienda.getPresupuesto());
        System.out.println("Mascotas en tienda: " + tienda.getInventarioMascotas().getSize());
    }
}