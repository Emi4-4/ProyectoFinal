package org.example.logica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TiendaTest {

    private Tienda tienda;

    @BeforeEach
    void setUp() {
        // Inicializamos la tienda con un presupuesto inicial de $100,000 para las pruebas
        tienda = new Tienda(100000);
    }

    @Test
    @DisplayName("Constructor: Debe inicializar el presupuesto y los depósitos vacíos")
    void testConstructor() {
        assertEquals(100000, tienda.getPresupuesto(), "El presupuesto inicial debe ser el especificado en el constructor.");
        assertEquals(0, tienda.getInventarioMascotas().getSize(), "El inventario de mascotas debe iniciar vacío.");
        assertEquals(0, tienda.getInventarioSuministros().getSize(), "El inventario de suministros debe iniciar vacío.");
        assertEquals(0, tienda.getInventarioHabitats().getSize(), "El inventario de hábitats debe iniciar vacío.");
    }

    @Test
    @DisplayName("Comprar Suministros: Debe agregar productos al inventario y descontar el costo del presupuesto")
    void testComprarSuministroExitoso() {
        TipoSuministro tipo = TipoSuministro.ALIMENTO_GATO;
        int cantidad = 5;
        int costoTotal = tipo.getPrecio() * cantidad;
        int presupuestoInicial = tienda.getPresupuesto();

        tienda.comprarSuministro(tipo, cantidad);

        assertEquals(cantidad, tienda.getInventarioSuministros().getSize(),
                "Se debieron añadir exactamente las unidades compradas al depósito.");
        assertEquals(presupuestoInicial - costoTotal, tienda.getPresupuesto(),
                "Se debió restar el costo total de los suministros del presupuesto.");
    }

    @Test
    @DisplayName("Comprar Hábitats: Debe retornar true y registrar los hábitats al tener presupuesto suficiente")
    void testComprarHabitatExitoso() {
        TipoHabitat tipo = TipoHabitat.JAULA_MEDIANA;
        int cantidad = 2;
        int costoTotal = tipo.getPrecio() * cantidad;
        int presupuestoInicial = tienda.getPresupuesto();

        boolean exito = tienda.comprarHabitat(tipo, cantidad);

        assertTrue(exito, "La compra de hábitats debió retornar true al haber fondos suficientes.");
        assertEquals(cantidad, tienda.getInventarioHabitats().getSize(),
                "Se debieron añadir los hábitats al inventario de la tienda.");
        assertEquals(presupuestoInicial - costoTotal, tienda.getPresupuesto(),
                "El presupuesto debió disminuir en el costo total del hábitat.");
    }

    @Test
    @DisplayName("Comprar Hábitats (Sin Fondos): Debe retornar false si el costo supera el presupuesto")
    void testComprarHabitatSinPresupuesto() {
        Tienda tiendaPobre = new Tienda(100); // Presupuesto bajo que no alcanza para un hábitat

        boolean exito = tiendaPobre.comprarHabitat(TipoHabitat.ACUARIO_GRANDE, 1);

        assertFalse(exito, "Debe retornar false cuando el presupuesto es menor al costo total.");
        assertEquals(0, tiendaPobre.getInventarioHabitats().getSize(),
                "No se debió agregar ningún hábitat al inventario.");
        assertEquals(100, tiendaPobre.getPresupuesto(),
                "El presupuesto debe mantenerse intacto si la compra falló.");
    }

    @Test
    @DisplayName("Agregar Mascota: Debe ingresarla al inventario y asignarla al hábitat compatible")
    void testAgregarMascotaExitoso() {
        // 1. Compramos un hábitat compatible primero para que pueda alojar al gato
        tienda.comprarHabitat(TipoHabitat.HABITAT_FELINO_PEQUEÑO, 1);
        Mascotas siames = new Siames(1, "Misi", "Gato");

        // 2. Agregamos la mascota
        tienda.agregarMascota(siames);

        assertEquals(1, tienda.getInventarioMascotas().getSize(), "La mascota debió registrarse en el depósito de la tienda.");
        assertNotNull(siames.getHabitat(), "La mascota debió quedar asignada exitosamente a un hábitat compatible.");
    }

    @Test
    @DisplayName("Agregar Mascota (Excepción): Debe lanzar IllegalStateException si no hay hábitats disponibles")
    void testAgregarMascotaSinHabitatLanzaExcepcion() {
        Mascotas perro = new Labrador(2, "Rex", "Perro");

        // Intentamos agregar al perro sin haber comprado ningún hábitat canino previo
        assertThrows(IllegalStateException.class, () -> {
            tienda.agregarMascota(perro);
        }, "Debe lanzar IllegalStateException al no encontrar un hábitat compatible y con espacio disponible.");
    }

    @Test
    @DisplayName("Generar Cliente: Debe retornar null si el inventario de mascotas está vacío")
    void testGenerarClienteInventarioVacio() {
        Cliente cliente = tienda.generarCliente();
        assertNull(cliente, "Si no hay mascotas en inventario, generarCliente() debe retornar null.");
    }

    @Test
    @DisplayName("Generar Cliente: Debe asignar una mascota deseada del inventario al cliente generado")
    void testGenerarClienteExitoso() {
        tienda.comprarHabitat(TipoHabitat.JAULA_PEQUEÑA, 1);
        Mascotas canario = new Colibri(3, "Pico", "Pajaro");
        tienda.agregarMascota(canario);

        Cliente cliente = tienda.generarCliente();

        assertNotNull(cliente, "Debe generar y retornar un objeto Cliente válido.");
        assertNotNull(cliente.getMascotaDeseada(), "El cliente debe tener asignada una mascota deseada.");
        assertEquals(canario.getId(), cliente.getMascotaDeseada().getId(), "La mascota deseada debe provenir del inventario de la tienda.");
    }

    @Test
    @DisplayName("Vender Mascota: Flujo exitoso suma dinero, libera el hábitat y remueve la mascota del depósito")
    void testVenderMascotaExitoso() throws Exception {
        // 1. Preparamos el hábitat y la mascota
        tienda.comprarHabitat(TipoHabitat.ACUARIO_MEDIANO, 1);
        Mascotas pez = new PezDorado(4, "Nemo", "Pez");
        tienda.agregarMascota(pez);

        int presupuestoAntesDeVenta = tienda.getPresupuesto();

        // ← CAMBIO: usamos el método real de la mascota en vez de recalcular la fórmula a mano
        int precioEsperado = pez.calcularPrecioVenta();

        // 2. Creamos un cliente con suficiente dinero que desee a esa mascota
        Cliente cliente = new Cliente(100, "Juan", 50000);
        cliente.setMascotaDeseada(pez);

        // 3. Efectuamos la venta
        boolean vendida = tienda.venderMascota(cliente);

        assertTrue(vendida, "La venta debió concretarse exitosamente.");
        assertEquals(0, tienda.getInventarioMascotas().getSize(), "La mascota debió ser eliminada del depósito de la tienda.");
        assertEquals(presupuestoAntesDeVenta + precioEsperado, tienda.getPresupuesto(),
                "El presupuesto debió aumentar según calcularPrecioVenta() de la mascota.");
    }

    @Test
    @DisplayName("Vender Mascota (Excepción): Debe lanzar MascotaNoEncontradaException si no está en el depósito")
    void testVenderMascotaInexistenteLanzaExcepcion() {
        Mascotas pezFantasma = new PezPayaso(99, "Fantasma", "Pez");
        Cliente cliente = new Cliente(101, "María", 20000);
        cliente.setMascotaDeseada(pezFantasma);

        // El pez nunca fue agregado a la tienda mediante agregarMascota()
        assertThrows(MascotaNoEncontradaException.class, () -> {
            tienda.venderMascota(cliente);
        }, "Debe lanzar MascotaNoEncontradaException al intentar vender una mascota que no está en el inventario.");
    }

    @Test
    @DisplayName("Vender Mascota (Excepción): Debe lanzar PresupuestoInsuficienteException si el cliente no puede pagar")
    void testVenderMascotaClienteSinDineroLanzaExcepcion() {
        tienda.comprarHabitat(TipoHabitat.HABITAT_CANINO_GRANDE, 1);
        Mascotas perro = new Labrador(5, "Golfo", "Perro");
        tienda.agregarMascota(perro);

        // Creamos un cliente con solo $10 de presupuesto
        Cliente clientePobre = new Cliente(102, "Carlos", 10);
        clientePobre.setMascotaDeseada(perro);

        assertThrows(PresupuestoInsuficienteException.class, () -> {
            tienda.venderMascota(clientePobre);
        }, "Debe lanzar PresupuestoInsuficienteException si el cliente no tiene fondos para cubrir el precio final.");
    }

    @Test
    @DisplayName("Ejecutar Actividad (Excepción): Debe lanzar IllegalArgumentException si no existe el ID de la mascota")
    void testEjecutarActividadEnMascotaInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
            // Intentamos jugar con el ID 999 que no se encuentra en el inventario
            tienda.ejecutarActividadEnMascota(999, new Jugar());
        }, "Debe lanzar IllegalArgumentException al no encontrar la mascota por ID en el inventario.");
    }

    @Test
    @DisplayName("Paso del Tiempo: Debe degradar hambre (+5), felicidad (-5) e higiene (-5) en condiciones normales")
    void testSimularPasoDelTiempoNormal() {
        // 1. Compramos un hábitat y agregamos una mascota (inicia con Hambre:50, Fel:50, Hig:100, Sal:100)
        tienda.comprarHabitat(TipoHabitat.HABITAT_FELINO_PEQUEÑO, 1);
        Mascotas gato = new Siames(10, "Garfield", "Gato");
        tienda.agregarMascota(gato);

        // 2. Simulamos el paso de un bloque de tiempo
        tienda.simularPasoDelTiempo();

        // 3. Verificamos que los stats se hayan modificado según lo esperado
        assertEquals(55, gato.getNivelHambre(), "El hambre debió aumentar de 50 a 55 (+5).");
        assertEquals(45, gato.getNivelFelicidad(), "La felicidad debió disminuir de 50 a 45 (-5).");
        assertEquals(95, gato.getNivelHigiene(), "La higiene debió disminuir de 100 a 95 (-5).");
        assertEquals(100, gato.getNivelSalud(), "La salud no debe verse afectada si el animal no está en estado crítico.");
    }

    @Test
    @DisplayName("Paso del Tiempo (Deterioro): Debe restar salud (-5) si el hambre es > 80 o la higiene < 20")
    void testSimularPasoDelTiempoCondicionCritica() {
        // 1. Preparamos el entorno y la mascota
        tienda.comprarHabitat(TipoHabitat.HABITAT_CANINO_GRANDE, 1);
        Mascotas perro = new Labrador(11, "Firulais", "Perro");
        tienda.agregarMascota(perro);

        // 2. Forzamos un estado crítico de abandono: mucha hambre y muy sucio
        perro.setNivelHambre(85);
        perro.setNivelHigiene(15);
        int saludInicial = perro.getNivelSalud(); // 100

        // 3. Simulamos el paso del tiempo en esas condiciones críticas
        tienda.simularPasoDelTiempo();

        // 4. Verificamos la degradación normal y la penalización de salud
        assertEquals(90, perro.getNivelHambre(), "El hambre debió subir a 90.");
        assertEquals(10, perro.getNivelHigiene(), "La higiene debió bajar a 10.");
        assertEquals(saludInicial - 5, perro.getNivelSalud(), "La salud debió bajar en 5 puntos por estar en estado crítico.");
    }

    @Test
    @DisplayName("Paso del Tiempo (Salud 0): Debe remover la mascota y liberar hábitat sin sumar presupuesto")
    void testSimularPasoDelTiempoSaludCeroRemueveSinGanancia() {
        // 1. Setup: Compramos hábitat, agregamos mascota y guardamos el presupuesto inicial
        tienda.comprarHabitat(TipoHabitat.HABITAT_CANINO_GRANDE, 1);
        Mascotas perro = new Labrador(20, "Boby", "Perro");
        tienda.agregarMascota(perro);
        int presupuestoAntes = tienda.getPresupuesto();

        // 2. Forzamos la salud a 5 y condiciones críticas para que baje a 0 en el siguiente turno
        perro.setNivelSalud(5);
        perro.setNivelHambre(90);

        // 3. Ejecutamos el paso del tiempo
        tienda.simularPasoDelTiempo();

        // 4. Verificaciones
        assertEquals(0, tienda.getInventarioMascotas().getSize(), "La mascota debió ser removida del inventario al llegar a salud 0.");
        assertEquals(0, tienda.getInventarioHabitats().obtenerTodos().get(0).obtenerMascotas().size(), "El hábitat debió quedar vacío.");
        assertEquals(presupuestoAntes, tienda.getPresupuesto(), "El presupuesto NO debió modificarse (sin ganancias).");
    }
}