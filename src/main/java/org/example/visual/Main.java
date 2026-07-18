package org.example.visual;

import javax.swing.SwingUtilities;

import org.example.logica.Proveedor;
import org.example.logica.SimuladorReabastecimiento;
import org.example.visual.VentanaInicio;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // CAMBIO AQUÍ: Usamos getInstancia() en lugar de new Proveedor()
            Proveedor proveedor = Proveedor.getInstancia();

            // Iniciar simulador de reabastecimiento
            SimuladorReabastecimiento simulador = new SimuladorReabastecimiento(proveedor);
            simulador.iniciar();

            // Mostrar ventana inicial
            new VentanaInicio().setVisible(true);
        });

    }

}