package org.example.visual;

import org.example.logica.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Ventana de diálogo encargada de desplegar el inventario actual de
 * suministros disponibles en la tienda (alimentos, medicinas, shampoo).
 *
 * @author Emiliano
 */

public class VentanaInventario extends JDialog {
    private Tienda tienda;
    /**
     * Constructor que inicializa y estructura la ventana de inventario de suministros.
     *
     * @param padre Ventana principal contenedora
     * @param tienda Instancia de la tienda
     */

    public VentanaInventario(VentanaPrincipal padre, Tienda tienda) {
        super(padre, "Inventario de Suministros", true);
        this.tienda = tienda;

        setSize(400, 400);
        setLocationRelativeTo(padre);
        setLayout(new BorderLayout(10, 10));

        construirVentana();
    }

    private void construirVentana() {
        JLabel titulo = new JLabel("📦 INVENTARIO DE SUMINISTROS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        // Panel con información del inventario
        JPanel panelInventario = new JPanel();
        panelInventario.setLayout(new GridLayout(0, 2, 10, 10));
        panelInventario.setBorder(new TitledBorder("Suministros disponibles"));

        panelInventario.add(crearLabel("🐱 Comida Gato:", contar(TipoSuministro.ALIMENTO_GATO)));
        panelInventario.add(crearLabel("🐕 Comida Perro:", contar(TipoSuministro.ALIMENTO_PERRO)));
        panelInventario.add(crearLabel("🐠 Comida Pez:", contar(TipoSuministro.ALIMENTO_PEZ)));
        panelInventario.add(crearLabel("🦜 Comida Pájaro:", contar(TipoSuministro.ALIMENTO_PAJARO)));
        panelInventario.add(crearLabel("💊 Medicina:", contar(TipoSuministro.MEDICINA)));
        panelInventario.add(crearLabel("🧴 Shampoo:", contar(TipoSuministro.SHAMPOO)));

        JScrollPane scroll = new JScrollPane(panelInventario);
        add(scroll, BorderLayout.CENTER);

        // Panel inferior con total
        JPanel panelTotal = new JPanel();
        int total = tienda.getInventarioSuministros().getSize();
        JLabel lblTotal = new JLabel("Total de suministros: " + total);
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 14f));
        panelTotal.add(lblTotal);
        add(panelTotal, BorderLayout.SOUTH);

        // Botón cerrar
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());

        JPanel botones = new JPanel();
        botones.add(cerrar);
        add(botones, BorderLayout.SOUTH);
    }

    private JPanel crearLabel(String nombre, int cantidad) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel lbl = new JLabel(nombre);
        lbl.setFont(lbl.getFont().deriveFont(12f));

        JLabel cantidad_label = new JLabel(String.valueOf(cantidad));
        cantidad_label.setFont(cantidad_label.getFont().deriveFont(Font.BOLD, 14f));
        cantidad_label.setHorizontalAlignment(SwingConstants.RIGHT);

        panel.add(lbl, BorderLayout.WEST);
        panel.add(cantidad_label, BorderLayout.EAST);

        return panel;
    }

    private int contar(TipoSuministro tipo) {
        int cantidad = 0;
        for (Suministro s : tienda.getInventarioSuministros().obtenerTodos()) {
            if (s.getTipo() == tipo) {
                cantidad++;
            }
        }
        return cantidad;
    }
}