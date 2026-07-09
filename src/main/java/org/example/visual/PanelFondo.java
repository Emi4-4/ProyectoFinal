package org.example.visual;

import javax.swing.*;
import java.awt.*;

public class PanelFondo extends JPanel {

    private final Image fondo = new ImageIcon(
            getClass().getResource("/images/fondo.jpg")
    ).getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }
}