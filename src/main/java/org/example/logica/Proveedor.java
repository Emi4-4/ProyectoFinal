package org.example.logica;

import java.io.StreamCorruptedException;

public class Proveedor {
    private static Proveedor instancia;

    private Deposito<Mascotas> stockMascotas;
    private Deposito<Suministro> stockSuministros;

    private static final int PRECIO_GATO = 5000;
    private static final int PRECIO_PERRO = 6000;
    private static final int PRECIO_PEZ = 2500;
    private static final int PRECIO_PAJARO = 1100;

    public Proveedor() {
        stockMascotas = new Deposito<>();
        stockSuministros = new Deposito<>();
        inicializarStock();
    }


    private void inicializarStock() {

        for (int i = 0; i < 2; i++) {
            stockMascotas.addProducto(new Siames(siguienteId++, "Siames", "Gato"));
            stockMascotas.addProducto(new Calico(siguienteId++, "Calico", "Gato"));
            stockMascotas.addProducto(new Labrador(siguienteId++, "Labrador", "Perro"));
            stockMascotas.addProducto(new Chihuahua(siguienteId++, "Chihuahua", "Perro"));
            stockMascotas.addProducto(new Colibri(siguienteId++, "Colibri", "Pajaro"));
            stockMascotas.addProducto(new Tucan(siguienteId++, "Tucan", "Pajaro"));
            stockMascotas.addProducto(new PezDorado(siguienteId++, "PezDorado", "Pez"));
            stockMascotas.addProducto(new PezPayaso(siguienteId++, "PezPayaso", "Pez"));
        }

        for (int i = 0; i < 10; i++) {
            stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_GATO));
            stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PERRO));
            stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PEZ));
            stockSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PAJARO));
            stockSuministros.addProducto(new Suministro(TipoSuministro.MEDICINA));
            stockSuministros.addProducto(new Suministro(TipoSuministro.SHAMPOO));
        }
        System.out.println("✓ Proveedor inicializado con stock (60 suministros totales, 8 mascotas)");
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

        Habitat habitatDisponible = tienda.getInventarioHabitats().buscarElemento(
                h -> !h.estaLleno() && h.getTipo().getTipoAnimal() == mascota.getTipoAnimal()
        );

        if (habitatDisponible == null) {
            throw new IllegalStateException(
                    "No hay hábitats disponibles para " + mascota.getNombre() +
                            ". Necesitas un " + mascota.getTipoAnimal().getNombre());
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


        System.out.println("Se compró " + mascota.getNombre()
                + " por $" + precio);

        return true;
    }

    public boolean venderSuministro(Tienda tienda, TipoSuministro tipo, int cantidad) throws StockInsuficienteException, PresupuestoInsuficienteException {

        // Validar que hay suficiente stock
        int stockActual = contarSuministro(tipo);
        if (stockActual < cantidad) {
            throw new StockInsuficienteException(
                    "Solo hay " + stockActual + " unidades de " + tipo +
                            " disponibles (intentaste comprar " + cantidad + ").");
        }

        int costoTotal = tipo.getPrecio() * cantidad;

        // Validar presupuesto
        if (tienda.getPresupuesto() < costoTotal) {
            throw new PresupuestoInsuficienteException(
                    "Presupuesto insuficiente. Necesitas $" + costoTotal +
                            " pero tienes $" + tienda.getPresupuesto());
        }

        // Restar presupuesto
        tienda.setPresupuesto(tienda.getPresupuesto() - costoTotal);

        // Comprar suministro
        tienda.comprarSuministro(tipo, cantidad);

        // Remover del stock del proveedor
        removerSuministro(tipo, cantidad);

        System.out.println("✓ Se compraron " + cantidad + " " + tipo + " por $" + costoTotal);

        return true;
    }

    /**
     * Cuenta cuántas unidades de un suministro hay en stock
     */
    private int contarSuministro(TipoSuministro tipo) {
        return (int) stockSuministros.obtenerTodos().stream()
                .filter(s -> s.getTipo() == tipo)
                .count();
    }

    public int getStockSuministro(TipoSuministro tipo) {
        return contarSuministro(tipo);
    }
    public Deposito<Suministro> getStockSuministros() {
        return stockSuministros;
    }

    /**
     * Remueve suministros del stock del proveedor
     */
    private void removerSuministro(TipoSuministro tipo, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            Suministro s = stockSuministros.buscarElemento(
                    suministro -> suministro.getTipo() == tipo
            );
            if (s != null) {
                stockSuministros.removerElemento(s);
            }
        }
    }

    /**
     * Vende un hábitat a la tienda
     * @param tienda tienda compradora
     * @param tipo tipo de hábitat a vender
     * @param cantidad cuántos hábitats
     * @return true si se vendió, false si presupuesto insuficiente
     */
    public boolean venderHabitat(Tienda tienda, TipoHabitat tipo, int cantidad)
            throws PresupuestoInsuficienteException {

        int costoTotal = tipo.getPrecio() * cantidad;

        if (tienda.getPresupuesto() < costoTotal) {
            throw new PresupuestoInsuficienteException(
                    "Presupuesto insuficiente para comprar " + cantidad + " " + tipo.getNombre() +
                            " (cuesta $" + costoTotal + ").");
        }

        tienda.setPresupuesto(tienda.getPresupuesto() - costoTotal);
        tienda.comprarHabitat(tipo, cantidad);

        System.out.println("✓ Se compraron " + cantidad + " " + tipo.getNombre() + " por $" + costoTotal);

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

    /**
     * Obtiene el precio de una mascota por su nombre
     */
    public int obtenerPrecioMascota(String nombre) {
        switch (nombre) {
            case "Siames":
            case "Calico":
                return PRECIO_GATO;
            case "Labrador":
            case "Chihuahua":
                return PRECIO_PERRO;
            case "Colibri":
            case "Tucan":
                return PRECIO_PAJARO;
            case "PezDorado":
            case "PezPayaso":
                return PRECIO_PEZ;
            default:
                return 0;
        }
    }
}