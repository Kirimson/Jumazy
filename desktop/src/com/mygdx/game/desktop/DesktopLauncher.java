package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Jumazy;

public class DesktopLauncher {
	
	private static final Jumazy game = new Jumazy();
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Jumazy.WORLD_WIDTH;
		config.height = Jumazy.WORLD_HEIGHT; 
		config.title = "Jumazy";
		config.addIcon("chest-gold.png", FileType.Internal);
		
		new LwjglApplication(game, config);
	}
}
