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
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    private void agregarMascotasIniciales(Tienda tienda) {
        // Agregar mascotas iniciales para empezar
        tienda.agregarMascota(new Siames(1, "Michi", "Gato"));
        tienda.agregarMascota(new Siames(2, "Luna", "Gato"));
        tienda.agregarMascota(new Siames(3, "Simba", "Gato"));
    }
    private void iniciarComponentes(){

    }

    private void iniciarTienda(){
        try {

        }catch () {

        }
    }
}
