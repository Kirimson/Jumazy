package aston.team15.jumazy.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;

public class TextureSelectionScreen extends MenuScreen {

	public TextureSelectionScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton texture1 = new MenuScreenButton("Texture 1", null, game);
		MenuScreenButton texture2 = new MenuScreenButton("Texture 2", null, game);
		MenuScreenButton texture3 = new MenuScreenButton("Texture 3", null, game);
		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.SETTINGS_SCREEN, game);

		Table textureTable = new Table();

		textureTable.setFillParent(true);
		textureTable.setPosition(0.0f, 0.0f);
		textureTable.row();
		textureTable.add(texture1).pad(100);
		textureTable.add(texture2).pad(100);
		textureTable.add(texture3).pad(100);

		table.add(backButton).bottom().right().expand().pad(70);

		Image background = new Image(new Texture("tBackground.jpg"));
		background.setSize(JumazyController.WORLD_WIDTH, JumazyController.WORLD_HEIGHT);
		stage.addActor(background);
		stage.addActor(table);
		stage.addActor(textureTable);
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
