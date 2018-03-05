package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;

public class StartGameMenuScreen extends MenuScreen {

	public StartGameMenuScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton startGameButton = new MenuScreenButton("START GAME", MenuScreens.PLAYER_NUMBER_SELECTION_SCREEN, game);
		MenuScreenButton texturesButton = new MenuScreenButton("TEXTURES", MenuScreens.TEXTURE_SELECTION_SCREEN, game);
		MenuScreenButton tutorialButton = new MenuScreenButton("TUTORIAL", MenuScreens.TUTORIAL_SCREEN, game);
		MenuScreenButton backButton = new MenuScreenButton("BACK", MenuScreens.MAIN_MENU_SCREEN, game);
				
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
