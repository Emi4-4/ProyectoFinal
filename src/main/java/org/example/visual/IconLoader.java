package org.example.visual;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public final class IconLoader {

    public static final String GATO_CALICO = "icono_gato_calico.png";
    public static final String GATO_SIAMES = "icono_gato_siames.png";
    public static final String PAJARO_COLIBRI = "icono_pajaro_colibri.png";
    public static final String PAJARO_TUCAN = "icono_pajaro_tucan.png";
    public static final String PERRO_CHIHUAHUA = "icono_perro_chihuahua.png";
    public static final String PERRO_LABRADOR = "icono_perro_labrador.png";
    public static final String PEZ_DORADO = "icono_pez_dorado.png";
    public static final String PEZ_PAYASO = "icono_pez_payaso.png";
    public static final String LOGO_TIENDA = "icono_tienda.png";


    private IconLoader() { }

    public static Icon obtenerIconoMascota(String nombreMascota, int tamano) {
        String archivo;
        String emoji;

        switch (nombreMascota == null ? "" : nombreMascota) {
            case "Calico": archivo = GATO_CALICO; emoji = "\uD83D\uDC31"; break;
            case "Siames": archivo = GATO_SIAMES; emoji = "\uD83D\uDC31"; break;
            case "Colibri": archivo = PAJARO_COLIBRI; emoji = "\uD83D\uDC26"; break;
            case "Tucan": archivo = PAJARO_TUCAN; emoji = "\uD83D\uDC26"; break;
            case "Chihuahua": archivo = PERRO_CHIHUAHUA; emoji = "\uD83D\uDC36"; break;
            case "Labrador": archivo = PERRO_LABRADOR; emoji = "\uD83D\uDC36"; break;
            case "PezDorado": archivo = PEZ_DORADO; emoji = "\uD83D\uDC1F"; break;
            case "PezPayaso": archivo = PEZ_PAYASO; emoji = "\uD83D\uDC1F"; break;
            default: archivo = LOGO_TIENDA; emoji = "\uD83D\uDC3E";
        }
        return cargarOGenerar(archivo, emoji, tamano);
    }

    private static Icon cargarOGenerar(String nombreArchivo, String emojiRespaldo, int tamano) {
        URL recurso = IconLoader.class.getResource("/images/" + nombreArchivo);
        if (recurso != null) {
            ImageIcon original = new ImageIcon(recurso);
            Image escalada = original.getImage().getScaledInstance(tamano, tamano, Image.SCALE_SMOOTH);
            return new ImageIcon(escalada);
        }
        return generarIconoEmoji(emojiRespaldo, tamano);
    }

    private static Icon generarIconoEmoji(String emoji, int tamano) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(230, 230, 240));
                g2.fillOval(x, y, tamano, tamano);
                g2.setFont(new Font("Dialog", Font.PLAIN, (int) (tamano * 0.6)));
                FontMetrics fm = g2.getFontMetrics();
                int textX = x + (tamano - fm.stringWidth(emoji)) / 2;
                int textY = y + (tamano + fm.getAscent()) / 2 - 4;
                g2.drawString(emoji, textX, textY);
                g2.dispose();
            }
            @Override public int getIconWidth() { return tamano; }
            @Override public int getIconHeight() { return tamano; }
        };
    }
}