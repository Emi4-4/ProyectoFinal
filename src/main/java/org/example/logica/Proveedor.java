package org.example.logica;

/**
 * Clase Proveedor que gestiona el stock de mascotas, suministros y hábitats.
 * Utiliza el patrón Singleton para garantizar una única instancia en toda la aplicación.
 */

public class Proveedor {
    private static Proveedor instancia;

    private Deposito<Mascotas> stockMascotas;
    private Deposito<Suministro> stockSuministros;

    private static final int PRECIO_GATO = 5000;
    private static final int PRECIO_PERRO = 6000;
    private static final int PRECIO_PEZ = 2500;
    private static final int PRECIO_PAJARO = 1100;

    /**
     * Constructor privado para Singleton.
     * Inicializa el stock con valores por defecto.
     */
    private Proveedor() {
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

    /**
     * Vende una mascota a la tienda.
     * Busca la mascota por ID en el stock, verifica hábitat disponible,
     * descuenta el presupuesto y transfiere la mascota.
     *
     * @param tienda     Tienda compradora
     * @param idMascota  ID de la mascota a comprar
     * @return true si la venta fue exitosa
     * @throws MascotaNoEncontradaException Si la mascota no existe en stock
     * @throws IllegalStateException Si no hay hábitat disponible o presupuesto insuficiente
     */
    public boolean venderMascota(Tienda tienda, int idMascota) throws  MascotaNoEncontradaException, IllegalStateException{

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

        int precio = obtenerPrecioMascota(mascota.getTipo());
        // Verificamos presupuesto
        if (tienda.getPresupuesto() < precio) {
            throw new IllegalStateException("Presupuesto insuficiente. Necesitas $" + precio);
        }
        // ahora realizamos la compra
        tienda.setPresupuesto(tienda.getPresupuesto() - precio);

        stockMascotas.removerElemento(mascota);
        tienda.agregarMascota(mascota);

        System.out.println("Se compró " + mascota.getNombre()
                + " por $" + precio);

        return true;
    }

    /**
     * Vende suministros a la tienda.
     *
     * @param tienda Tienda compradora
     * @param tipo Tipo de suministro
     * @param cantidad Cantidad a comprar
     * @return true si la venta fue exitosa
     * @throws StockInsuficienteException Si no hay suficiente stock
     * @throws PresupuestoInsuficienteException Si el presupuesto es insuficiente
     */
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
     * @return true si se vendió, false si presupuesto es insuficiente
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
     * Obtiene el precio de una mascota según su tipo.
     *
     * @param tipo Tipo de mascota ("Gato", "Perro", etc.)
     * @return Precio correspondiente
     */
    public int obtenerPrecioMascota(String tipo) {
        switch (tipo) {
            case "Gato": return PRECIO_GATO;
            case "Perro": return PRECIO_PERRO;
            case "Pez": return PRECIO_PEZ;
            case "Pajaro": return PRECIO_PAJARO;
            case "Pájaro": return PRECIO_PAJARO;
            default: throw new IllegalArgumentException("Tipo de mascota desconocido: " + tipo);
        }
    }
}