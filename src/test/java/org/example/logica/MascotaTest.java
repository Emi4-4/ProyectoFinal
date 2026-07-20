package org.example.logica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MascotaTest {

    private MascotaPrueba mascota;

    // Subclase temporal para poder instanciar y probar la clase abstracta Mascotas
    private static class MascotaPrueba extends Mascotas {
        public MascotaPrueba(int id, String nombre, String tipo) {
            super(id, nombre, tipo);
        }

        @Override
        public TipoSuministro getAlimentoPermitido() {
            return TipoSuministro.ALIMENTO_PERRO;
        }

        @Override
        public String emitirSonido() {
            return "¡Guau de prueba!";
        }

        @Override
        public TipoAnimal getTipoAnimal() {
            return TipoAnimal.PERRO;
        }
    }

    @BeforeEach
    void setUp() {
        mascota = new MascotaPrueba(1, "Firulais", "Perro");
    }

    @Test
    @DisplayName("Constructor: Debe inicializar con los estados por defecto correctos")
    void testValoresIniciales() {
        assertEquals(1, mascota.getId(), "El ID debe coincidir con el asignado.");
        assertEquals("Firulais", mascota.getNombre(), "El nombre debe coincidir.");
        assertEquals("Perro", mascota.getTipo(), "El tipo debe coincidir.");
        assertEquals(50, mascota.getNivelHambre(), "El nivel inicial de hambre debe ser 50.");
        assertEquals(50, mascota.getNivelFelicidad(), "El nivel inicial de felicidad debe ser 50.");
        assertEquals(100, mascota.getNivelSalud(), "El nivel inicial de salud debe ser 100.");
        assertEquals(100, mascota.getNivelHigiene(), "El nivel inicial de higiene debe ser 100.");
        assertNull(mascota.getHabitat(), "Al inicio no debe tener hábitat asignado.");
    }

    @Test
    @DisplayName("Límites de Estado: Los setters no deben permitir valores menores a 0 ni mayores a 100")
    void testLimitesDeEstados() {
        // Probamos límite inferior (valores negativos)
        mascota.setNivelHambre(-10);
        mascota.setNivelFelicidad(-50);
        mascota.setNivelSalud(-1);
        mascota.setNivelHigiene(-100);

        assertEquals(0, mascota.getNivelHambre(), "El hambre no puede ser menor a 0.");
        assertEquals(0, mascota.getNivelFelicidad(), "La felicidad no puede ser menor a 0.");
        assertEquals(0, mascota.getNivelSalud(), "La salud no puede ser menor a 0.");
        assertEquals(0, mascota.getNivelHigiene(), "La higiene no puede ser menor a 0.");

        // Probamos límite superior (valores mayores a 100)
        mascota.setNivelHambre(150);
        mascota.setNivelFelicidad(200);
        mascota.setNivelSalud(101);
        mascota.setNivelHigiene(500);

        assertEquals(100, mascota.getNivelHambre(), "El hambre no puede superar 100.");
        assertEquals(100, mascota.getNivelFelicidad(), "La felicidad no puede superar 100.");
        assertEquals(100, mascota.getNivelSalud(), "La salud no puede superar 100.");
        assertEquals(100, mascota.getNivelHigiene(), "La higiene no puede superar 100.");
    }

    @Test
    @DisplayName("Patrón Observer: Se debe notificar al observador cuando cambia un estado")
    void testObserverNotificacion() {
        // Usamos un arreglo de un elemento para verificar si el observador fue llamado
        final boolean[] notificado = {false};

        MascotaObserver observador = new MascotaObserver() {
            @Override
            public void onEstadoActualizado(Mascotas m) {
                notificado[0] = true;
            }
        };

        mascota.agregarObservador(observador);
        mascota.setNivelHambre(30); // Esto debería disparar notificarObservadores()

        assertTrue(notificado[0], "El observador debió ser notificado al cambiar el nivel de hambre.");
    }

    @Test
    @DisplayName("Patrón Observer: No debe notificar a un observador que fue removido")
    void testObserverRemover() {
        final int[] contadorNotificaciones = {0};

        MascotaObserver observador = new MascotaObserver() {
            @Override
            public void onEstadoActualizado(Mascotas m) {
                contadorNotificaciones[0]++;
            }
        };

        mascota.agregarObservador(observador);
        mascota.setNivelFelicidad(80); // Suma 1 notificación
        mascota.removerObservador(observador);
        mascota.setNivelFelicidad(90); // Ya no debería sumar

        assertEquals(1, contadorNotificaciones[0], "El observador removido no debe seguir recibiendo notificaciones.");
    }

    @Test
    @DisplayName("Hábitat y Raza: Debe asignar correctamente el hábitat y obtener el nombre de la clase")
    void testHabitatYRaza() {
        Habitat habitat = new Habitat(1, TipoHabitat.JAULA_MEDIANA);
        mascota.setHabitat(habitat);

        assertEquals(habitat, mascota.getHabitat(), "Debe retornar el hábitat que se le asignó.");
        assertEquals("MascotaPrueba", mascota.getRaza(), "getRaza() debe retornar el nombre simple de la clase.");
    }
}