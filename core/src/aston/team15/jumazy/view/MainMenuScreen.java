package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Jumazy;

public class MainMenuScreen extends MenuScreen {

	public MainMenuScreen(Jumazy theGame) {
		super(theGame);

		ScreenSwitchButton playButton = new ScreenSwitchButton("PLAY", Screens.START_GAME_SCREEN, game);
		ScreenSwitchButton settingsButton = new ScreenSwitchButton("SETTINGS", Screens.SETTINGS_SCREEN, game);
 
		TextButton quitButton = new TextButton("QUIT", game.getSkin());
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
