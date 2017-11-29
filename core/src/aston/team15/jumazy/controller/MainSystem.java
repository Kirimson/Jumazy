package aston.team15.jumazy.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Absract class containing methods to interface with the main libGDX class. Gives subclasses access to draw on the SpriteBatch and handle input from the user
 * @author kieran, Jawwad
 *
 */
public abstract class MainSystem {
	
	protected SystemManager sysManager;
	protected OrthographicCamera cam;
	
	protected static final int GAME_WIDTH = 1280;
	protected static final int GAME_HEIGHT = 720;

	public MainSystem(SystemManager sysMan) {
		sysManager = sysMan;
		cam = new OrthographicCamera();
	}
	
	public abstract SpriteBatch draw(SpriteBatch batch);
	public abstract void handleInput();
	protected abstract void setupCamera();
	
	public OrthographicCamera getCamera() {
		return cam;
	}
}
