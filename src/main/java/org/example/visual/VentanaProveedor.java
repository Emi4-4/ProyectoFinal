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

        // HÁBITATS
        panel.add(new JLabel("=== HÁBITATS ==="));
        panel.add(crearBotonHabitat(TipoHabitat.HABITAT_FELINO_PEQUEÑO));
        panel.add(crearBotonHabitat(TipoHabitat.HABITAT_FELINO_MEDIANO));
        panel.add(crearBotonHabitat(TipoHabitat.HABITAT_FELINO_GRANDE));
        panel.add(crearBotonHabitat(TipoHabitat.HABITAT_CANINO_PEQUEÑO));
        panel.add(crearBotonHabitat(TipoHabitat.HABITAT_CANINO_MEDIANO));
        panel.add(crearBotonHabitat(TipoHabitat.HABITAT_CANINO_GRANDE));
        panel.add(crearBotonHabitat(TipoHabitat.JAULA_PEQUEÑA));
        panel.add(crearBotonHabitat(TipoHabitat.JAULA_MEDIANA));
        panel.add(crearBotonHabitat(TipoHabitat.JAULA_GRANDE));
        panel.add(crearBotonHabitat(TipoHabitat.ACUARIO_PEQUEÑO));
        panel.add(crearBotonHabitat(TipoHabitat.ACUARIO_MEDIANO));
        panel.add(crearBotonHabitat(TipoHabitat.ACUARIO_GRANDE));

        // MASCOTAS
        panel.add(new JLabel("=== MASCOTAS ==="));
        panel.add(crearBotonMascota("Siames", 5000));
        panel.add(crearBotonMascota("Calico", 5000));
        panel.add(crearBotonMascota("Labrador", 6000));
        panel.add(crearBotonMascota("Chihuahua", 6000));
        panel.add(crearBotonMascota("Colibri", 8000));
        panel.add(crearBotonMascota("Tucan", 8000));
        panel.add(crearBotonMascota("PezDorado", 10000));
        panel.add(crearBotonMascota("PezPayaso", 10000));

        // SUMINISTROS
        panel.add(new JLabel("=== SUMINISTROS ==="));
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

    /**
     * Crea botón para comprar hábitats
     */
    private JButton crearBotonHabitat(TipoHabitat tipo) {
        JButton boton = new JButton("Comprar " + tipo.getNombre() + " ($" + tipo.getPrecio() + ")");

        boton.addActionListener(e -> {
            try {
                proveedor.venderHabitat(tienda, tipo, 1);
                // ✓ CONFIRMACIÓN DE COMPRA
                JOptionPane.showMessageDialog(this,
                        "✓ Compraste: " + tipo.getNombre() + "\n" +
                                "Precio: $" + tipo.getPrecio() + "\n" +
                                "Presupuesto restante: $" + tienda.getPresupuesto(),
                        "Compra Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                ventanaPrincipal.actualizarVentana();
            } catch (PresupuestoInsuficienteException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "Presupuesto Insuficiente",
                        JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        return boton;
    }


    private JButton crearBotonMascota(String nombre, int precio) {

        JButton boton = new JButton("Comprar " + nombre + " ($" + precio + ")");

        boton.addActionListener(e -> {

            try {
                Mascotas mascota = proveedor.getStockMascotas().buscarElemento(
                        m -> m.getNombre().equals(nombre)
                );

                if (mascota != null) {
                    proveedor.venderMascota(tienda, mascota.getId());
                    // ✓ CONFIRMACIÓN DE COMPRA
                    JOptionPane.showMessageDialog(this,
                            "✓ Compraste: " + mascota.getNombre() + "\n" +
                                    "Precio: $" + precio + "\n" +
                                    "Presupuesto restante: $" + tienda.getPresupuesto(),
                            "Mascota Adquirida",
                            JOptionPane.INFORMATION_MESSAGE);

                    ventanaPrincipal.actualizarVentana();

                } else {
                    JOptionPane.showMessageDialog(this,
                            "No hay " + nombre + " disponibles.",
                            "Sin Stock",
                            JOptionPane.WARNING_MESSAGE);
                }
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "No hay Hábitat",
                        JOptionPane.WARNING_MESSAGE);
            } catch (PresupuestoInsuficienteException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "Presupuesto Insuficiente",
                        JOptionPane.WARNING_MESSAGE);
            } catch (MascotaNoEncontradaException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        return boton;

    }

    private JButton crearBotonSuministro(TipoSuministro tipo){
        int stock = proveedor.obtenerStockSuministro(tipo);

        JButton boton = new JButton("Comprar " + tipo + " ($" + tipo.getPrecio() + ")");

        boton.addActionListener(e->{

            try {
                // Diálogo para elegir cantidad
                String input = JOptionPane.showInputDialog(this,
                        "¿Cuántos " + tipo + " deseas comprar?\n" +
                                "Stock disponible: " + stock,
                        "1");

                if (input == null) {
                    return; // Usuario canceló
                }

                int cantidad = Integer.parseInt(input);

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "❌ Ingresa una cantidad válida",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (cantidad > stock) {
                    JOptionPane.showMessageDialog(this,
                            "❌ Solo hay " + stock + " disponibles",
                            "Stock Insuficiente",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                proveedor.venderSuministro(tienda, tipo, cantidad);

                int nuevoStock = proveedor.obtenerStockSuministro(tipo);
                JOptionPane.showMessageDialog(this,
                        "✓ Compraste: " + cantidad + " x " + tipo + "\n" +
                                "Costo total: $" + (tipo.getPrecio() * cantidad) + "\n" +
                                "Presupuesto restante: $" + tienda.getPresupuesto() + "\n" +
                                "Stock restante en proveedor: " + nuevoStock,
                        "Compra Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

                ventanaPrincipal.actualizarVentana();

                // Actualizar botón con nuevo stock
                boton.setText("Comprar " + tipo + " (Stock: " + nuevoStock + " | $" + tipo.getPrecio() + ")");


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Ingresa un número válido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (PresupuestoInsuficienteException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "Presupuesto Insuficiente",
                        JOptionPane.WARNING_MESSAGE);
            } catch (StockInsuficienteException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ " + ex.getMessage(),
                        "Sin Stock",
                        JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return boton;

    }

}