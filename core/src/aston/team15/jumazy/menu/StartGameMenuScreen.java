package aston.team15.jumazy.menu;

import aston.team15.jumazy.controller.JumazyController;
import aston.team15.jumazy.view.MenuScreenButton;
import aston.team15.jumazy.view.MenuScreens;

/**
 * Allows player to go the player go to playerAmountSelectScreen and TutorialScreen
 *
 * @author Dom, Mahnoor
 */
public class StartGameMenuScreen extends MenuScreen {

	public StartGameMenuScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton startGameButton = new MenuScreenButton("Start Game",
				MenuScreens.PLAYER_NUMBER_SELECTION_SCREEN, game);
		
		MenuScreenButton tutorialButton = new MenuScreenButton("Tutorial", MenuScreens.TUTORIAL_SCREEN, game);
		MenuScreenButton editorButton = new MenuScreenButton("Editor", MenuScreens.EDITOR_SCREEN, game);
		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.MAIN_MENU_SCREEN, game);

		table.add(startGameButton).pad(10);
		table.row();
		table.add(tutorialButton).pad(10);
		table.row();
		table.add(editorButton).pad(10);
		table.row();
		table.add(backButton).bottom().right().expand().pad(70);

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
