package aston.team15.jumazy.view;

import com.mygdx.game.Jumazy;

public class TextureSelectionScreen extends MenuScreen {

	public TextureSelectionScreen(Jumazy theGame) {
		super(theGame);

		ScreenSwitchButton texture1 = new ScreenSwitchButton("TEXTURE 1", null, game);
		ScreenSwitchButton texture2 = new ScreenSwitchButton("TEXTURE 2", null, game);
		ScreenSwitchButton texture3 = new ScreenSwitchButton("TEXTURE 3", null, game);
		ScreenSwitchButton backButton = new ScreenSwitchButton("BACK", Screens.START_GAME_SCREEN, game);


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
