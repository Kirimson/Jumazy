package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.GameSound;
import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.menu.EditorScreen;
import aston.team15.jumazy.menu.MainMenuScreen;
import aston.team15.jumazy.menu.PlayerAmountSelectScreen;
import aston.team15.jumazy.menu.SettingsScreen;
import aston.team15.jumazy.menu.StartGameMenuScreen;
import aston.team15.jumazy.menu.TextureSelectionScreen;
import aston.team15.jumazy.menu.TutorialScreen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * A button that takes a GameScreen Enum as a parameter, taking the player to that screen wean the button is clicked
 *
 * @author Abdullah, Dom
 */
public class MenuScreenButton extends JumazyButton {

	public MenuScreenButton(String string, final MenuScreens startGameScreen, final JumazyController game) {
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
				case EDITOR_SCREEN:
					game.setScreen(new EditorScreen(game));
					break;
				}
			}
		});
	}

}
