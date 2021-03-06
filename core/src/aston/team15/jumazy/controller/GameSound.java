package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

/**
 * Class managing all sounds that are played in the game, volume scaling to the game's set volume
 *
 * @author Dom
 */
public class GameSound {

    private static Music menuMusic = Gdx.audio.newMusic(Gdx.files.internal("snd/Menu-Music.mp3"));
    private static Music gameStartMusic = Gdx.audio.newMusic(Gdx.files.internal("snd/Creepy Music.mp3"));
    private static Music buttonSound = Gdx.audio.newMusic(Gdx.files.internal("snd/Button.wav"));
    private static Music currentMusic;
    private static boolean musicPlaying = false;
    private static float uniVol = 0.5f;
    
    public static void playMenuMusic() {
    	currentMusic = menuMusic;
    	currentMusic.setVolume(uniVol);
        currentMusic.play();
        currentMusic.setLooping(true);
        musicPlaying = true;
    }

    public static void stopMenuMusic() {
    	currentMusic.stop();
        musicPlaying = false;
    }
    
    public static boolean getMusicPlaying() {
    	return musicPlaying;
    }

    public static void playGameStartMusic() {
        gameStartMusic.play();
        gameStartMusic.setVolume(uniVol);
    }

    public static void stopGameStartMusic() {
        gameStartMusic.stop();
    }

    public static void playButtonSound() {
    	buttonSound.setVolume(uniVol);
        buttonSound.play();
    }

	public static void setVolume(float volume) {
		uniVol = volume;
		currentMusic.setVolume(volume);
	}

	public static float getVolumePercent() {
		return uniVol*100;
	}

    public static void playCorrectSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/correct.wav")).play(uniVol);
    }

    public static void playIncorrectSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/incorrect.wav")).play(uniVol);
    }
    
    public static void playPreFightSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/preFight.mp3")).play(uniVol);
    }
    
    public static void playSwordSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/sword.wav")).play(uniVol);
    }
    
    public static void playDiceSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/dice.wav")).play(uniVol);
    }
    
    public static void playStepSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/step.mp3")).play(uniVol);
    }
    
    public static void playItemSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/item.mp3")).play(uniVol);
    }
    
    public static void playLostFightSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/lostFight.wav")).play(uniVol);
    }
    
    public static void playVictorySound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/victorySound.mp3")).play(uniVol);
    }
    
    public static void playTrapSound() {
        Gdx.audio.newSound(Gdx.files.internal("snd/trap.wav")).play(uniVol);
    }
    
    public static void setJungleMenuMusic() {
    	stopMenuMusic();
    	menuMusic = Gdx.audio.newMusic(Gdx.files.internal("snd/Menu-Music.mp3"));
    	playMenuMusic();
    }
    
    public static void setMedievalMenuMusic() {
    	stopMenuMusic();
    	menuMusic = Gdx.audio.newMusic(Gdx.files.internal("snd/Medieval.mp3"));
    	playMenuMusic();
    }
    
    public static void setOldEnglishMenuMusic() {
    	stopMenuMusic();
    	menuMusic = Gdx.audio.newMusic(Gdx.files.internal("snd/OldEnglishMenuSong.mp3"));
    	playMenuMusic();
    }
}
