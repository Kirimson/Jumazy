package aston.team15.jumazy.view;

import aston.team15.jumazy.controller.JumazyController;

public class TextureSelectionScreen extends MenuScreen {

	public TextureSelectionScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton texture1 = new MenuScreenButton("Classic", null, game);
		MenuScreenButton texture2 = new MenuScreenButton("Current", null, game);
		MenuScreenButton texture3 = new MenuScreenButton("Old English", null, game);
		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);

		table.add(texture1).pad(10);
		table.row();
		table.add(texture2).pad(10);
		table.row();
		table.add(texture3).pad(10);
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
