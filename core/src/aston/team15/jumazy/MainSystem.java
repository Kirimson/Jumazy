package aston.team15.jumazy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Absract class containing methods to interface with the main libGDX class. Gives subclasses access to draw on the SpriteBatch and handle input from the user
 * @author kieran, Jawwad
 *
 */
public abstract class MainSystem {
	
	protected SystemManager sysManager;

	public MainSystem(SystemManager sysMan) {
		sysManager = sysMan;
	}
	
	public abstract SpriteBatch draw(SpriteBatch batch);
	public abstract void handleInput();
}
