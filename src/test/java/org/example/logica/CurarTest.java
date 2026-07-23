package org.example.logica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CurarTest {

    private Curar curar;
    private Deposito<Suministro> inventario;
    private Calico gato;

    @BeforeEach
    void setUp() {
        inventario = new Deposito<>();
        curar = new Curar(inventario);
        gato = new Calico(1, "Miau", "Gato");

        // "Dañar" la salud del gato
        gato.setNivelSalud(50);

        // Agregar medicina al inventario
        inventario.addProducto(new Suministro(TipoSuministro.MEDICINA));
        inventario.addProducto(new Suministro(TipoSuministro.MEDICINA));
    }

    @Test
    void testCurarConsumeMedicinaCorrectamente() {
        // Verificar que hay 2 medicinas
        assertEquals(2, inventario.getSize(), "Debería haber 2 medicinas inicialmente");

        int saludBefore = gato.getNivelSalud();

        // Curar la mascota
        curar.realizar(gato);

        // Verificar que consumió 1 medicina
        assertEquals(1, inventario.getSize(), "Debería quedar 1 medicina después de curar");

        // Verificar que la salud aumentó
        assertTrue(gato.getNivelSalud() > saludBefore, "La salud debería aumentar");
    }

    @Test
    void testCurarSinMedicina() {
        // Remover todas las medicinas
        inventario.getProducto();
        inventario.getProducto();

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> curar.realizar(gato)
        );

        assertEquals("No quedan medicinas en el inventario.", ex.getMessage());
        assertEquals(0, inventario.getSize());
    }


    @Test
    void testCurarMultiplasVeces() {
        // Agregar más medicinas
        inventario.addProducto(new Suministro(TipoSuministro.MEDICINA));
        assertEquals(3, inventario.getSize());

        int saludBefore = gato.getNivelSalud();

        // Curar 2 veces
        curar.realizar(gato);
        curar.realizar(gato);

        // Verificar que consumió 2 medicinas
        assertEquals(1, inventario.getSize(), "Debería quedar 1 medicina");

        // Verificar que la salud aumentó más
        assertTrue(gato.getNivelSalud() > saludBefore, "La salud debería haber aumentado");
    }
}