package org.example.logica;
import java.util.Random;

public class Tienda {
    private Deposito<Mascotas> inventarioMascotas;
    private Deposito<Suministro> inventarioSuministros;
    private Deposito<Habitat> inventarioHabitats;

    private int presupuesto;
    private Random random;

    private String[] nombresClientes = {"Ana", "Carlos", "María", "Juan"};

    public Tienda(int presupuestoInicial){
        this.presupuesto = presupuestoInicial;
        this.inventarioMascotas=new Deposito<>();
        this.inventarioSuministros =new Deposito<>();
        this.inventarioHabitats = new Deposito<>();
        this.random =new Random();
    }

    public Cliente generarCliente() {

        if (inventarioMascotas.getSize() == 0) {
            return null;
        }

        String nombre = nombresClientes[random.nextInt(nombresClientes.length)];
        int presupuesto = 5000 + random.nextInt(15000);

        Cliente cliente = new Cliente(random.nextInt(1000), nombre, presupuesto);

        int indice = random.nextInt(inventarioMascotas.getSize());

        Mascotas mascota =
                inventarioMascotas.obtenerTodos().get(indice);

        cliente.setMascotaDeseada(mascota);

        return cliente;
    }

    public boolean venderMascota(Cliente cliente) {

        Mascotas mascota = cliente.getMascotaDeseada();
        if (mascota == null) {
            System.out.println("❌ Cliente no tiene mascota deseada");
            return false;
        }

        // Calcular precio: basado en stats de la mascota
        int precioBase = 2000;
        int bonusSalud = mascota.getNivelSalud() * 5;
        int precioFinal = precioBase + bonusSalud;

        if (!cliente.comprarMascota(mascota, precioFinal)) {
            System.out.println("❌ Cliente no tiene presupuesto suficiente");
            return false;
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
    public boolean asignarMascotaAHabitat(Mascotas mascota) {
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

    public void agregarMascota(Mascotas m){
        this.inventarioMascotas.addProducto(m);
        System.out.println(m.getNombre() + " ha sido ingresado a la tienda.");

        if (!asignarMascotaAHabitat(m)) {
            System.out.println("⚠️ Advertencia: " + m.getNombre() +
                    " está en inventario pero sin hábitat asignado");
        }
    }

    public void ejecutarActividadEnMascota(int id, Actividad actividad) {
        Mascotas mascota = inventarioMascotas.buscarElemento(m -> m.getId() == id);
        if (mascota != null) {
            actividad.realizar(mascota);
        } else {
            System.out.println("Error: No se encontró una mascota con el ID: " + id);
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
