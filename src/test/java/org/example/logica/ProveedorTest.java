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
        proveedor = Proveedor.getInstancia();
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
        proveedor.venderHabitat(tienda, TipoHabitat.HABITAT_FELINO_MEDIANO, 1);

        Mascotas mascotaAComprar = proveedor.getStockMascotas().obtenerTodos().get(0);
        int idMascota = mascotaAComprar.getId();
        int stockInicialProveedor = proveedor.getStockMascotas().getSize();
        int presupuestoAntesDeMascota = tienda.getPresupuesto();

        // ← CAMBIO: usamos getTipoAnimal() (enum) en vez de getTipo() (String)
        int precioMascota = proveedor.obtenerPrecioMascota(mascotaAComprar.getTipoAnimal());

        boolean resultado = proveedor.venderMascota(tienda, idMascota);

        assertTrue(resultado, "La venta debió retornar true al cumplir todas las condiciones.");
        assertEquals(stockInicialProveedor - 1, proveedor.getStockMascotas().getSize(),
                "El animal debió ser removido del depósito del proveedor.");
        assertEquals(presupuestoAntesDeMascota - precioMascota, tienda.getPresupuesto(),
                "Se debió descontar el precio exacto de la mascota del presupuesto.");
        assertEquals(1, tienda.getInventarioMascotas().getSize(),
                "La mascota debió ingresar exitosamente al inventario de la tienda.");
    }

    @Test
    @DisplayName("Venta de Mascotas (Excepción): Debe fallar si el ID de la mascota no existe en el proveedor")
    void testVenderMascotaInexistenteLanzaExcepcion() {
        int idInexistente = 999999;
        assertThrows(MascotaNoEncontradaException.class, () -> {
            proveedor.venderMascota(tienda, idInexistente);
        }, "Debe lanzar MascotaNoEncontradaException si se pide un ID que no está en el stock.");
    }

    @Test
    @DisplayName("Venta de Mascotas (Excepción): Debe lanzar IllegalStateException si la tienda no tiene un hábitat compatible")
    void testVenderMascotaSinHabitatLanzaExcepcion() {
        Mascotas mascota = proveedor.getStockMascotas().obtenerTodos().get(0);
        assertThrows(IllegalStateException.class, () -> {
            proveedor.venderMascota(tienda, mascota.getId());
        }, "Debe bloquear la compra si la tienda no tiene dónde alojar a la mascota.");
    }

    @Test
    @DisplayName("Venta de Hábitats: Debe transferir hábitats y cobrar el costo total correctamente")
    void testVenderHabitatExitoso() throws Exception {
        TipoHabitat tipo = TipoHabitat.ACUARIO_GRANDE;
        int cantidad = 2;
        int costoTotal = tipo.getPrecio() * cantidad;
        int presupuestoInicial = tienda.getPresupuesto();

        boolean resultado = proveedor.venderHabitat(tienda, tipo, cantidad);

        assertTrue(resultado, "La venta del hábitat debió realizarse exitosamente.");
        assertEquals(presupuestoInicial - costoTotal, tienda.getPresupuesto(),
                "Debe descontarse el precio del hábitat multiplicado por la cantidad comprada.");
        assertEquals(cantidad, tienda.getInventarioHabitats().getSize(),
                "La tienda debió recibir los hábitats en su inventario.");
    }

    @Test
    @DisplayName("Venta de Hábitats (Excepción): Debe fallar si la tienda no tiene dinero suficiente")
    void testVenderHabitatSinPresupuestoLanzaExcepcion() {
        Tienda tiendaSinFondos = new Tienda(50);
        assertThrows(PresupuestoInsuficienteException.class, () -> {
            proveedor.venderHabitat(tiendaSinFondos, TipoHabitat.HABITAT_CANINO_GRANDE, 1);
        }, "Debe lanzar PresupuestoInsuficienteException si no alcanza el dinero para el hábitat.");
    }

    @Test
    @DisplayName("Venta de Suministros (Excepción): Debe fallar al solicitar más stock del disponible en el proveedor")
    void testVenderSuministroSinStockLanzaExcepcion() {
        TipoSuministro tipo = TipoSuministro.SHAMPOO;
        int stockDisponible = proveedor.getStockSuministro(tipo);

        assertThrows(StockInsuficienteException.class, () -> {
            proveedor.venderSuministro(tienda, tipo, stockDisponible + 10);
        }, "Debe lanzar StockInsuficienteException al superar el límite disponible en el proveedor.");
    }

    @Test
    @DisplayName("Precios: obtenerPrecioMascota() debe retornar el precio correcto para cada valor del enum TipoAnimal")
    void testObtenerPrecioMascotaParaCadaTipo() {

        assertEquals(5000, proveedor.obtenerPrecioMascota(TipoAnimal.GATO), "El precio del Gato debe ser 5000.");
        assertEquals(6000, proveedor.obtenerPrecioMascota(TipoAnimal.PERRO), "El precio del Perro debe ser 6000.");
        assertEquals(2500, proveedor.obtenerPrecioMascota(TipoAnimal.PEZ), "El precio del Pez debe ser 2500.");
        assertEquals(1100, proveedor.obtenerPrecioMascota(TipoAnimal.PAJARO), "El precio del Pájaro debe ser 1100.");
    }
}