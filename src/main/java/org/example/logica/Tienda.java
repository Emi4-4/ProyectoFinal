package org.example.logica;
import java.util.Random;

public class Tienda {
    private Deposito<Mascotas> inventarioMascotas;
    private Deposito<Suministro> inventarioSuministros;
    private int presupuesto;
    private Random random;

    private String[] nombreMascotas = {"Pepa", "Firulais", "Luna", "Juan"};
    private String[] tipoMascotas = {"Perro", "Gato", "Pez", "Pájaro"};
    private String[] nombresClientes = {"Ana", "Carlos", "María", "Juan"};

    public Tienda(int presupuestoInicial){
        this.presupuesto = presupuestoInicial;
        this.inventarioMascotas=new Deposito<>();
        this.inventarioSuministros =new Deposito<>();
        presupuesto=20000;
        this.random =new Random();

    }

    public Cliente generarCliente() {
        String nombre = nombresClientes[random.nextInt(nombresClientes.length)];
        int presupuesto = 5000 + random.nextInt(15000);
        return new Cliente(random.nextInt(1000), nombre, presupuesto);
    }
    public boolean atenderCliente(Cliente cliente){
        if (inventarioMascotas.buscarElemento(m -> true) == null) {
            System.out.println("No hay mascotas disponibles para vender.");
            return false;
        }
        Mascotas mascota = inventarioMascotas.buscarElemento(m -> true);
        int precio = 2000 + random.nextInt(8000);
        if (cliente.getPresupuesto() >= precio){
            cliente.comprarMascota(mascota, precio);
            inventarioMascotas.getProducto(); // Remover mascota del inventario
            this.presupuesto+=precio;
            return true;
        }
        return false;
    }

    private void inicializarSuministros() {
        // Agregar suministros iniciales
        for (int i = 0; i < 4; i++) {
            inventarioSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PERRO));
            inventarioSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_GATO));
            inventarioSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PEZ));
            inventarioSuministros.addProducto(new Suministro(TipoSuministro.ALIMENTO_PAJARO));
            inventarioSuministros.addProducto(new Suministro(TipoSuministro.MEDICINA));
            inventarioSuministros.addProducto(new Suministro(TipoSuministro.SHAMPOO));
        }
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

    public void agregarMascota(Mascotas m){
        this.inventarioMascotas.addProducto(m);
        System.out.println(m.getNombre() + " ha sido ingresado a la tienda.");
    }
    public Mascotas ventaMascota(){
        //
        return inventarioMascotas.getProducto();
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

}
