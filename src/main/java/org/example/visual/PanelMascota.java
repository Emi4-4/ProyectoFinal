package org.example.visual;

import org.example.logica.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente visual que representa una mascota dentro del panel principal.
 * <p>
 * Actúa como <b>observador concreto</b> del patrón Observer: se registra
 * en la {@link Mascotas} que representa y se refresca automáticamente
 * cada vez que alguno de sus indicadores cambia (por ejemplo, al ejecutar
 * una {@link Actividad}). Al pasar el mouse por encima se actualiza un
 * tooltip con el resumen de estado, y al hacer clic se abre un diálogo
 * expandido con más detalle y botones de acción.
 */
public class PanelMascota extends JPanel implements MascotaObserver {
    private final Mascotas mascota;
    private final VentanaPrincipal ventana;
    private final JLabel etiquetaIcono;
    private final JLabel etiquetaNombre;
    private final JLabel etiquetaResumen;

    /**
     * @param mascota mascota representada por este panel
     * @param ventana ventana principal, usada para delegar acciones y refrescar el estado global
     */
    public PanelMascota(Mascotas mascota, VentanaPrincipal ventana) {
        this.mascota = mascota;
        this.ventana = ventana;
        mascota.agregarObservador(this);

        setLayout(new BorderLayout(4, 2));
        setBorder(new LineBorder(new Color(180, 180, 200), 1, true));
        setPreferredSize(new Dimension(140, 120));
        setBackground(Color.WHITE);

        System.out.println(mascota.getTipo());
        etiquetaIcono = new JLabel(IconLoader.obtenerIconoMascota(mascota.getRaza(), 48));
        etiquetaIcono.setHorizontalAlignment(SwingConstants.CENTER);

        etiquetaNombre = new JLabel(mascota.getNombre(), SwingConstants.CENTER);
        etiquetaNombre.setFont(etiquetaNombre.getFont().deriveFont(Font.BOLD));

        etiquetaResumen = new JLabel("", SwingConstants.CENTER);
        etiquetaResumen.setFont(etiquetaResumen.getFont().deriveFont(10f));

        add(etiquetaIcono, BorderLayout.CENTER);
        add(etiquetaNombre, BorderLayout.NORTH);
        add(etiquetaResumen, BorderLayout.SOUTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ventana.mostrarDetalleMascota(mascota);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(240, 245, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });

        actualizarTexto();
    }
    public Mascotas getMascota() {
        return mascota;
    }

    private void actualizarTexto() {
        String resumen = String.format("H:%d F:%d Hi:%d S:%d",
                mascota.getNivelHambre(), mascota.getNivelFelicidad(),
                mascota.getNivelHigiene(), mascota.getNivelSalud());
        etiquetaResumen.setText(resumen);
        setToolTipText("<html>" + mascota.getNombre() + " (" + mascota.getTipo() + ")<br>"
                + "Hambre: " + mascota.getNivelHambre() + "/100<br>"
                + "Felicidad: " + mascota.getNivelFelicidad() + "/100<br>"
                + "Higiene: " + mascota.getNivelHigiene() + "/100<br>"
                + "Salud: " + mascota.getNivelSalud() + "/100</html>");
    }

    @Override
    public void onEstadoActualizado(Mascotas mascota) {
        // Se invoca desde el hilo de Swing en este proyecto (las actividades
        // se ejecutan siempre en respuesta a un ActionListener), por lo que
        // no es necesario envolver en SwingUtilities.invokeLater.
        actualizarTexto();
        repaint();
    }
}
