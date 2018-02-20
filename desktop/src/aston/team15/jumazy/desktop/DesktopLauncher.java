package aston.team15.jumazy.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import aston.team15.jumazy.view.JumazyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = JumazyGame.TITLE;
		config.width = JumazyGame.WIDTH;
		config.height = JumazyGame.HEIGHT;

		config.addIcon("path.png", FileType.Internal);
		new LwjglApplication(new JumazyGame(), config);
	}
}
 