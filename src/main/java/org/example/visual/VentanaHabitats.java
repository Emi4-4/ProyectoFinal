package org.example.visual;

import org.example.logica.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
/**
 * Ventana de diálogo destinada a la gestión y visualización detallada
 * de los hábitats adquiridos en la tienda, así como de su capacidad y ocupación.
 *
 * @author Emiliano
 */

public class VentanaHabitats extends JDialog {
    private Tienda tienda;

    /**
     * Constructor que configura y despliega la ventana de gestión de hábitats.
     *
     * @param padre Ventana principal de referencia
     * @param tienda Instancia actual de la tienda
     */
    public VentanaHabitats(VentanaPrincipal padre, Tienda tienda) {
        super(padre, "Gestión de Hábitats", true);
        this.tienda = tienda;

        setSize(500, 600);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout(10, 10));

        construirVentana();
    }

    private void construirVentana() {
        JLabel titulo = new JLabel("🏠 MIS HÁBITATS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Panel principal con hábitats
        JPanel panelHabitats = new JPanel();
        panelHabitats.setLayout(new BoxLayout(panelHabitats, BoxLayout.Y_AXIS));
        panelHabitats.setBorder(new TitledBorder("Detalles de cada hábitat"));

        // Mostrar cada hábitat
        if (tienda.getInventarioHabitats().getSize() == 0) {
            panelHabitats.add(new JLabel("⚠️ No tienes hábitats. Compra uno en el Proveedor."));
        } else {
            for (Habitat habitat : tienda.getInventarioHabitats().obtenerTodos()) {
                panelHabitats.add(crearPanelHabitat(habitat));
                panelHabitats.add(Box.createVerticalStrut(10));
            }
        }

        JScrollPane scroll = new JScrollPane(panelHabitats);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior con resumen
        JPanel panelResumen = new JPanel(new GridLayout(0, 2, 10, 5));
        panelResumen.setBorder(new TitledBorder("Resumen"));

        panelResumen.add(crearLabel("Total de Hábitats:", tienda.getInventarioHabitats().getSize()));
        panelResumen.add(crearLabel("Total de Mascotas:", tienda.getInventarioMascotas().getSize()));
        panelResumen.add(crearLabel("Espacio Disponible Total:", calcularEspacioTotal()));
        panelResumen.add(crearLabel("Espacio Ocupado Total:", tienda.getInventarioMascotas().getSize()));

        add(panelResumen, BorderLayout.SOUTH);

        // Botón cerrar
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());

        JPanel botones = new JPanel();
        botones.add(cerrar);
        add(botones, BorderLayout.SOUTH);
    }

    /**
     * Crea un panel visual para cada hábitat
     */
    private JPanel crearPanelHabitat(Habitat habitat) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 200), 2));
        panel.setBackground(new Color(240, 248, 255));

        // Nombre y tipo
        JLabel lblNombre = new JLabel("🏠 " + habitat.getTipo().getNombre());
        lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD, 14f));
        panel.add(lblNombre);

        panel.add(Box.createVerticalStrut(5));

        // Info del hábitat
        JLabel lblCapacidad = new JLabel("Capacidad: " + habitat.getOcupados() + "/" + habitat.getCapacidad());
        JLabel lblEspacio = new JLabel("Espacio disponible: " + habitat.obtenerEspaciosDisponibles());
        JLabel lblTipo = new JLabel("Tipo de animal: " + habitat.getTipo().getTipoAnimal().getNombre());

        lblCapacidad.setFont(lblCapacidad.getFont().deriveFont(12f));
        lblEspacio.setFont(lblEspacio.getFont().deriveFont(12f));
        lblTipo.setFont(lblTipo.getFont().deriveFont(12f));

        panel.add(lblCapacidad);
        panel.add(lblEspacio);
        panel.add(lblTipo);

        panel.add(Box.createVerticalStrut(8));

        // Mascotas en el hábitat
        JLabel lblMascotas = new JLabel("Animales en este hábitat:");
        lblMascotas.setFont(lblMascotas.getFont().deriveFont(Font.BOLD, 11f));
        panel.add(lblMascotas);

        if (habitat.obtenerMascotas().isEmpty()) {
            JLabel sinMascotas = new JLabel("  (Vacío)");
            sinMascotas.setFont(sinMascotas.getFont().deriveFont(11f));
            panel.add(sinMascotas);
        } else {
            for (Mascotas mascota : habitat.obtenerMascotas()) {
                JLabel lblMascota = new JLabel("  • " + mascota.getNombre() + " (" + mascota.getRaza() + ")");
                lblMascota.setFont(lblMascota.getFont().deriveFont(11f));
                panel.add(lblMascota);
            }
        }

        // Barra de progreso visual
        panel.add(Box.createVerticalStrut(5));
        JProgressBar barraCapacidad = new JProgressBar(0, habitat.getCapacidad());
        barraCapacidad.setValue(habitat.getOcupados());
        barraCapacidad.setStringPainted(true);
        barraCapacidad.setString(habitat.getOcupados() + "/" + habitat.getCapacidad());
        panel.add(barraCapacidad);

        return panel;
    }

    /**
     * Crea un label con nombre y valor
     */
    private JPanel crearLabel(String nombre, int valor) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel lbl = new JLabel(nombre);
        lbl.setFont(lbl.getFont().deriveFont(12f));

        JLabel valor_label = new JLabel(String.valueOf(valor));
        valor_label.setFont(valor_label.getFont().deriveFont(Font.BOLD, 14f));
        valor_label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(lbl, BorderLayout.WEST);
        panel.add(valor_label, BorderLayout.EAST);

        return panel;
    }

    /**
     * Calcula el espacio total disponible en todos los hábitats
     */
    private int calcularEspacioTotal() {
        int espacioTotal = 0;
        for (Habitat h : tienda.getInventarioHabitats().obtenerTodos()) {
            espacioTotal += h.obtenerEspaciosDisponibles();
        }
        return espacioTotal;
    }
}