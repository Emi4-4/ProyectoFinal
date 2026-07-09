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
    private final JLabel lblPresupuesto = new JLabel();
    private final JPanel panelMascotas = new JPanel();
    private final JTextArea areaRegistro = new JTextArea();
    private final SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

    public void actualizarVentana() {
        refrescarMascotas();
        actualizarPresupuesto();
    }

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
        setContentPane(new PanelFondo());
        setLayout(new BorderLayout(8, 8));

        add(construirPanelSuperior(), BorderLayout.NORTH);
        add(construirPanelCentral(), BorderLayout.CENTER);
        add(construirPanelRegistro(), BorderLayout.SOUTH);

        refrescarMascotas();
        actualizarPresupuesto();
        iniciarSimulacionClientes();

    }
    private final Proveedor proveedor = new Proveedor();

    private JPanel construirPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(8, 12, 4, 12));
        lblPresupuesto.setFont(lblPresupuesto.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(new JLabel(IconLoader.obtenerIconoMascota(null, 32)), BorderLayout.WEST);
        panel.add(lblPresupuesto, BorderLayout.CENTER);
        JButton btnProveedor = new JButton("Proveedor");
        btnProveedor.addActionListener(e ->
                new VentanaProveedor(this, tienda, proveedor).setVisible(true)
        );
        panel.add(btnProveedor, BorderLayout.EAST);
        return panel;
    }

    private JScrollPane construirPanelCentral() {
        panelMascotas.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelMascotas.setOpaque(false);
        panelMascotas.setBorder(new TitledBorder("Mis mascotas (clic para ver detalle y cuidarlas)"));
        JScrollPane scroll = new JScrollPane(panelMascotas);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        return scroll;
    }


    private JScrollPane construirPanelRegistro() {
        areaRegistro.setEditable(false);
        areaRegistro.setRows(7);
        areaRegistro.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaRegistro);
        scroll.setBorder(new TitledBorder("Registro de eventos"));
        return scroll;
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

    /**
     * Logica encargada de simular clientes para la tienda, ademas de darles tiempos de oferta y compra.
     */
    //
    private void iniciarSimulacionClientes() {
            Timer timer = new Timer(40000, e -> mostrarSolicitudCliente());
            timer.start();
        }

    private void mostrarSolicitudCliente() {

        if (tienda.getInventarioMascotas().getSize() == 0) {
            return;
        }
        Cliente cliente = tienda.generarCliente();
        Mascotas mascota = cliente.getMascotaDeseada();

        JDialog popup = new JDialog(this);
        popup.setUndecorated(true);
        popup.setSize(300, 180);
        popup.setLocation(
                getX() + getWidth() - 320,
                getY() + getHeight() - 220
        );

        JPanel panel = new JPanel(new BorderLayout(5,5));
        JLabel imagenCliente = new JLabel(
                new ImageIcon(getClass().getResource("/images/icono_cliente.jpg"))
        );
        JLabel texto = new JLabel(
                "<html>"
                        + "Cliente: " + cliente.getNombre()
                        + "<br>Quiere comprar: "
                        + mascota.getNombre()
                        + "<br>Precio ofrecido: $"
                        + cliente.getPresupuesto()
                        + "<br><br>Tiempo restante: 10 segundos"
                        + "</html>"
        );

        JButton aceptar = new JButton("Aceptar");
        JButton rechazar = new JButton("Rechazar");


        aceptar.addActionListener(e -> {

            if (tienda.venderMascota(cliente)) {

                log(cliente.getNombre()
                        + " adoptó a "
                        + mascota.getNombre());

                refrescarMascotas();
                actualizarPresupuesto();
            }
            popup.dispose();
        });

        rechazar.addActionListener(e -> {
                    log(cliente.getNombre() + " rechazó la compra.");
            popup.dispose();
        });

        JPanel botones = new JPanel();
        botones.add(aceptar);
        botones.add(rechazar);

        panel.add(imagenCliente, BorderLayout.WEST);
        panel.add(texto, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        popup.add(panel);
        popup.setVisible(true);

        iniciarCuentaRegresiva(popup, texto);
    }

    private void iniciarCuentaRegresiva(JDialog popup, JLabel texto) {
        final int[] tiempo = {10};
        Timer cuenta = new Timer(1000, e -> {
            tiempo[0]--;
            texto.setText(
                    "<html>"
                            + texto.getText()
                            + "<br>Tiempo restante: "
                            + tiempo[0]
                            + "</html>"
            );

            if(tiempo[0] <= 0){
                log("El cliente se fue sin comprar.");
                popup.dispose();
                ((Timer)e.getSource()).stop();}
        });
        cuenta.start();
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
