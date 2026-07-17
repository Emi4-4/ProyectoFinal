package org.example.logica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ActividadesTest {

    private Deposito<Suministro> inventario;
    private Calico gato;

    @BeforeEach
    void setUp() {
        inventario = new Deposito<>();
        gato = new Calico(1, "Miau", "Gato");
    }

    @Test
    void testAlimentarSinComidaLanzaExcepcion() {
        Alimentar alimentar = new Alimentar(inventario);

        assertThrows(IllegalStateException.class, () -> {
            alimentar.realizar(gato);
        }, "Debería lanzar excepción sin comida");
    }

    @Test
    void testLimpiarSinShampoLanzaExcepcion() {
        Limpiar limpiar = new Limpiar(inventario);

        assertThrows(IllegalStateException.class, () -> {
            limpiar.realizar(gato);
        }, "Debería lanzar excepción sin shampoo");
    }

    @Test
    void testCurarSinMedicinaLanzaExcepcion() {
        Curar curar = new Curar(inventario);
        gato.setNivelSalud(50);

        assertThrows(IllegalStateException.class, () -> {
            curar.realizar(gato);
        }, "Debería lanzar excepción sin medicina");
    }

    @Test
    void testAlimentarConComidaFunciona() {
        inventario.addProducto(new Suministro(TipoSuministro.ALIMENTO_GATO));
        Alimentar alimentar = new Alimentar(inventario);

        int hambreAntes = gato.getNivelHambre();
        assertDoesNotThrow(() -> alimentar.realizar(gato),
                "Debería funcionar con comida disponible");
        assertTrue(gato.getNivelHambre() < hambreAntes,
                "El hambre debería disminuir");
    }

    @Test
    void testLimpiarConShampoFunciona() {
        gato.setNivelHigiene(30);
        inventario.addProducto(new Suministro(TipoSuministro.SHAMPOO));
        Limpiar limpiar = new Limpiar(inventario);

        int higieneAntes = gato.getNivelHigiene();
        assertDoesNotThrow(() -> limpiar.realizar(gato),
                "Debería funcionar con shampoo disponible");
        assertTrue(gato.getNivelHigiene() > higieneAntes,
                "La higiene debería aumentar");
    }

    @Test
    void testCurarConMedicinaFunciona() {
        gato.setNivelSalud(50);
        inventario.addProducto(new Suministro(TipoSuministro.MEDICINA));
        Curar curar = new Curar(inventario);

        int saludAntes = gato.getNivelSalud();
        assertDoesNotThrow(() -> curar.realizar(gato),
                "Debería funcionar con medicina disponible");
        assertTrue(gato.getNivelSalud() > saludAntes,
                "La salud debería aumentar");
    }
}