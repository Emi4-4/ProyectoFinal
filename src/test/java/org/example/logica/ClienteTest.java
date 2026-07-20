package org.example.logica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;
    private Mascotas mascotaFalsa;

    // Subclase temporal de Mascotas para simular la adopción
    private static class MascotaAdopcion extends Mascotas {
        public MascotaAdopcion(int id, String nombre) {
            super(id, nombre, "Gato");
        }

        @Override
        public TipoSuministro getAlimentoPermitido() {
            return TipoSuministro.ALIMENTO_GATO;
        }

        @Override
        public String emitirSonido() {
            return "¡Miau Miau!";
        }

        @Override
        public TipoAnimal getTipoAnimal() {
            return TipoAnimal.GATO;
        }
    }

    @BeforeEach
    void setUp() {
        cliente = new Cliente(10, "Carlos", 50000);
        mascotaFalsa = new MascotaAdopcion(1, "Pelusa");
    }

    @Test
    @DisplayName("Constructor: Debe inicializar el cliente con sus valores por defecto")
    void testConstructor() {
        assertEquals(10, cliente.getId(), "El ID debe coincidir.");
        assertEquals("Carlos", cliente.getNombre(), "El nombre debe coincidir.");
        assertEquals(50000, cliente.getPresupuesto(), "El presupuesto inicial debe ser 50000.");
        assertNull(cliente.getMascotaComprada(), "Al inicio no debe tener mascota comprada.");
        assertNull(cliente.getMascotaDeseada(), "Al inicio no debe tener mascota deseada.");
        assertEquals("", cliente.getSonidoMascotaComprada(), "El sonido inicial debe estar vacío.");
    }

    @Test
    @DisplayName("Mascota Deseada: Debe permitir asignar y obtener la mascota deseada")
    void testMascotaDeseada() {
        cliente.setMascotaDeseada(mascotaFalsa);
        assertEquals(mascotaFalsa, cliente.getMascotaDeseada(), "Debe retornar la mascota deseada asignada.");
    }

    @Test
    @DisplayName("Comprar Mascota (Éxito): Debe restar presupuesto, guardar mascota y capturar sonido")
    void testComprarMascotaExitoso() {
        int precio = 15000;
        int presupuestoInicial = cliente.getPresupuesto();

        boolean compraExitosa = cliente.comprarMascota(mascotaFalsa, precio);

        assertTrue(compraExitosa, "El método debe retornar true si hay presupuesto suficiente.");
        assertEquals(presupuestoInicial - precio, cliente.getPresupuesto(), "Se debe descontar el precio del presupuesto.");
        assertEquals(mascotaFalsa, cliente.getMascotaComprada(), "La mascota comprada debe quedar registrada en el cliente.");
        assertEquals("¡Miau Miau!", cliente.getSonidoMascotaComprada(), "El cliente debe capturar el sonido emitido por la mascota.");
    }

    @Test
    @DisplayName("Comprar Mascota (Sin Fondos): Debe retornar false y no alterar el presupuesto si no alcanza el dinero")
    void testComprarMascotaPresupuestoInsuficiente() {
        Cliente clientePobre = new Cliente(11, "Ana", 1000); // Solo tiene $1000
        int precioCaro = 5000;

        boolean compraExitosa = clientePobre.comprarMascota(mascotaFalsa, precioCaro);

        assertFalse(compraExitosa, "Debe retornar false si el precio supera el presupuesto del cliente.");
        assertEquals(1000, clientePobre.getPresupuesto(), "El presupuesto debe mantenerse intacto.");
        assertNull(clientePobre.getMascotaComprada(), "No se debió registrar ninguna mascota comprada.");
        assertEquals("", clientePobre.getSonidoMascotaComprada(), "No debió capturar ningún sonido.");
    }
}