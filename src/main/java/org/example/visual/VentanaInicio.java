package org.example.visual;

import org.example.logica.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicio extends JFrame{
    private JTextField txtPresupuesto;
    private JButton botonIniciar;

    public VentanaInicio(){

        setTitle("Tienda de Mascotas - Inicio");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        iniciarComponentes();
    }
    private void agregarMascotasIniciales(Tienda tienda) {
        // Agregar mascotas iniciales para empezar
        tienda.agregarMascota(new Siames(1, "Michi", "Gato"));
        tienda.agregarMascota(new Siames(2, "Luna", "Gato"));
        tienda.agregarMascota(new Siames(3, "Simba", "Gato"));
    }
    private void iniciarComponentes(){
        JLabel lblTexto = new JLabel("Ingrese el Presupuesto Inicial:");
        txtPresupuesto = new JTextField("20000", 15);
        botonIniciar = new JButton("Iniciar Simulación");

        add(lblTexto);
        add(txtPresupuesto);
        add(botonIniciar);

        botonIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarTienda();
            }
        });
    }

    private void iniciarTienda(){
        try {
            String texto = txtPresupuesto.getText().trim();
            int presupuestoInicial = Integer.parseInt(texto);

            if (presupuestoInicial <= 0) {
                JOptionPane.showMessageDialog(this, "El presupuesto debe ser un monto mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Tienda tienda = new Tienda(presupuestoInicial);
            agregarMascotasIniciales(tienda);

            VentanaPrincipal ventanaPrincipal = new VentanaPrincipal(tienda);
            ventanaPrincipal.setVisible(true);

            this.dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número entero válido.", "Formato Incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }
}
