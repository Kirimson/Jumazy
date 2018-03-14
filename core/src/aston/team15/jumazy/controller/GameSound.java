package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

    private static Sound menuMusic = Gdx.audio.newSound(Gdx.files.internal("snd/Menu-Music.mp3"));
    private static Sound gameStartMusic = Gdx.audio.newSound(Gdx.files.internal("snd/Creepy Music.mp3"));
    private static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("snd/Button.wav"));
    private static boolean musicPlaying = false;
    private static float uniVol = 0.5f;
    
    public static void playMenuMusic() {
        menuMusic.play(uniVol);
        musicPlaying = true;
    }

    public static void stopMenuMusic() {
        menuMusic.stop();
        musicPlaying = false;
    }

    public static void loopMenuMusic() {
        menuMusic.loop(uniVol);
        musicPlaying = true;
    }
    
    public static boolean getMusicPlaying() {
    	return musicPlaying;
    }

    public static void playGameStartMusic() {
        gameStartMusic.play(uniVol);
    }

    public static void stopGameStartMusic() {
        gameStartMusic.stop();
    }

    public static void playButtonSound() {
        buttonSound.play(uniVol);
    }
}
