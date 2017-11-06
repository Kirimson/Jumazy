package aston.team15.jumazy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class MainSystem {
	
	protected SystemManager sysManager;

	public MainSystem(SystemManager sysMan) {
		sysManager = sysMan;
	}
	
	public abstract SpriteBatch draw(SpriteBatch batch);
	public abstract void handleInput();
}
