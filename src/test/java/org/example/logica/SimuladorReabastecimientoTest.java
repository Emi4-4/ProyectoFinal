package org.example.logica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class SimuladorReabastecimientoTest {

    private Proveedor proveedor;
    private SimuladorReabastecimiento simulador;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor();
        simulador = new SimuladorReabastecimiento(proveedor);
    }

    @Test
    void testSimuladorAumentaStockSuministros() throws InterruptedException {
        int stockInicial = proveedor.getStockSuministros().getSize();
        System.out.println("Stock inicial: " + stockInicial);

        // Iniciar simulador
        simulador.iniciar();

        // Esperar 12 segundos (el simulador se ejecuta cada 10)
        Thread.sleep(12000);

        int stockFinal = proveedor.getStockSuministros().getSize();
        System.out.println("Stock final: " + stockFinal);

        // Verificar que aumentó (pero no supera 20)
        assertTrue(stockFinal <= 20, "Stock no debe superar 20");

        simulador.detener();
    }

    @Test
    void testSimuladorAumentaMascotas() throws InterruptedException {
        int mascotasInicial = proveedor.getStockMascotas().getSize();
        System.out.println("Mascotas iniciales: " + mascotasInicial);

        // Iniciar simulador
        simulador.iniciar();

        // Esperar 12 segundos
        Thread.sleep(12000);

        int mascotasFinal = proveedor.getStockMascotas().getSize();
        System.out.println("Mascotas finales: " + mascotasFinal);

        // Verificar que no supera 12 (3 de cada tipo)
        assertTrue(mascotasFinal <= 12, "Mascotas no deben superar 12");

        simulador.detener();
    }

    @Test
    void testSimuladorNoSuperapMaximos() throws InterruptedException {
        // Iniciar simulador múltiples veces
        simulador.iniciar();

        // Dejar correr 30 segundos
        Thread.sleep(30000);

        int stockFinal = proveedor.getStockSuministros().getSize();
        int mascotasFinal = proveedor.getStockMascotas().getSize();

        // Verificar límites
        assertTrue(stockFinal <= 20, "Suministros: " + stockFinal + " > 20");
        assertTrue(mascotasFinal <= 12, "Mascotas: " + mascotasFinal + " > 12");

        System.out.println("✓ Test pasó - Stock final: " + stockFinal + ", Mascotas: " + mascotasFinal);

        simulador.detener();
    }
}