package org.example.visual;
import org.example.logica.*;
import javax.swing.*;
import java.awt.*;

public class VentanaProveedor extends JDialog {
    private Tienda tienda;
    private Proveedor proveedor;
    private VentanaPrincipal ventanaPrincipal;

    public VentanaProveedor(VentanaPrincipal padre, Tienda tienda, Proveedor proveedor) {

        super(padre, "Proveedor", true);
        this.ventanaPrincipal = padre;
        this.tienda = tienda;
        this.proveedor = proveedor;

        setSize(450, 600);
        setLocationRelativeTo(padre);

        construirVentana();
    }

    private void construirVentana() {

        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("PROVEEDOR", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        // Imagen del proveedor
        JLabel imagen = new JLabel(
                new ImageIcon(getClass().getResource("/images/icono_proveedor.jpg"))
        );
        imagen.setHorizontalAlignment(SwingConstants.CENTER);

        // Panel con botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 5, 5));

        panel.add(crearBotonMascota("Siames", 5000));
        panel.add(crearBotonMascota("Calico", 5000));
        panel.add(crearBotonMascota("Labrador", 6000));
        panel.add(crearBotonMascota("Chihuahua", 6000));
        panel.add(crearBotonMascota("Colibri", 8000));
        panel.add(crearBotonMascota("Tucan", 8000));
        panel.add(crearBotonMascota("PezDorado", 10000));
        panel.add(crearBotonMascota("PezPayaso", 10000));

        panel.add(crearBotonSuministro(TipoSuministro.ALIMENTO_GATO));
        panel.add(crearBotonSuministro(TipoSuministro.ALIMENTO_PERRO));
        panel.add(crearBotonSuministro(TipoSuministro.ALIMENTO_PEZ));
        panel.add(crearBotonSuministro(TipoSuministro.ALIMENTO_PAJARO));
        panel.add(crearBotonSuministro(TipoSuministro.MEDICINA));
        panel.add(crearBotonSuministro(TipoSuministro.SHAMPOO));

        // Centro
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(imagen, BorderLayout.NORTH);
        centro.add(new JScrollPane(panel), BorderLayout.CENTER);

        add(centro, BorderLayout.CENTER);

        // Botón cerrar
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        add(cerrar, BorderLayout.SOUTH);
    }

    private JButton crearBotonMascota(String nombre, int precio) {

        JButton boton = new JButton("Comprar " + nombre + " ($" + precio + ")");

        boton.addActionListener(e -> {

            Mascotas mascota = proveedor.getStockMascotas().buscarElemento(
                    m -> m.getNombre().equals(nombre)
            );

            if (mascota != null) {
                proveedor.venderMascota(tienda, mascota.getId());
                ventanaPrincipal.actualizarVentana();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No hay " + nombre + " disponibles.");
            }
        });
        return boton;
    }

    private JButton crearBotonSuministro(TipoSuministro tipo){

        JButton boton =
                new JButton("Comprar "+tipo);

        boton.addActionListener(e->{

            proveedor.venderSuministro(tienda,tipo);
            ventanaPrincipal.actualizarVentana();

        });

        return boton;
    }

}