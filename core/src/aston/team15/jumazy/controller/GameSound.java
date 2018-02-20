package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

	private Sound menuMusic = Gdx.audio.newSound(Gdx.files.internal("14-I Smell Pussy feat. G-Unit.mp3"));
	
	public void playMenuMusic() {
		menuMusic.play();
	}
	
	public void stopMenuMusic() {
		menuMusic.stop();
	}
}
