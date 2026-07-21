package org.example.logica;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SimuladorReabastecimiento {
    private Proveedor proveedor;
    private Timer timer;
    private Random random;
    private static final int INTERVALO_MS = 10000; // 10 segundos
    private static final int MAX_SUMINISTRO_POR_TIPO = 10;
    private static final int MAX_MASCOTAS_POR_NOMBRE = 3; // Límite por especie específica

    public SimuladorReabastecimiento(Proveedor proveedor) {
        this.proveedor = proveedor;
        this.random = new Random();
        this.timer = new Timer("ReabastecimientoTimer", true);
    }

    public void iniciar() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                reabastecerSuministros();
                reabastecerMascotas();
            }
        }, INTERVALO_MS, INTERVALO_MS);

        System.out.println("✓ Simulador de reabastecimiento iniciado (Revisando cada 10s)");
    }

    /**
     * Reabastece CADA TIPO de suministro independientemente, máximo 10
     */
    private void reabastecerSuministros() {
        for (TipoSuministro tipo : TipoSuministro.values()) {
            int stockActual = contarSuministrosPorTipo(tipo);

            if (stockActual < MAX_SUMINISTRO_POR_TIPO) {
                int cantidadAleatoria = random.nextInt(3) + 1; // Genera entre 1 y 3 para rellenar
                int nuevoStock = stockActual + cantidadAleatoria;

                // Evitar que sobrepase el límite (10)
                if (nuevoStock > MAX_SUMINISTRO_POR_TIPO) {
                    cantidadAleatoria = MAX_SUMINISTRO_POR_TIPO - stockActual;
                }

                for (int i = 0; i < cantidadAleatoria; i++) {
                    proveedor.getStockSuministros().addProducto(new Suministro(tipo));
                }

                if (cantidadAleatoria > 0) {
                    System.out.println("📦 Reabastecido: +" + cantidadAleatoria + " " + tipo +
                            " (Actual: " + (stockActual + cantidadAleatoria) + "/" + MAX_SUMINISTRO_POR_TIPO + ")");
                }
            }
        }
    }

    /**
     * Reabastece CADA TIPO ESPECÍFICO de mascota independientemente, máximo 3
     */
    private void reabastecerMascotas() {
        // Nombres específicos que coinciden con los botones de la VentanaProveedor
        String[] nombres = {"Siames", "Calico", "Labrador", "Chihuahua", "Colibri", "Tucan", "PezDorado", "PezPayaso"};

        for (String nombre : nombres) {
            int countActual = contarMascotasPorNombre(nombre);

            if (countActual < MAX_MASCOTAS_POR_NOMBRE) {
                int cantidadAleatoria = random.nextInt(2) + 1; // Genera 1 o 2 para rellenar

                // Evitar que sobrepase el límite (3)
                if (countActual + cantidadAleatoria > MAX_MASCOTAS_POR_NOMBRE) {
                    cantidadAleatoria = MAX_MASCOTAS_POR_NOMBRE - countActual;
                }

                for (int i = 0; i < cantidadAleatoria; i++) {
                    Mascotas nueva = crearMascotaPorNombre(nombre);
                    if (nueva != null) {
                        proveedor.getStockMascotas().addProducto(nueva);
                    }
                }

                if (cantidadAleatoria > 0) {
                    System.out.println("🐾 Reabastecido: +" + cantidadAleatoria + " " + nombre +
                            " (Actual: " + (countActual + cantidadAleatoria) + "/" + MAX_MASCOTAS_POR_NOMBRE + ")");
                }
            }
        }
    }

    private int contarSuministrosPorTipo(TipoSuministro tipo) {
        int count = 0;
        for (Suministro s : proveedor.getStockSuministros().obtenerTodos()) {
            if (s.getTipo() == tipo) {
                count++;
            }
        }
        return count;
    }

    private int contarMascotasPorNombre(String nombre) {
        int count = 0;
        for (Mascotas m : proveedor.getStockMascotas().obtenerTodos()) {
            if (m.getNombre().equals(nombre)) {
                count++;
            }
        }
        return count;
    }

    private Mascotas crearMascotaPorNombre(String nombre) {
        int id = random.nextInt(100000); // Generamos ID aleatorio para la mascota nueva
        switch (nombre) {
            case "Siames": return new Siames(id, "Siames", "Gato");
            case "Calico": return new Calico(id, "Calico", "Gato");
            case "Labrador": return new Labrador(id, "Labrador", "Perro");
            case "Chihuahua": return new Chihuahua(id, "Chihuahua", "Perro");
            case "PezDorado": return new PezDorado(id, "PezDorado", "Pez");
            case "PezPayaso": return new PezPayaso(id, "PezPayaso", "Pez");
            case "Tucan": return new Tucan(id, "Tucan", "Pajaro");
            case "Colibri": return new Colibri(id, "Colibri", "Pajaro");
            default: return null;
        }
    }

}