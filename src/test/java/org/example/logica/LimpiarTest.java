package org.example.logica;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class LimpiarTest {

    private Limpiar limpiar;
    private Deposito<Suministro> inventario;
    private Calico gato;

    @BeforeEach
    void setUp() {
        inventario = new Deposito<>();
        limpiar = new Limpiar(inventario);
        gato = new Calico(1, "Miau", "Gato");

        gato.setNivelHigiene(30);//"ensuciamos" al gato para probar los valores

        // Agregar shampoo al inventario
        inventario.addProducto(new Suministro(TipoSuministro.SHAMPOO));
        inventario.addProducto(new Suministro(TipoSuministro.SHAMPOO));
    }

    @Test
    void testLimpiarConsumeSuministroCorrectamente() {
        // Verificar que hay 2 shampoos
        assertEquals(2, inventario.getSize(), "Debería haber 2 shampoos inicialmente");

        int higieneBefore = gato.getNivelHigiene(); // = 30

        // Limpiar la mascota
        limpiar.realizar(gato);

        // Verificar que consumió 1 shampoo
        assertEquals(1, inventario.getSize(), "Debería quedar 1 shampoo después de limpiar");

        // Verificar que la higiene aumentó
        assertTrue(gato.getNivelHigiene() > higieneBefore,
                "La higiene debería aumentar de " + higieneBefore);
    }

    @Test
    void testLimpiarSinShampoo() {
        // Remover todos los shampoos
        inventario.getProducto();
        inventario.getProducto();

        int higieneBefore = gato.getNivelHigiene();

        // Intentar limpiar sin shampoo
        limpiar.realizar(gato);

        // Verificar que no cambió la higiene
        assertEquals(higieneBefore, gato.getNivelHigiene(),
                "La higiene no debería cambiar sin shampoo");
        assertEquals(0, inventario.getSize(), "No debería haber shampoo");
    }

    @Test
    void testLimpiarMultiplasVeces() {
        // Agregar más shampoos
        inventario.addProducto(new Suministro(TipoSuministro.SHAMPOO));
        assertEquals(3, inventario.getSize());

        // Limpiar 2 veces
        limpiar.realizar(gato);
        limpiar.realizar(gato);

        // Verificar que consumió 2 shampoos
        assertEquals(1, inventario.getSize(), "Debería quedar 1 shampoo");
    }
}