package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;

public class TutorialScreen extends MenuScreen {

	public TutorialScreen(JumazyController theGame) {
		super(theGame);

		ScreenSwitchButton backButton = new ScreenSwitchButton("BACK", Screens.START_GAME_SCREEN, game);

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
