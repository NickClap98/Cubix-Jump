
package Meths;


import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.io.File;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ReproductorAudioLogin {
    
     private Clip clip;
    public  void MP3() {
        try {
            // PONER RUTA DEL LOGIN.wav
            File file = new File("C:/Users/Nick/Documents/Ejercicios_Java/Painbal/src/fondo/Login.wav");
            
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            // Reproducir en un nuevo hilo
            Thread t = new Thread() {
                public void run() {
                    clip.start();
                    while (clip.getMicrosecondPosition() < clip.getMicrosecondLength()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    clip.close();
                }
            };
            t.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      public  void CloseMP3() {
      
      if (clip != null && clip.isOpen()) {
            clip.stop();
            clip.close();
           
        } else {
            System.out.println("El reproductor no está abierto.");
        }
    }
      
      
      }

