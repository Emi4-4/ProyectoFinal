package org.example.visual;

import javax.sound.sampled.*;
import java.net.URL;

public final class SoundLoader {

    public static void reproducirSonido(String archivo) {
        try {
            URL recurso = SoundLoader.class.getResource("/sounds/" + archivo);

            if (recurso == null) {
                System.out.println("Sonido no encontrado: " + archivo);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(recurso);
            Clip clip = AudioSystem.getClip();

            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            System.out.println("Error al reproducir sonido: " + e.getMessage());
        }
    }
}