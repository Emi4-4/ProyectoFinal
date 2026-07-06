package org.example.visual;

import org.example.logica.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private Tienda tienda;

    public VentanaPrincipal(Tienda tienda) {
        this.tienda = tienda;

        setTitle("Simulador de Tienda de Mascotas - Panel Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel provisional = new JLabel("Presupuesto: $" + tienda.getPresupuesto());
        add(provisional);
    }
}
