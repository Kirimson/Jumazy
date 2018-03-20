package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;

/**
 * @author Abdullah, Kieran, Dom, Shayan
 */
public class MainMenuScreen extends MenuScreen {

	public MainMenuScreen(JumazyController theGame) {
		super(theGame);

		
		Pixmap cursor = new Pixmap(Gdx.files.internal("mouse.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, 0, 0));

		MenuScreenButton playButton = new MenuScreenButton("Play", MenuScreens.START_GAME_SCREEN, game);
		MenuScreenButton settingsButton = new MenuScreenButton("Settings", MenuScreens.SETTINGS_SCREEN, game);

		JumazyButton quitButton = new JumazyButton("Quit", game.getSkin());
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

		if(GameSound.getMusicPlaying() == false) {
			GameSound.playMenuMusic();
		}
		GameSound.stopGameStartMusic();
		theGame.resetQuestions();
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
