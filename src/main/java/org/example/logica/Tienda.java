package org.example.logica;
import java.util.Random;

/**
 * Representa la tienda de mascotas gestionada por el usuario.
 * Contiene el inventario de mascotas, suministros y hábitats,
 * y maneja la lógica de ventas y generación de clientes.
 *
 * @author Emiliano Díaz
 * @author Valentina Serón
 */
public class Tienda {
    private Deposito<Mascotas> inventarioMascotas;
    private Deposito<Suministro> inventarioSuministros;
    private Deposito<Habitat> inventarioHabitats;

    private int presupuesto;
    private Random random;

    private String[] nombresClientes = {"Ana", "Carlos", "María", "Juan"};
    /**
     * Constructor de la tienda con presupuesto inicial.
     *
     * @param presupuestoInicial Monto inicial de la tienda
     */
    public Tienda(int presupuestoInicial){
        this.presupuesto = presupuestoInicial;
        this.inventarioMascotas=new Deposito<>();
        this.inventarioSuministros =new Deposito<>();
        this.inventarioHabitats = new Deposito<>();
        this.random =new Random();
    }

    /**
     * Genera un cliente virtual con una mascota deseada aleatoria.
     * La mascota se selecciona del inventario actual de la tienda.
     *
     * @return Cliente generado, o null si no hay mascotas en inventario
     */
    public Cliente generarCliente() {

        if (inventarioMascotas.getSize() == 0) {
            return null;
        }

        String nombre = nombresClientes[random.nextInt(nombresClientes.length)];
        int presupuesto = 5000 + random.nextInt(15000);

        Cliente cliente = new Cliente(random.nextInt(1000), nombre, presupuesto);

        // selección de una mascota aleatoria para vender
        int indice = random.nextInt(inventarioMascotas.getSize());
        Mascotas mascota =
                inventarioMascotas.obtenerTodos().get(indice);

        cliente.setMascotaDeseada(mascota);

        return cliente;
    }

    /**
     * Vende una mascota a un cliente.
     * Verifica que la mascota deseada siga en inventario y que el cliente
     * tenga presupuesto suficiente.
     *
     * @param cliente Cliente interesado en comprar una mascota
     * @return true si la venta se concretó, false en caso contrario
     */
    public boolean venderMascota(Cliente cliente) throws MascotaNoEncontradaException, PresupuestoInsuficienteException{

        Mascotas mascota = cliente.getMascotaDeseada();
        if (mascota == null) {
            System.out.println("❌ Cliente no tiene mascota deseada");
            return false;
        }

        // Calcular precio: basado en stats de la mascota
        int precioBase = 2000;
        int bonusSalud = mascota.getNivelSalud() * 5;
        int precioFinal = precioBase + bonusSalud;

        // verificamos que la mascota esté en el inventario
        boolean mascotaenInventario=false;
        for (Mascotas m:inventarioMascotas.obtenerTodos()){
            if (m.getId() == mascota.getId()) {
                mascotaenInventario = true;
                mascota = m;
                break;
            }
        }
        if (!mascotaenInventario){
            throw new MascotaNoEncontradaException("❌ No hay mascota en inventario");
        }

        if (!cliente.comprarMascota(mascota, precioFinal)) {
            throw new PresupuestoInsuficienteException("❌ Cliente no tiene presupuesto suficiente");
        }

        inventarioMascotas.removerElemento(mascota);

        // Liberar espacio en el hábitat
        Habitat habitatAnterior = mascota.getHabitat();
        if (habitatAnterior != null) {
            habitatAnterior.removerMascota(mascota);
            System.out.println("✓ Espacio liberado en " + habitatAnterior.getTipo().getNombre());
        }

        presupuesto += precioFinal;
        System.out.println("✓ Mascota vendida por $" + precioFinal);

        return true;
    }


    public String obtenerEstadoMascotas() {
        StringBuilder sb = new StringBuilder();
        for (Mascotas m : inventarioMascotas.obtenerTodos()) {
            sb.append(m.toString());
        }
        return sb.toString();
    }

    public void comprarSuministro(TipoSuministro tipo, int cantidad){
        int costo = tipo.getPrecio() * cantidad;
        if (presupuesto >= costo) {
            for (int i = 0; i < cantidad; i++){
                inventarioSuministros.addProducto(new Suministro(tipo));
            }
            this.presupuesto -= costo;
            System.out.println("Se compraron " + cantidad + " unidades de " + tipo + " por $" + costo);
        }else{
            System.out.println("Presupuesto insuficiente.");
        }
    }

    /**
     * Compra uno o más hábitats del tipo especificado
     * @param tipo tipo de hábitat a comprar
     * @param cantidad cuántos hábitats comprar
     * @return true si se compraron, false si presupuesto insuficiente
     */
    public boolean comprarHabitat(TipoHabitat tipo, int cantidad) {
        int costoTotal = tipo.getPrecio() * cantidad;

        if (presupuesto < costoTotal) {
            System.out.println("❌ Presupuesto insuficiente. Necesitas $" + costoTotal +
                    " pero tienes $" + presupuesto);
            return false;
        }

        for (int i = 0; i < cantidad; i++) {
            int idHabitat = inventarioHabitats.getSize() + 1;
            Habitat nuevoHabitat = new Habitat(idHabitat, tipo);
            inventarioHabitats.addProducto(nuevoHabitat);
        }

        presupuesto -= costoTotal;
        System.out.println("✓ Se compraron " + cantidad + " " + tipo.getNombre() +
                " por $" + costoTotal);
        return true;
    }

    /**
     * Asigna una mascota a un hábitat disponible
     * @param mascota mascota a asignar
     * @return true si se asignó, false si no hay hábitats disponibles
     */
    public boolean asignarMascotaAHabitat(Mascotas mascota) throws IllegalStateException{
        // Buscar un hábitat con espacio disponible del tipo correcto
        Habitat habitatDisponible = inventarioHabitats.buscarElemento(
                h -> !h.estaLleno() && h.getTipo().getTipoAnimal() == mascota.getTipoAnimal()
        );

        if (habitatDisponible == null) {
            throw new IllegalStateException(
                    "❌ No hay hábitats disponibles para " + mascota.getNombre() +
                            ". Compra un hábitat " + mascota.getTipoAnimal().getNombre() + " primero.");
        }

        habitatDisponible.agregarMascota(mascota);
        System.out.println("✓ " + mascota.getNombre() + " asignado a " +
                habitatDisponible.getTipo().getNombre());
        return true;
    }

    /**
     * Agrega una mascota al inventario y la asigna a un hábitat disponible.
     *
     * @param m Mascota a agregar
     */
    public void agregarMascota(Mascotas m){
        this.inventarioMascotas.addProducto(m);
        System.out.println(m.getNombre() + " ha sido ingresado a la tienda.");

        if (!asignarMascotaAHabitat(m)) {
            System.out.println("⚠️ Advertencia: " + m.getNombre() +
                    " está en inventario pero sin hábitat asignado");
        }
    }

    /**
     * Ejecuta una actividad sobre una mascota específica.
     *
     * @param id        ID de la mascota
     * @param actividad Actividad a ejecutar (Alimentar, Jugar, etc.)
     */
    public void ejecutarActividadEnMascota(int id, Actividad actividad) {
        Mascotas mascota = inventarioMascotas.buscarElemento(m -> m.getId() == id);
        if (mascota != null) {
            actividad.realizar(mascota);
        } else {
            throw new IllegalArgumentException("Error: No se encontró una mascota con el ID: " + id);
        }
    }

    /**
     * Simula el paso del tiempo degradando los estados de todas las mascotas
     * en el inventario.
     */
    public void simularPasoDelTiempo() {
        // Recorremos todas las mascotas que posees actualmente en la tienda
        for (Mascotas m : inventarioMascotas.obtenerTodos()) {

            // Con el tiempo, el hambre aumenta (+5)
            m.setNivelHambre(m.getNivelHambre() + 5);

            // La felicidad y la higiene disminuyen (-5)
            m.setNivelFelicidad(m.getNivelFelicidad() - 5);
            m.setNivelHigiene(m.getNivelHigiene() - 5);

            // LÓGICA EXTRA DE SALUD:
            // Si la mascota está pasando mucha hambre (> 80) o está muy sucia (< 20),
            // su salud empieza a deteriorarse (-5)
            if (m.getNivelHambre() > 80 || m.getNivelHigiene() < 20) {
                m.setNivelSalud(m.getNivelSalud() - 5);
            }
        }
    }

    //getter y setter
    public Deposito<Suministro> getInventarioSuministros() {
        return this.inventarioSuministros;
    }
    public Deposito<Mascotas> getInventarioMascotas() {
        return inventarioMascotas;
    }
    public int getPresupuesto() {
        return presupuesto;
    }
    public void setPresupuesto(int presupuesto) {
        this.presupuesto = presupuesto;
    }
    public Deposito<Habitat> getInventarioHabitats() {
        return this.inventarioHabitats;
    }

}
