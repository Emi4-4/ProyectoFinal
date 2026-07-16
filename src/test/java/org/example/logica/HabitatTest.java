package org.example.logica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class HabitatTest {

    private Habitat habitat;
    private Calico gato;

    @BeforeEach
    void setUp() {
        habitat = new Habitat(1, TipoHabitat.HABITAT_FELINO_PEQUEÑO);
        gato = new Calico(1, "Miau", "Gato");
    }

    @Test
    void testAgregarMascotaExitosamente() {
        boolean resultado = habitat.agregarMascota(gato);
        assertTrue(resultado, "El gato debería agregarse exitosamente");
        assertEquals(1, habitat.getOcupados(), "Debería haber 1 mascota");
    }

    @Test
    void testAgregarMascotaIncompatible() {
        PezDorado pez = new PezDorado(2, "Nemo", "Pez");
        boolean resultado = habitat.agregarMascota(pez);
        assertFalse(resultado, "El pez NO debería agregarse");
        assertEquals(0, habitat.getOcupados(), "Debería haber 0 mascotas");
    }

    @Test
    void testEspaciosDisponibles() {
        assertEquals(2, habitat.obtenerEspaciosDisponibles(),
                "Debería haber 2 espacios disponibles (capacidad 2)");
        habitat.agregarMascota(gato);
        assertEquals(1, habitat.obtenerEspaciosDisponibles(),
                "Debería haber 1 espacio disponible");
    }

    @Test
    void testHabitatLleno() {
        assertFalse(habitat.estaLleno(), "No debería estar lleno inicialmente");
        Calico gato2 = new Calico(3, "Garfield", "Gato");
        habitat.agregarMascota(gato);
        habitat.agregarMascota(gato2);
        assertTrue(habitat.estaLleno(), "Debería estar lleno (2/2)");
    }

    @Test
    void testRemoverMascota() {
        habitat.agregarMascota(gato);
        assertEquals(1, habitat.getOcupados());
        boolean removida = habitat.removerMascota(gato);
        assertTrue(removida, "La mascota debería removerse");
        assertEquals(0, habitat.getOcupados(), "Debería haber 0 mascotas");
    }
}