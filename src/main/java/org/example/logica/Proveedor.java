package org.example.logica;

import java.io.StreamCorruptedException;

public class Proveedor {
    private static Proveedor instancia;

    private Deposito<Mascotas> stockMascotas;
    private Deposito<Suministro> stockSuministros;

    private static final int PRECIO_GATO = 5000;
    private static final int PRECIO_PERRO = 6000;
    private static final int PRECIO_PEZ = 8000;
    private static final int PRECIO_PAJARO = 10000;

    public Proveedor() {
        stockMascotas = new Deposito<>();
        stockSuministros = new Deposito<>();
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
        stockMascotas.addProducto(new PezDorado(7, "PezDorado", "Pez"));
        stockMascotas.addProducto(new PezPayaso(8, "PezPayaso", "Pez"));

        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_GATO));
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PERRO));
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PEZ));
        stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PAJARO));
        stockSuministros.addProducto(new Suministro(TipoSuministro.MEDICINA));
        stockSuministros.addProducto(new Suministro(TipoSuministro.SHAMPOO));
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


    public boolean venderMascota(Tienda tienda, int idMascota) throws  MascotaNoEncontradaException{

        Mascotas mascota = stockMascotas.buscarElemento(
                m -> m.getId() == idMascota
        );

        if (mascota == null) {
            throw new MascotaNoEncontradaException("La mascota con ID " + idMascota + " no existe en el proveedor.");
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

        if (stockMascotas.getSize() < 4) {
            reponerMascotas();
            System.out.println("El proveedor ha recibido nuevas mascotas.");
        }

        System.out.println("Se compró " + mascota.getNombre()
                + " por $" + precio);

        return true;
    }

    public boolean venderSuministro(Tienda tienda, TipoSuministro tipo) throws StockInsuficienteException, PresupuestoInsuficienteException {

        if (tienda.getPresupuesto() < tipo.getPrecio()) {
            throw new PresupuestoInsuficienteException(
                    "Presupuesto insuficiente para comprar " + tipo + " (cuesta $" + tipo.getPrecio() + ").");
        }
        Suministro suministro = stockSuministros.buscarElemento(s -> s.getTipo() == tipo);
        if (suministro == null) {
            throw new StockInsuficienteException("No hay stock de " + tipo + " en el proveedor.");
        }
        if (tienda.getPresupuesto() < tipo.getPrecio()) {
            throw new PresupuestoInsuficienteException(
                    "Presupuesto insuficiente para comprar " + tipo + " (cuesta $" + tipo.getPrecio() + ").");
        }
        tienda.setPresupuesto(
                tienda.getPresupuesto() - tipo.getPrecio()
        );

        tienda.getInventarioSuministros()
                .addProducto(new Suministro(tipo));

        stockSuministros.removerElemento(suministro);
        System.out.println("Se compró " + tipo);

        return true;
    }
    /**
     * Obtiene la única instancia del proveedor (patrón Singleton),
     * creándola la primera vez que se solicita.
     *
     * @return la instancia compartida de {@link Proveedor}
     */
    public static synchronized Proveedor getInstancia() {
        if (instancia == null) {
            instancia = new Proveedor();
        }
        return instancia;
    }
}