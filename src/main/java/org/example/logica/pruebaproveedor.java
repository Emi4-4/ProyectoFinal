package org.example.logica;

public class pruebaproveedor {

    public static void main(String[] args) {

        Tienda tienda = new Tienda(100000);
        System.out.println("Presupuesto inicial: " + tienda.getPresupuesto());
        Proveedor proveedor = new Proveedor();

        proveedor.venderMascota(tienda, 1);
        proveedor.venderMascota(tienda, 2);
        proveedor.venderMascota(tienda, 3);
        proveedor.venderMascota(tienda, 4);
        proveedor.venderMascota(tienda, 5);
        proveedor.venderMascota(tienda, 6);
        proveedor.venderMascota(tienda, 7);
        proveedor.venderMascota(tienda, 8);
        proveedor.venderMascota(tienda, 9);

        System.out.println("Presupuesto: " + tienda.getPresupuesto());
        System.out.println("Mascotas en tienda: " + tienda.getInventarioMascotas().getSize());

        proveedor.venderSuministro(tienda, TipoSuministro.ALIMENTO_GATO);
        proveedor.venderSuministro(tienda, TipoSuministro.ALIMENTO_GATO);
        proveedor.venderSuministro(tienda, TipoSuministro.ALIMENTO_GATO);

        System.out.println("Presupuesto: " + tienda.getPresupuesto());
        System.out.println("Suministro en tienda: " + tienda.getInventarioSuministros().getSize());
    }
}