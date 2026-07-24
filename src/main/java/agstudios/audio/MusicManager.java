package agstudios.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicManager {

    private static MediaPlayer mediaPlayer;

    public static void playMusic(String musicFile) {

        try {

            if (mediaPlayer == null) {

                Media media = new Media(
                        MusicManager.class
                                .getResource("/music/" + musicFile)
                                .toExternalForm()
                );

                mediaPlayer = new MediaPlayer(media);

                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

                mediaPlayer.setVolume(1.0);

                mediaPlayer.play();
            }

        } catch (Exception e) {

            System.out.println("Couldn't play music: " + musicFile);

        }
    }
    public static void setVolume(double volume) {

        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }

    }
    public static double getVolume() {

        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }

        return 1.0;

    }

    public static void stopMusic() {

        if (mediaPlayer != null) {

            mediaPlayer.stop();

            mediaPlayer = null;
        }
    }

}