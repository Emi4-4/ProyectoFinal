package org.example.logica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class MascotasObserverTest {

    private Calico gato;
    private ContadorObservador observador;

    @BeforeEach
    void setUp() {
        gato = new Calico(1, "Miau", "Gato");
        observador = new ContadorObservador();
        gato.agregarObservador(observador);
    }

    @Test
    void testSetNivelHambreNotificaObservadores() {
        assertEquals(0, observador.contadorLlamadas, "No debería haber llamadas inicialmente");

        gato.setNivelHambre(75);

        assertEquals(1, observador.contadorLlamadas, "Debería notificar al cambiar hambre");
    }

    @Test
    void testSetNivelFelicidadNotificaObservadores() {
        gato.setNivelFelicidad(80);
        assertEquals(1, observador.contadorLlamadas, "Debería notificar al cambiar felicidad");
    }

    @Test
    void testSetNivelHigieneNotificaObservadores() {
        gato.setNivelHigiene(60);
        assertEquals(1, observador.contadorLlamadas, "Debería notificar al cambiar higiene");
    }

    @Test
    void testSetNivelSaludNotificaObservadores() {
        gato.setNivelSalud(50);
        assertEquals(1, observador.contadorLlamadas, "Debería notificar al cambiar salud");
    }

    @Test
    void testMultiplesNotificaciones() {
        gato.setNivelHambre(50);
        gato.setNivelFelicidad(60);
        gato.setNivelHigiene(70);
        gato.setNivelSalud(80);

        assertEquals(4, observador.contadorLlamadas, "Debería haber 4 notificaciones");
    }

    @Test
    void testRemoverObservador() {
        gato.setNivelHambre(50);
        assertEquals(1, observador.contadorLlamadas);

        gato.removerObservador(observador);
        gato.setNivelHambre(75);

        assertEquals(1, observador.contadorLlamadas, "No debería notificar después de remover");
    }

    /**
     * Observador mock que cuenta cuántas veces fue notificado
     */
    private static class ContadorObservador implements MascotaObserver {
        int contadorLlamadas = 0;

        @Override
        public void onEstadoActualizado(Mascotas mascota) {
            contadorLlamadas++;
        }
    }
}