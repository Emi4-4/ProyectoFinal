package org.example.logica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimuladorReabastecimientoTest {

    private Proveedor proveedor;
    private Tienda tienda;

    @BeforeEach
    void setUp() {
        // Obtenemos la instancia Singleton del Proveedor
        proveedor = Proveedor.getInstancia();
        // Creamos una tienda con presupuesto suficiente para realizar compras de prueba ($100,000)[cite: 11]
        tienda = new Tienda(100000);
    }


    @Test
    @DisplayName("Reabastecimiento de Tienda: Debe transferir suministros y descontar presupuesto correctamente")
    void testReabastecerSuministrosTienda() throws Exception {
        TipoSuministro tipo = TipoSuministro.ALIMENTO_GATO;
        int cantidadAComprar = 3;

        int stockInicialProveedor = proveedor.getStockSuministro(tipo);
        int inventarioInicialTienda = tienda.getInventarioSuministros().getSize();
        int presupuestoInicial = tienda.getPresupuesto();
        int costoTotal = tipo.getPrecio() * cantidadAComprar;

        // La tienda se reabastece comprándole al proveedor[cite: 10]
        proveedor.venderSuministro(tienda, tipo, cantidadAComprar);

        // 1. El stock del proveedor debió disminuir[cite: 10]
        assertEquals(stockInicialProveedor - cantidadAComprar, proveedor.getStockSuministro(tipo),
                "El stock del proveedor debe reducirse tras la venta.");

        // 2. El inventario de la tienda debió aumentar en la cantidad comprada[cite: 11]
        assertEquals(inventarioInicialTienda + cantidadAComprar, tienda.getInventarioSuministros().getSize(),
                "El inventario de la tienda debió incorporar los suministros comprados.");

        // 3. El presupuesto debió descontarse según el costo total[cite: 10, 11]
        assertEquals(presupuestoInicial - costoTotal, tienda.getPresupuesto(),
                "El presupuesto de la tienda debió disminuir en exactamente: $" + costoTotal);
    }

    @Test
    @DisplayName("Excepción: Debe fallar si la tienda intenta comprar más stock del disponible en el Proveedor")
    void testReabastecimientoSinStockSuficiente() {
        TipoSuministro tipo = TipoSuministro.ALIMENTO_PERRO;
        int stockActual = proveedor.getStockSuministro(tipo);
        int cantidadExcesiva = stockActual + 50; // Superamos intencionalmente el stock disponible[cite: 10]

        // Verificamos que se lance la excepción correcta[cite: 10]
        assertThrows(StockInsuficienteException.class, () -> {
            proveedor.venderSuministro(tienda, tipo, cantidadExcesiva);
        }, "Se esperaba StockInsuficienteException al intentar vaciar el stock del proveedor.");
    }

    @Test
    @DisplayName("Excepción: Debe fallar si la tienda no tiene presupuesto para reabastecerse")
    void testReabastecimientoSinPresupuesto() {
        // Creamos una tienda con un presupuesto intencionalmente bajo[cite: 11]
        Tienda tiendaPobre = new Tienda(10);
        TipoSuministro tipo = TipoSuministro.MEDICINA;

        // Verificamos que la regla financiera bloquee el reabastecimiento[cite: 10]
        assertThrows(PresupuestoInsuficienteException.class, () -> {
            proveedor.venderSuministro(tiendaPobre, tipo, 1);
        }, "Se esperaba PresupuestoInsuficienteException al intentar comprar sin dinero suficiente.");
    }
}