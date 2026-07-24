package agstudios.audio;

import javafx.scene.media.AudioClip;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private static final Map<String, AudioClip> sounds = new HashMap<>();
    private static double volume = 1.0;

    public static void playSound(String soundFile) {

        try {

            AudioClip clip = sounds.get(soundFile);

            if (clip == null) {

                clip = new AudioClip(
                        SoundManager.class
                                .getResource("/sounds/" + soundFile)
                                .toExternalForm()
                );

                sounds.put(soundFile, clip);
            }

            clip.stop();
            clip.play(volume);

        } catch (Exception e) {

            System.out.println("Couldn't play sound: " + soundFile);
            e.printStackTrace();

        }
    }
    public static void setVolume(double newVolume) {

        volume = newVolume;

    }

    public static double getVolume() {

        return volume;

    }
}
