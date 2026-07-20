package org.example.logica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProveedorTest {

    private Proveedor proveedor;
    private Tienda tienda;

    @BeforeEach
    void setUp() {
        // Obtenemos la instancia Singleton antes de cada prueba
        proveedor = Proveedor.getInstancia();
        // Inicializamos una tienda con $100,000 para poder comprar tranquilamente
        tienda = new Tienda(100000);
    }

    @Test
    @DisplayName("Singleton: getInstancia() debe retornar siempre exactamente el mismo objeto en memoria")
    void testSingletonInstanciaUnica() {
        Proveedor otraReferencia = Proveedor.getInstancia();
        assertSame(proveedor, otraReferencia, "El patrón Singleton no está funcionando; se crearon dos instancias distintas.");
    }

    @Test
    @DisplayName("Venta de Mascotas: Flujo exitoso transfiere la mascota y reduce el presupuesto de la tienda")
    void testVenderMascotaExitoso() throws Exception {
        // 1. Para poder comprar un animal, primero necesitamos comprarle un hábitat compatible en la tienda
        proveedor.venderHabitat(tienda, TipoHabitat.HABITAT_FELINO_MEDIANO, 1);

        // 2. Tomamos el primer animal que haya en el stock del proveedor para comprarlo
        Mascotas mascotaAComprar = proveedor.getStockMascotas().obtenerTodos().get(0);
        int idMascota = mascotaAComprar.getId();
        int stockInicialProveedor = proveedor.getStockMascotas().getSize();
        int presupuestoAntesDeMascota = tienda.getPresupuesto();
        int precioMascota = proveedor.obtenerPrecioMascota(mascotaAComprar.getTipo());

        // 3. Ejecutamos la venta[cite: 12]
        boolean resultado = proveedor.venderMascota(tienda, idMascota);

        assertTrue(resultado, "La venta debió retornar true al cumplir todas las condiciones[cite: 12].");
        assertEquals(stockInicialProveedor - 1, proveedor.getStockMascotas().getSize(),
                "El animal debió ser removido del depósito del proveedor[cite: 12].");
        assertEquals(presupuestoAntesDeMascota - precioMascota, tienda.getPresupuesto(),
                "Se debió descontar el precio exacto de la mascota del presupuesto[cite: 12].");
        assertEquals(1, tienda.getInventarioMascotas().getSize(),
                "La mascota debió ingresar exitosamente al inventario de la tienda[cite: 12].");
    }

    @Test
    @DisplayName("Venta de Mascotas (Excepción): Debe fallar si el ID de la mascota no existe en el proveedor")
    void testVenderMascotaInexistenteLanzaExcepcion() {
        int idInexistente = 999999;

        assertThrows(MascotaNoEncontradaException.class, () -> {
            proveedor.venderMascota(tienda, idInexistente);
        }, "Debe lanzar MascotaNoEncontradaException si se pide un ID que no está en el stock[cite: 12].");
    }

    @Test
    @DisplayName("Venta de Mascotas (Excepción): Debe lanzar IllegalStateException si la tienda no tiene un hábitat compatible")
    void testVenderMascotaSinHabitatLanzaExcepcion() {
        // Buscamos un animal en el proveedor, pero NO le compramos ningún hábitat a la tienda[cite: 12]
        Mascotas mascota = proveedor.getStockMascotas().obtenerTodos().get(0);

        assertThrows(IllegalStateException.class, () -> {
            proveedor.venderMascota(tienda, mascota.getId());
        }, "Debe bloquear la compra si la tienda no tiene dónde alojar a la mascota[cite: 12].");
    }

    @Test
    @DisplayName("Venta de Hábitats: Debe transferir hábitats y cobrar el costo total correctamente")
    void testVenderHabitatExitoso() throws Exception {
        TipoHabitat tipo = TipoHabitat.ACUARIO_GRANDE;
        int cantidad = 2;
        int costoTotal = tipo.getPrecio() * cantidad;
        int presupuestoInicial = tienda.getPresupuesto();

        boolean resultado = proveedor.venderHabitat(tienda, tipo, cantidad);

        assertTrue(resultado, "La venta del hábitat debió realizarse exitosamente[cite: 12].");
        assertEquals(presupuestoInicial - costoTotal, tienda.getPresupuesto(),
                "Debe descontarse el precio del hábitat multiplicado por la cantidad comprada[cite: 12].");
        assertEquals(cantidad, tienda.getInventarioHabitats().getSize(),
                "La tienda debió recibir los hábitats en su inventario[cite: 12].");
    }

    @Test
    @DisplayName("Venta de Hábitats (Excepción): Debe fallar si la tienda no tiene dinero suficiente")
    void testVenderHabitatSinPresupuestoLanzaExcepcion() {
        Tienda tiendaSinFondos = new Tienda(50); // Presupuesto muy bajo[cite: 12]

        assertThrows(PresupuestoInsuficienteException.class, () -> {
            proveedor.venderHabitat(tiendaSinFondos, TipoHabitat.HABITAT_CANINO_GRANDE, 1);
        }, "Debe lanzar PresupuestoInsuficienteException si no alcanza el dinero para el hábitat[cite: 12].");
    }

    @Test
    @DisplayName("Venta de Suministros (Excepción): Debe fallar al solicitar más stock del disponible en el proveedor")
    void testVenderSuministroSinStockLanzaExcepcion() {
        TipoSuministro tipo = TipoSuministro.SHAMPOO;
        int stockDisponible = proveedor.getStockSuministro(tipo);

        assertThrows(StockInsuficienteException.class, () -> {
            // Intentamos comprar más de lo que el proveedor tiene[cite: 12]
            proveedor.venderSuministro(tienda, tipo, stockDisponible + 10);
        }, "Debe lanzar StockInsuficienteException al superar el límite disponible en el proveedor[cite: 12].");
    }

    @Test
    @DisplayName("Precios: obtenerPrecioMascota() debe retornar los precios constantes exactos por tipo")
    void testObtenerPrecioMascota() {
        assertEquals(5000, proveedor.obtenerPrecioMascota("Gato"), "El precio del Gato debe ser 5000[cite: 12].");
        assertEquals(6000, proveedor.obtenerPrecioMascota("Perro"), "El precio del Perro debe ser 6000[cite: 12].");
        assertEquals(2500, proveedor.obtenerPrecioMascota("Pez"), "El precio del Pez debe ser 2500[cite: 12].");
        assertEquals(1100, proveedor.obtenerPrecioMascota("Pajaro"), "El precio del Pájaro debe ser 1100[cite: 12].");

        // Probamos que lance excepción con un tipo inválido[cite: 12]
        assertThrows(IllegalArgumentException.class, () -> {
            proveedor.obtenerPrecioMascota("Dinosaurio");
        }, "Debe lanzar IllegalArgumentException ante un tipo de animal desconocido[cite: 12].");
    }
}