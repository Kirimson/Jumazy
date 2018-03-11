package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;

public class MainMenuScreen extends MenuScreen {

	public MainMenuScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton playButton = new MenuScreenButton("Play", MenuScreens.START_GAME_SCREEN, game);
		MenuScreenButton settingsButton = new MenuScreenButton("Settings", MenuScreens.SETTINGS_SCREEN, game);

		TextButton quitButton = new TextButton("Quit", game.getSkin());
		quitButton.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});

		table.add(playButton).pad(10);
		table.row();
		table.add(settingsButton).pad(10);
		table.row();
		table.add(quitButton).pad(10);

		table.padTop(150);

		stage.addActor(table);
		
		GameSound.playMenuMusic();
		GameSound.stopGameStartMusic();

		GameSound.playMenuMusic();
		GameSound.stopGameStartMusic();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
