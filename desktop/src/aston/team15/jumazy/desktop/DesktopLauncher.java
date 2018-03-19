package aston.team15.jumazy.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import aston.team15.jumazy.controller.JumazyController;

/**
 * The main class for the desktop application that creates the game. Creates a
 * new {@link aston.team15.jumazy.controller.JumazyController} object, and uses
 * the Singleton design pattern to ensure that only one object of this type
 * exists at one time.
 * 
 * Sets window height, width, title and icon, and creates other important
 * objects.
 * 
 * @author Abdullah, Kieran
 *
 */
public class DesktopLauncher {

	private static final JumazyController game = new JumazyController();

	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = JumazyController.WORLD_WIDTH;
		config.height = JumazyController.WORLD_HEIGHT;
		config.title = "Jumazy";
		config.addIcon("chest-gold.png", FileType.Internal);

		new LwjglApplication(game, config);
	}

}
