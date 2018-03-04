package aston.team15.jumazy.view;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.JumazyController;

public class ScreenSwitchButton extends TextButton {

	public ScreenSwitchButton(String string, final Screens startGameScreen, final JumazyController game) {
		super(string, game.getSkin());
		this.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				switch (startGameScreen) {
				case MAIN_MENU_SCREEN:
					game.setScreen(new MainMenuScreen(game));
					break;
				case START_GAME_SCREEN:
					game.setScreen(new StartGameMenuScreen(game));
					break;
				case TUTORIAL_SCREEN:
					game.setScreen(new TutorialScreen(game));
					break;
				case TEXTURE_SELECTION_SCREEN:
					game.setScreen(new TextureSelectionScreen(game));
					break;
				case PLAYER_NUMBER_SELECTION_SCREEN:
					game.setScreen(new PlayerAmountSelectScreen(game));
					break;
				case SETTINGS_SCREEN:
					game.setScreen(new SettingsScreen(game));
					break;
				default:
					game.setScreen(new ErrorScreen(game));
					break;
				}
			}
		});
	}

}
