package org.example.visual;

import org.example.logica.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(8, 12, 4, 12));
        lblPresupuesto.setFont(lblPresupuesto.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(new JLabel(IconLoader.obtenerIconoMascota(null, 32)), BorderLayout.WEST);
        panel.add(lblPresupuesto, BorderLayout.CENTER);
        return panel;
    }
    private JScrollPane construirPanelCentral() {
        panelMascotas.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelMascotas.setBorder(new TitledBorder("Mis mascotas (clic para ver detalle y cuidarlas)"));
        JScrollPane scroll = new JScrollPane(panelMascotas);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel construirPanelProveedor() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("Tienda del Proveedor"));
        panel.setPreferredSize(new Dimension(230, 0));

        panel.add(botonComprarMascota("Gato", 8000));
        panel.add(botonComprarMascota("Perro", 10000));
        panel.add(botonComprarMascota("Pez", 3000));
        panel.add(botonComprarMascota("Pájaro", 5000));
        panel.add(Box.createVerticalStrut(12));

        for (TipoSuministro tipo : TipoSuministro.values()) {
            panel.add(botonComprarSuministro(tipo));
        }

        return panel;
    }

    private JButton botonComprarMascota(String tipo, int precioReferencia) {
        JButton boton = new JButton("Comprar " + tipo + " (~$" + precioReferencia + ")");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> comprarMascotaDelProveedor(tipo));
        return boton;
    }

    private JButton botonComprarSuministro(TipoSuministro tipo) {
        JButton boton = new JButton("Comprar " + tipo + " ($" + tipo.getPrecio() + ")");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> comprarSuministroDelProveedor(tipo));
        return boton;
    }

    private JScrollPane construirPanelRegistro() {
        areaRegistro.setEditable(false);
        areaRegistro.setRows(7);
        areaRegistro.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaRegistro);
        scroll.setBorder(new TitledBorder("Registro de eventos"));
        return scroll;
    }

    // Acciones con el proveedor
    private void comprarMascotaDelProveedor(String tipo) {
        Mascotas candidata = proveedor.getStockMascotas().buscarElemento(m -> m.getTipo().equals(tipo));
        if (candidata == null) {
            log("El proveedor no tiene más mascotas de tipo " + tipo + " disponibles.");
            return;
        }
        try {
            proveedor.venderMascota(tienda, candidata.getId());
            log("Compraste a " + candidata.getNombre() + " (" + tipo + ") al proveedor.");
        } catch (MascotaNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo comprar", JOptionPane.WARNING_MESSAGE);
        } finally {
            refrescarMascotas();
            actualizarPresupuesto();
        }
    }

    private void comprarSuministroDelProveedor(TipoSuministro tipo) {
        try {
            proveedor.venderSuministro(tienda, tipo);
            log("Compraste 1 unidad de " + tipo + ".");
        } catch (StockInsuficienteException | PresupuestoInsuficienteException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo comprar", JOptionPane.WARNING_MESSAGE);
        } finally {
            actualizarPresupuesto();
        }
    }

    // Detalle y cuidado de una mascota
    /**
     * Abre un diálogo con el detalle completo de la mascota y botones
     * para ejecutar cada {@link Actividad} disponible sobre ella.
     *
     * @param mascota mascota a mostrar en detalle
     */
    public void mostrarDetalleMascota(Mascotas mascota) {
        JDialog dialogo = new JDialog(this, mascota.getNombre(), true);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialogo.setSize(340, 380);
        dialogo.setLocationRelativeTo(this);
        dialogo.setLayout(new BorderLayout(6, 6));

        JLabel icono = new JLabel(IconLoader.obtenerIconoMascota(mascota.getTipo(), 96));
        icono.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblEstado = new JLabel();
        lblEstado.setVerticalAlignment(SwingConstants.TOP);
        Runnable actualizarEstado = () -> lblEstado.setText("<html><body style='width:220px'>"
                + "<b>" + mascota.getNombre() + "</b> (" + mascota.getTipo() + ")<br>"
                + "Sonido: " + mascota.emitirSonido() + "<br><br>"
                + "Hambre: " + mascota.getNivelHambre() + "/100<br>"
                + "Felicidad: " + mascota.getNivelFelicidad() + "/100<br>"
                + "Higiene: " + mascota.getNivelHigiene() + "/100<br>"
                + "Salud: " + mascota.getNivelSalud() + "/100</body></html>");
        actualizarEstado.run();

        MascotaObserverSwing observadorDialogo = m -> actualizarEstado.run();
        mascota.agregarObservador(observadorDialogo);
        dialogo.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                mascota.removerObservador(observadorDialogo);
            }
        });

        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.add(icono, BorderLayout.NORTH);
        panelInfo.add(lblEstado, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 4, 4));
        panelBotones.add(botonActividad("Alimentar", new Alimentar(tienda.getInventarioSuministros()), mascota));
        panelBotones.add(botonActividad("Jugar", new Jugar(), mascota));
        panelBotones.add(botonActividad("Limpiar", new Limpiar(tienda.getInventarioSuministros()), mascota));
        panelBotones.add(botonActividad("Curar", new Curar(tienda.getInventarioSuministros()), mascota));

        dialogo.add(panelInfo, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setVisible(true);
    }

    private JButton botonActividad(String etiqueta, Actividad actividad, Mascotas mascota) {
        JButton boton = new JButton(etiqueta);
        boton.addActionListener(e -> {
            // en caso de que no se encuentre mascota, debería haber una exception
            tienda.ejecutarActividadEnMascota(mascota.getId(), actividad);
            log(etiqueta + " -> " + mascota.getNombre());
        });
        return boton;
    }

    /**
     * Observador funcional interno usado exclusivamente por los diálogos
     * de detalle, que solo necesitan refrescar sus propias etiquetas.
     */
    @FunctionalInterface
    private interface MascotaObserverSwing extends MascotaObserver {
    }

    // Simulación de clientes
    private void iniciarSimulacionClientes() {
        Timer timer = new Timer(9000, e -> {
            if (tienda.getInventarioMascotas().getSize() == 0) {
                return;
            }
            Cliente cliente = tienda.generarCliente();
            boolean vendida = tienda.atenderCliente(cliente);
            if (vendida) {
                log("Cliente " + cliente.getNombre() + " adoptó a " + cliente.getMascotaComprada().getNombre()
                        + " (" + cliente.getSonidoMascotaComprada() + ")");
                refrescarMascotas();
                actualizarPresupuesto();
            } else {
                log("Cliente " + cliente.getNombre() + " visitó la tienda pero no compró nada.");
            }
        });
        timer.start();
    }

    // Utilidades de refresco
    private void refrescarMascotas() {
        panelMascotas.removeAll();
        for (Mascotas m : tienda.getInventarioMascotas().obtenerTodos()) {
            panelMascotas.add(new PanelMascota(m, this));
        }
        panelMascotas.revalidate();
        panelMascotas.repaint();
    }

    private void actualizarPresupuesto() {
        lblPresupuesto.setText("Presupuesto: $" + tienda.getPresupuesto());
    }

    private void log(String mensaje) {
        areaRegistro.append("[" + formatoHora.format(new Date()) + "] " + mensaje + "\n");
        areaRegistro.setCaretPosition(areaRegistro.getDocument().getLength());
    }
}
