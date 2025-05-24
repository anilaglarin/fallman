package game.items;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class musicPlayer {
    private Clip clip;

    public void playMusic(String path) {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            URL url = getClass().getResource(path);  //hata kontrolü
            if (url == null) {
                System.err.println("Ses dosyası bulunamadı: " + path);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }
}
