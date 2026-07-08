package org.example.logica;

public class Proveedor {

    private Deposito<Mascotas> stockMascotas;

    private static final int PRECIO_GATO = 5000;
    private static final int PRECIO_PERRO = 6000;
    private static final int PRECIO_PEZ = 8000;
    private static final int PRECIO_PAJARO = 10000;

    public Proveedor() {
        stockMascotas = new Deposito<>();
        inicializarStock();
    }


    private void inicializarStock() {

        // Mascotas
        stockMascotas.addProducto(new Siames(1, "Siames", "Gato"));
        stockMascotas.addProducto(new Calico(2, "Calico", "Gato"));
        stockMascotas.addProducto(new Labrador(3, "Labrador", "Perro"));
        stockMascotas.addProducto(new Chihuahua(4, "Chihuahua", "Perro"));
        stockMascotas.addProducto(new Colibri(5, "Colibri", "Pajaro"));
        stockMascotas.addProducto(new Tucan(6, "Tucan", "Pajaro"));
        stockMascotas.addProducto(new PezDorado(7, "Pez Dorado", "Pez"));
        stockMascotas.addProducto(new PezPayaso(8, "Pez Payaso", "Pez"));


    }

    private int siguienteId = 9;

    public void reponerMascotas() {

        stockMascotas.addProducto(new Siames(siguienteId++, "Siames", "Gato"));
        stockMascotas.addProducto(new Calico(siguienteId++, "Calico", "Gato"));
        stockMascotas.addProducto(new Labrador(siguienteId++, "Labrador", "Perro"));
        stockMascotas.addProducto(new Chihuahua(siguienteId++, "Chihuahua", "Perro"));
        stockMascotas.addProducto(new Colibri(siguienteId++, "Colibri", "Pajaro"));
        stockMascotas.addProducto(new Tucan(siguienteId++, "Tucan", "Pajaro"));
        stockMascotas.addProducto(new PezDorado(siguienteId++, "Pez Dorado", "Pez"));
        stockMascotas.addProducto(new PezPayaso(siguienteId++, "Pez Payaso", "Pez"));
    }

    public Deposito<Mascotas> getStockMascotas() {
        return stockMascotas;
    }


    public boolean venderMascota(Tienda tienda, int idMascota) {

        Mascotas mascota = stockMascotas.buscarElemento(
                m -> m.getId() == idMascota
        );

        if (mascota == null) {
            System.out.println("La mascota no existe.");
            return false;
        }

        int precio;

        switch (mascota.getTipo()) {
            case "Gato":
                precio = PRECIO_GATO;
                break;

            case "Perro":
                precio = PRECIO_PERRO;
                break;

            case "Pez":
                precio = PRECIO_PEZ;
                break;

            case "Pajaro":
                precio = PRECIO_PAJARO;
                break;

            default:
                System.out.println("Tipo de mascota desconocido.");
                return false;
        }

        if (tienda.getPresupuesto() < precio) {
            System.out.println("Presupuesto insuficiente.");
            return false;
        }

        tienda.setPresupuesto(tienda.getPresupuesto() - precio);

        tienda.agregarMascota(mascota);

        stockMascotas.removerElemento(mascota);

        if (stockMascotas.getSize() < 2) {
            reponerMascotas();
            System.out.println("El proveedor ha recibido nuevas mascotas.");
        }

        System.out.println("Se compró " + mascota.getNombre()
                + " por $" + precio);

        return true;
    }

    public boolean venderSuministro(Tienda tienda, TipoSuministro tipo) {

        if (tienda.getPresupuesto() < tipo.getPrecio()) {
            System.out.println("Presupuesto insuficiente.");
            return false;
        }

        tienda.setPresupuesto(
                tienda.getPresupuesto() - tipo.getPrecio()
        );

        tienda.getInventarioSuministros()
                .addProducto(new Suministro(tipo));

        System.out.println("Se compró " + tipo);

        return true;
    }
}