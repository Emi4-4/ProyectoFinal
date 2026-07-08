package org.example.visual;

import javax.swing.SwingUtilities;
import org.example.visual.VentanaInicio;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new VentanaInicio().setVisible(true);
        });

    }

}