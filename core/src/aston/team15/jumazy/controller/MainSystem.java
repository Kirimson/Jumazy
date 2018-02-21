package aston.team15.jumazy.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aston.team15.jumazy.view.JumazyGame;

/**
 * Absract class containing methods to interface with the main libGDX class. Gives subclasses access to draw on the SpriteBatch and handle input from the user
 * @author kieran, Jawwad
 *
 */
public abstract class MainSystem {
	
	protected SystemManager sysManager;
	protected OrthographicCamera cam;
	protected Viewport viewport;
	
	public static float scalex, scaley;
	
	protected static final int GAME_WIDTH = 1280;
	protected static final int GAME_HEIGHT = 720;
	protected Stage stage;
	public MainSystem() {
		cam = new OrthographicCamera();
		viewport = new ScreenViewport(cam);
		
		Viewport view = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(view);
		scaley = (JumazyGame.HEIGHT/stage.getHeight());
		scalex = (JumazyGame.WIDTH/stage.getWidth());
		Gdx.input.setInputProcessor(stage);
		
	}
	
	public abstract void draw(SpriteBatch batch);
	public abstract void handleInput();
	protected abstract void setupCamera();
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
//	public void resize(int width, int height) {
//        viewport.update(width, height);
//    }
}
