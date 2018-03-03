package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.Jumazy;

public class StartGameMenuScreen extends MenuScreen {

	public StartGameMenuScreen(Jumazy theGame) {
		super(theGame);

		ScreenSwitchButton startGameButton = new ScreenSwitchButton("START GAME", Screens.PLAYER_NUMBER_SELECTION_SCREEN, game);
		ScreenSwitchButton texturesButton = new ScreenSwitchButton("TEXTURES", Screens.TEXTURE_SELECTION_SCREEN, game);
		ScreenSwitchButton tutorialButton = new ScreenSwitchButton("TUTORIAL", Screens.TUTORIAL_SCREEN, game);
		ScreenSwitchButton backButton = new ScreenSwitchButton("BACK", Screens.MAIN_MENU_SCREEN, game);
				
		table.add(startGameButton).pad(10);
		table.row();
		table.add(texturesButton).pad(10);
		table.row();
		table.add(tutorialButton).pad(10);
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
