package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

	private static Sound menuMusic = Gdx.audio.newSound(Gdx.files.internal("Menu-Music.mp3"));
	
	public static void playMenuMusic() {
		menuMusic.play();
	}
	
	public static void stopMenuMusic() {
		menuMusic.stop();
	}
	
	public static void loopMenuMusic() {
		menuMusic.loop();
	}
}
