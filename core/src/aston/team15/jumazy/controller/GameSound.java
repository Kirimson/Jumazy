package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

    private static Sound menuMusic = Gdx.audio.newSound(Gdx.files.internal("snd/Menu-Music.mp3"));
    private static Sound gameStartMusic = Gdx.audio.newSound(Gdx.files.internal("snd/Creepy Music.mp3"));
    private static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("snd/Button.wav"));

    public static void playMenuMusic() {
        menuMusic.play();
    }

    public static void stopMenuMusic() {
        menuMusic.stop();
    }

    public static void loopMenuMusic() {
        menuMusic.loop();
    }

    public static void playGameStartMusic() {
        gameStartMusic.play();
    }

    public static void stopGameStartMusic() {
        gameStartMusic.stop();
    }

    public static void playButtonSound() {
        buttonSound.play();
    }
}
