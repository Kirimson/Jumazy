package aston.team15.jumazy.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import aston.team15.jumazy.controller.Jumazy;

/** 
 * An abstract class for every Menu Screen. Initialises variables for the stage
 * and table that are common to every menu, and adds actors that are common to
 * every menu screen as well, for example the background.
 * 
 * Also implements code for dynamic resizing, rendering, and capturing input.
 * 
 * @author Abdullah
 *
 */
public abstract class MenuScreen implements Screen {
 
	protected Jumazy game;
	protected Stage stage; 
	protected Table table; 

	public MenuScreen(Jumazy theGame) {
		game = theGame;
		stage = new Stage(new FitViewport(Jumazy.WORLD_WIDTH, Jumazy.WORLD_HEIGHT));

		// create a Table object, used to organise the layout of our actors
		table = new Table();
		table.setFillParent(true);
		table.center().padTop(330);

		// initialises background Image object and adds it as the first actor on the
		// stage
		Image background = new Image(new Texture("background.jpg"));
		background.setSize(Jumazy.WORLD_WIDTH, Jumazy.WORLD_HEIGHT);
		stage.addActor(background);
	
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
}
