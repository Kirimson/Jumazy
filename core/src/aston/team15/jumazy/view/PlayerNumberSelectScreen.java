package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;

public class PlayerNumberSelectScreen extends MenuScreen {

	public PlayerNumberSelectScreen(JumazyController theGame) {
		super(theGame);
		
		ScreenSwitchButton twoPlayerButton = new ScreenSwitchButton("2 PLAYERS", Screens.GAME_SCREEN, game);
		ScreenSwitchButton fourPlayerButton = new ScreenSwitchButton("4 PLAYERS", Screens.GAME_SCREEN, game);
		ScreenSwitchButton backButton = new ScreenSwitchButton("BACK", Screens.START_GAME_SCREEN, game);

		table.add(twoPlayerButton).pad(10);
		table.row();
		table.add(fourPlayerButton).pad(10);
		table.row();
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
