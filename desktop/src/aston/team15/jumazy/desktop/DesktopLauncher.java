package aston.team15.jumazy.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;

import aston.team15.jumazy.JumazyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Jumazy";
		
		config.width = 1280;
		config.height = 720;

		config.addIcon("cross.jpg", FileType.Internal);
		
		new LwjglApplication(new JumazyGame(), config);
	}
}
