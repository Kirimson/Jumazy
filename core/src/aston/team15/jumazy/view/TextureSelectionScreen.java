package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import aston.team15.jumazy.controller.JumazyController;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TextureSelectionScreen extends MenuScreen {

	public TextureSelectionScreen(JumazyController theGame) {
		super(theGame, "tBackground.jpg");

		TextButton texture1 = new TextButton("Current", new Skin(Gdx.files.internal("jumazyskin/current/jumazy-skin.json")));
		texture1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.update("jumazyskin/current");
				game.setScreen(new TextureSelectionScreen(game));
			}
		});

		TextButton texture2 = new TextButton("Old English", new Skin(Gdx.files.internal("jumazyskin/oldenglish/jumazy-skin.json")));
		texture2.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.update("jumazyskin/oldenglish");
				game.setScreen(new TextureSelectionScreen(game));
			}
		});
		TextButton texture3 = new TextButton("Medieval", new Skin(Gdx.files.internal("jumazyskin/medievel/jumazy-skin.json")));
		texture3.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.update("jumazyskin/medievel");
				game.setScreen(new TextureSelectionScreen(game));
			}
		});



		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.SETTINGS_SCREEN, game);

		Table textureTable = new Table();

		textureTable.setFillParent(true);
		textureTable.setPosition(0.0f, 0.0f);
		textureTable.row();
		textureTable.add(texture1).pad(100);
		textureTable.add(texture2).pad(100);
		textureTable.add(texture3).pad(100);

		table.add(backButton).bottom().right().expand().pad(70);

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
