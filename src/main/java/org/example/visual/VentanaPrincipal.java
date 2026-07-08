package org.example.visual;

import org.example.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel principal del simulador: muestra el estado de la tienda, permite
 * comprarle mascotas y suministros al {@link Proveedor}, cuidar a las
 * mascotas propias mediante las distintas {@link Actividad actividades}
 * (patrón Command) y simula la llegada periódica de clientes interesados
 * en adoptar (patrón Observer + hilo de eventos de Swing mediante
 * {@link Timer}).
 */
public class VentanaPrincipal extends JFrame {
    private Tienda tienda;
    private final Proveedor proveedor = Proveedor.getInstancia();
    private final JLabel lblPresupuesto = new JLabel();
    private final JPanel panelMascotas = new JPanel();
    private final JTextArea areaRegistro = new JTextArea();
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

    public VentanaPrincipal(Tienda tienda) {
        /**
         * @param tienda la tienda administrada por el jugador, ya inicializada
         *               con su presupuesto y su lote de mascotas iniciales
         */
        this.tienda = tienda;

        setTitle("Simulador de Tienda de Mascotas - Panel Principal");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelProveedor(), BorderLayout.EAST);
        add(construirPanelRegistro(), BorderLayout.SOUTH);

        refrescarMascotas();
        actualizarPresupuesto();
        iniciarSimulacionClientes();
    }
}
