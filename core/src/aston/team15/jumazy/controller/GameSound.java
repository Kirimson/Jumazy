package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

	private Sound menuMusic = Gdx.audio.newSound(Gdx.files.internal("14-I Smell Pussy feat. G-Unit.mp3"));
	private boolean menuMusicPlay = true;
	private TitleSystem titsys = new TitleSystem();
	
	public GameSound() {
		//menuMusic = Gdx.audio.newSound(Gdx.files.internal("14-I Smell Pussy feat. G-Unit.mp3"));
		if(titsys.getPlayMusic() == true) {
			menuMusic.play();
		}
		else {
			menuMusic.stop();
		}
	}
	
	public void playMenuMusic() {
		menuMusic.play();
	}
	
	public void stopMenuMusic() {
		menuMusic.stop();
	}
	
	public boolean getMenuMusicPlay() {
		return menuMusicPlay;
	}
	
	public void setMenuMusic(boolean a) {
		menuMusicPlay = a;
	}
}
