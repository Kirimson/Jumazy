package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aston.team15.jumazy.controller.JumazyController;

public class TextureSelectionScreen extends MenuScreen {

	public TextureSelectionScreen(JumazyController theGame) {
		super(theGame);

		MenuScreenButton backButton = new MenuScreenButton("Back", MenuScreens.START_GAME_SCREEN, game);
		TextButton texture1 = new TextButton("Current", game.getSkin());
		texture1.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
			game.update("jumazyskin/current");
			game.setScreen(new TextureSelectionScreen(game));
			}			
		});
		table.add(texture1).pad(10);
		table.row();

		TextButton texture2 = new TextButton("Old English", game.getSkin());
		texture2.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.update("jumazyskin/oldenglish");
				game.setScreen(new TextureSelectionScreen(game));
				}
		});
		table.add(texture2).pad(10);
		table.row();

		TextButton texture3 = new TextButton("Snow", game.getSkin());
		texture3.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				game.update("jumazyskin/snow");
				game.setScreen(new TextureSelectionScreen(game));
				}
		});
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
