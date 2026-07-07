package org.example.logica;

public class Proveedor {

    private Deposito<Mascotas> stockMascotas;
    private Deposito<Suministro> stockSuministros;

    private static final int PRECIO_GATO = 8000;
    private static final int PRECIO_PERRO = 10000;
    private static final int PRECIO_PEZ = 3000;
    private static final int PRECIO_PAJARO = 5000;

    public Proveedor() {
        stockMascotas = new Deposito<>();
        stockSuministros = new Deposito<>();

        inicializarStock();
    }

    private void inicializarStock() {

        // Mascotas
        stockMascotas.addProducto(new Siames(1, "Sol", "Gato"));
        stockMascotas.addProducto(new Siames(2, "Luna", "Gato"));
        stockMascotas.addProducto(new Labrador(3, "Poncho", "Perro"));
        stockMascotas.addProducto(new Labrador(4, "Juancho", "Perro"));



        // Suministros
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_GATO));
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PERRO));
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PEZ));
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PAJARO));
        stockSuministros.addProducto(new Suministro(TipoSuministro.MEDICINA));
        stockSuministros.addProducto(new Suministro(TipoSuministro.SHAMPOO));
    }

    public Deposito<Mascotas> getStockMascotas() {
        return stockMascotas;
    }

    public Deposito<Suministro> getStockSuministros() {
        return stockSuministros;
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

            case "Pájaro":
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

        System.out.println("Se compró " + mascota.getNombre()
                + " por $" + precio);

        return true;
    }

    public boolean venderSuministro(Tienda tienda, TipoSuministro tipo) {

        Suministro suministro = stockSuministros.buscarElemento(
                s -> s.getTipo() == tipo
        );

        if (suministro == null) {
            System.out.println("No hay stock de " + tipo);
            return false;
        }

        if (tienda.getPresupuesto() < tipo.getPrecio()) {
            System.out.println("Presupuesto insuficiente.");
            return false;
        }

        tienda.setPresupuesto(tienda.getPresupuesto() - tipo.getPrecio());

        tienda.getInventarioSuministros().addProducto(suministro);

        stockSuministros.removerElemento(suministro);

        System.out.println("Se compró " + tipo);

        return true;
    }
}