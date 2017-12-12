package aston.team15.jumazy.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Absract class containing methods to interface with the main libGDX class. Gives subclasses access to draw on the SpriteBatch and handle input from the user
 * @author kieran, Jawwad
 *
 */
public abstract class MainSystem {
	
	protected SystemManager sysManager;
	protected OrthographicCamera cam;
	protected Viewport viewport;
	
	protected static final int GAME_WIDTH = 1280;
	protected static final int GAME_HEIGHT = 720;

	public MainSystem() {
		cam = new OrthographicCamera();
		viewport = new ScreenViewport(cam);
	}
	
	public abstract void draw(SpriteBatch batch);
	public abstract void handleInput();
	protected abstract void setupCamera();
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
